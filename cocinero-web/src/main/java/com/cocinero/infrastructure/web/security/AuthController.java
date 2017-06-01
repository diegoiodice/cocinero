package com.cocinero.infrastructure.web.security;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.web.AbstractController;
import com.cocinero.infrastructure.web.RequestMapping;
import com.cocinero.infrastructure.web.message.FlashHandler;
import com.cocinero.repository.UserRepository;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.FormLoginHandler;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class AuthController extends AbstractController {

    @Autowired
    private UserRepository userRepository;

    private AuthProvider authProvider;

    @RequestMapping(path="/register")
    public Handler<RoutingContext> renderRegisterView(){
        return ctx->ctx.put("view","users/register").next();
    }

    @RequestMapping(method=HttpMethod.POST,path="/register")
    public Handler<RoutingContext> register(){
        return ctx->{
            String email = ctx.request().getParam("email");
            String password = ctx.request().getParam("password");
            userRepository.create(User.builder()
                    .email(email)
                    .password(new Sha256Hash(password).toHex())
                    .build());
            ctx.reroute(HttpMethod.POST,"/login");
        };
    }

    @RequestMapping(path="/loginRedirect")
    public Handler<RoutingContext> loginRedirect(){
        return ctx->{
            getFlashHandler().addError(ctx,"login","com.cocinero.Login.required");
            doRedirect(ctx,"/login");};
    }

    @RequestMapping(path="/login")
    public Handler<RoutingContext> renderLoginView(){
        return ctx-> ctx.put("view","login").next();
    }

    @RequestMapping(method=HttpMethod.POST,path="/login",failureHandler = "loginFailureHandler")
    public Handler<RoutingContext> login(){
        return FormLoginHandler.create(authProvider)
                .setUsernameParam("email")
                .setPasswordParam("password")
                .setDirectLoggedInOKURL("/");
    }

    public Handler<RoutingContext> loginFailureHandler(){
        return ctx-> {
            getFlashHandler().addError(ctx,"login","com.cocinero.Login.invalid.credentials");
            doRedirect(ctx,"/login");
        };
    }

    @RequestMapping(path="/logout")
    public Handler<RoutingContext> logout(){
        return ctx->{
            ofNullable(ctx.remove("user"))
                    .ifPresent(user-> getFlashHandler().addSuccess(ctx,"logout","com.cocinero.Logout.logged.out"));
            ctx.clearUser();
            doRedirect(ctx,"/");
        };
    }

    public Handler<RoutingContext> refreshUser() {
        return ctx->{
            ofNullable(ctx.user()).ifPresent(user->
                    ctx.put("user",userRepository.findByEmail(user.principal().getString("username"))));
            ctx.next();
        };
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
}
