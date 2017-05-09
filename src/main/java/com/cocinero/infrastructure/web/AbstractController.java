package com.cocinero.infrastructure.web;

import com.cocinero.infrastructure.web.message.FlashHandler;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractController implements WebController{

    public final Handler<RoutingContext> defaultFailureHandler(){return ctx->{
        log.error(ctx.toString());
        FlashHandler.addError(ctx,ctx.failure().getMessage());
        doRedirect(ctx,ctx.normalisedPath());
    };}

    protected final void doRedirect(RoutingContext ctx, String path){
        ctx.response().putHeader("location", path).setStatusCode(302).end();
    }
}
