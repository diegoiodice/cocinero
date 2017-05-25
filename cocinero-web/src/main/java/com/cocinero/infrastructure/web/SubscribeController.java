package com.cocinero.infrastructure.web;

import com.cocinero.domain.Event;
import com.cocinero.domain.User;
import com.cocinero.infrastructure.security.Secured;
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

    @Secured
    @RequestMapping(path="/:id/subscribe")
    public Handler<RoutingContext> subscribe(){
        return ctx->{
            User currentUser = ctx.get("user");
            Event currentEvent = eventRepository.findById(ctx.pathParam("id"));
            //TODO: add attendant logic, statuses unconfirmed, confirmed, wating, already subscribed etc...
            //Attendant attendant = JOINING.execute(Attendant.builder().id(currentUser.getId()).build(),currentEvent,currentEvent.getAttendants());
            /*currentEvent.handleAttendant(Attendant.builder().id(currentUser.getId())
                    .status(JOINING)
                    .build());*/
            eventRepository.save(currentEvent);
            FlashHandler.addSuccess(ctx, "Guest subscribed successfully");
            doRedirect(ctx,"/events");
        };
    }
}
