package com.cocinero.infrastructure.web.controller;

import com.cocinero.infrastructure.web.notification.FlashHandler;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;

@Slf4j
public class AbstractController implements WebController{

    private FlashHandler flashHandler;

    public final Handler<RoutingContext> defaultFailureHandler(){return ctx->{
        log.error(ctx.toString());
        Throwable failure = ctx.failure();
        if(failure instanceof ConstraintViolationException){
            mapExceptions(ctx,(ConstraintViolationException)failure);
        }else{
            getFlashHandler().addError(ctx,"failure",failure.getMessage());
        }
        doRedirect(ctx,ctx.normalisedPath());
    };}

    private void mapExceptions(RoutingContext ctx, ConstraintViolationException failure) {
        failure.getConstraintViolations().forEach(violation->{
            getFlashHandler().addError(ctx,violation.getPropertyPath().toString(),violation.getMessage());
        });
    }

    protected final void doRedirect(RoutingContext ctx, String path){
        flashHandler.persistMessages(ctx);
        ctx.response().putHeader("location", path).setStatusCode(302).end();
    }

    @Override
    public final void setFlashHandler(FlashHandler flashHandler) {
        this.flashHandler = flashHandler;
    }

    public final FlashHandler getFlashHandler() {
        return this.flashHandler;
    }
}
