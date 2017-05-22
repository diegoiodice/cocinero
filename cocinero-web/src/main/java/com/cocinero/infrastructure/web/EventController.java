package com.cocinero.infrastructure.web;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
@RequestMapping(path="/events")
public class EventController extends AbstractController{


    @RequestMapping(path="/new")
    public Handler<RoutingContext> newEventView(){
        return ctx->ctx.put("view","events/new").next();
    }
}
