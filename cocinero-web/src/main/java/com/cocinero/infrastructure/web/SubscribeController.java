package com.cocinero.infrastructure.web;

import com.cocinero.domain.Attendant;
import com.cocinero.domain.Event;
import com.cocinero.domain.User;
import com.cocinero.infrastructure.web.message.FlashHandler;
import com.cocinero.repository.EventRepository;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequestMapping(path = "/events")
public class SubscribeController extends AbstractController{

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(path="/:id/subscribe")
    public Handler<RoutingContext> subscribe(){
        return ctx->{
            User currentUser = ctx.get("user");
            Event currentEvent = eventRepository.findById(ctx.pathParam("id"));
            currentEvent.getAttendants().add(Attendant.builder().id(currentUser.getId()).build());
            eventRepository.save(currentEvent);
            FlashHandler.addSuccess(ctx, "Guest subscribed successfully");
            doRedirect(ctx,"/events");
        };
    }
}
