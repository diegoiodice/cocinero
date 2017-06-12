package com.cocinero.infrastructure.web.controller;

import com.cocinero.domain.Attendant;
import com.cocinero.domain.Event;
import com.cocinero.domain.User;
import com.cocinero.infrastructure.security.Secured;
import com.cocinero.infrastructure.web.RequestMapping;
import com.cocinero.infrastructure.web.controller.AbstractController;
import com.cocinero.repository.EventRepository;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequestMapping(path = "/events")
public class SubscribeController extends AbstractController {

    @Autowired
    private EventRepository eventRepository;

    @Secured
    @RequestMapping(path="/:id/subscribe")
    public Handler<RoutingContext> subscribe(){
        return ctx->{
            User currentUser = ctx.get("user");
            Event currentEvent = eventRepository.findById(ctx.pathParam("id"));
            //TODO: add attendant logic, statuses unconfirmed, confirmed, wating, already subscribed etc...
            if(currentEvent.getAttendants().add(Attendant.builder().id(currentUser.getId()).build())){
                eventRepository.save(currentEvent);
                getFlashHandler().addSuccess(ctx, "subscription","com.cocinero.subscribed.success");
            }else{
                getFlashHandler().addError(ctx, "subscription","com.cocinero.subscribed.duplicate");
            }
            doRedirect(ctx,"/events");
        };
    }
}
