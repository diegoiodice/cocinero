package com.cocinero.infrastructure.web.override;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.Builder;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Builder(buildMethodName = "create")
public class MethodOverrideHandler implements Handler<RoutingContext> {

    private final String method;

    @Override
    public void handle(RoutingContext ctx) {
        Optional<HttpMethod> methodOptional = ofNullable(ctx.request().getParam(method))
                .map(String::toUpperCase)
                .map(HttpMethod::valueOf)
                .filter(method -> !method.equals(ctx.request().method()));
        if(methodOptional.isPresent()){
            ctx.reroute(methodOptional.get(),ctx.request().path());
        }else{
            ctx.next();
        }
    }
}
