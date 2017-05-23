package com.cocinero.infrastructure.web;

import com.cocinero.domain.Event;
import com.cocinero.domain.EventRepository;
import com.cocinero.infrastructure.web.message.FlashHandler;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequestMapping(path="/events")
public class EventController extends AbstractController{

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(path="/new")
    public Handler<RoutingContext> newEventView(){
        return ctx->ctx.put("view","events/new").next();
    }

    @RequestMapping(path="/",method = HttpMethod.POST)
    public Handler<RoutingContext> createEvent(){

        return ctx->{
            String eventName = ctx.request().getParam("name");
            String type = ctx.request().getParam("type");
            Integer maxAttendants = Integer.parseInt(ctx.request().getParam("maxAttendants"));
            BigDecimal amount = new BigDecimal(ctx.request().getParam("amount"));
            Date eventDate = getEventDate(ctx.request().getParam("eventDate"));
            String description = ctx.request().getParam("description");
            Event event = Event.builder()
                    .name(eventName)
                    .type(type)
                    .maxAttendants(maxAttendants)
                    .amount(amount)
                    .eventDate(eventDate)
                    .description(description)
                    .build();

            Event eventWithId = eventRepository.create(event);

            FlashHandler.addSuccess(ctx,"Event "+eventName+" created");
            doRedirect(ctx,"/events/show/"+eventWithId.getId());
        };
    }

    @RequestMapping(path="/show/:id")
    public Handler<RoutingContext> showEvent(){
        return ctx->{
            //TODO: findEventById
            ctx.put("eventId",ctx.pathParam("id"));
            ctx.put("view","events/show").next();
        };
    }

    private Date getEventDate(String eventDate) {
        try {
            return df.parse(eventDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }
}
