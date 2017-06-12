package com.cocinero.infrastructure;

import com.cocinero.infrastructure.security.Secured;
import com.cocinero.infrastructure.security.ShiroRealm;
import com.cocinero.infrastructure.web.RequestMapping;
import com.cocinero.infrastructure.web.controller.WebController;
import com.cocinero.infrastructure.web.notification.FlashHandler;
import com.cocinero.infrastructure.web.override.MethodOverrideHandler;
import com.cocinero.infrastructure.web.controller.AuthController;
import com.cocinero.repository.UserRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
public class Server extends AbstractVerticle {

    @Autowired
    private AppConfiguration configuration;

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private List<WebController> controllers;

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    private AuthHandler authHandler;

    @Override
    public void start() throws Exception {

        AuthProvider authProvider = ShiroAuth.create(vertx, new ShiroRealm(userRepository));

        authHandler = RedirectAuthHandler.create(authProvider, "/loginRedirect");
        FlashHandler flashHandler = FlashHandler.builder().environment(environment).create();

        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route().handler(MethodOverrideHandler.builder().method("_method").create());
        router.route().handler(flashHandler);
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        router.route().handler(UserSessionHandler.create(authProvider));

        router.route().blockingHandler(authController.refreshUser());

        authController.setAuthProvider(authProvider);
        addRoutes(router,authController);

        router.post().handler(authHandler);
        router.put().handler(authHandler);
        router.delete().handler(authHandler);
        router.getWithRegex("(\\/.+)+\\/(new|edit)").handler(authHandler);

        controllers.stream().map(c->{c.setFlashHandler(flashHandler); return c;})
                .filter(c-> !(c instanceof AuthController)).forEach(c->
                addRoutes(router, c));

        router.route("/public/*").handler(StaticHandler.create(configuration.getWebTheme()));

        router.route().handler((ctx) -> {
            ctx.put("themePath",configuration.getWebTheme());
            String view = ctx.get("view");
            if(view!=null){
                engine.render(ctx, configuration.getWebRoot().concat("/templates/").concat(view).concat(".html"),
                    res->{
                        if (res.succeeded()) {
                            ctx.response().end(res.result());
                        } else {
                            ctx.fail(res.cause());
                        }});
            }else{
                ctx.reroute("/events");
            }});

        vertx.createHttpServer().requestHandler(router::accept).listen(configuration.httpPort());
    }

    protected void addRoutes(final Router router,final WebController controller) {

        final Router subRouter = ofNullable(controller.getClass().getAnnotation(RequestMapping.class))
                .map(RequestMapping::path).map(p -> {
                    Router r = Router.router(vertx);
                    router.mountSubRouter(p, r);
                    return r;
                }).orElse(router);

        Stream.of(controller.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method->{
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    addHandlers(subRouter, controller, method, requestMapping);
                });
    }

    protected void addHandlers(Router router, WebController controller, Method method, RequestMapping requestMapping) {
        try {
            if(method.isAnnotationPresent(Secured.class)){
                router.route(requestMapping.method(), requestMapping.path())
                        .handler(authHandler);
            }
            Handler failureHandler = (Handler)controller.getClass()
                    .getMethod(requestMapping.failureHandler()).invoke(controller);
            Handler handler = (Handler)method.invoke(controller);
            router.route(requestMapping.method(), requestMapping.path())
                    .blockingHandler(handler).failureHandler(failureHandler);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage(),e);
            throw new IllegalStateException(e.getMessage(),e);
        }
    }
}