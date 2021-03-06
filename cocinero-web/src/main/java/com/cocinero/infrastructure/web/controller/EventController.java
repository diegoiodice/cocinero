package com.cocinero.infrastructure.web.controller;

import com.cocinero.domain.*;
import com.cocinero.infrastructure.web.RequestMapping;
import com.cocinero.repository.AddressRepository;
import com.cocinero.repository.EventRepository;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@RequestMapping(path="/events")
public class EventController extends AbstractController{

    private DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(path="/")
    public Handler<RoutingContext> listEvents(){
        return ctx-> ctx.put("events",eventRepository.findUpcomingEvents()).put("view","events/index").next();
    }

    @RequestMapping(path="/new")
    public Handler<RoutingContext> newEventView(){
        return ctx->ctx.put("view","events/new").reroute("");
    }

    @RequestMapping(path="/",method = HttpMethod.POST)
    public Handler<RoutingContext> createEvent(){

        return ctx->{
            User currentUser = ctx.get("user");

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
                    .host(Host.builder().id(currentUser.getId()).build())
                    .build();

            //event.getAttendants().add(Attendant.builder().id(currentUser.getId()).build());

            Event eventWithId = eventRepository.create(event);

            getFlashHandler().addSuccess(ctx,"event","Event "+eventName+" created");
            doRedirect(ctx,"/events/"+eventWithId.getId());
        };
    }

    @RequestMapping(path="/:id/addresses/new")
    public Handler<RoutingContext> newEventAddressView(){
        return ctx->ctx.put("event",eventRepository.findById(ctx.pathParam("id")))
                .put("view","addresses/new").next();
    }

    @RequestMapping(path="/:id/addresses",method = HttpMethod.POST)
    public Handler<RoutingContext> addNewAddressToEvent(){

        return ctx->{

            String postCode = ctx.request().getParam("postCode");
            String addressLine1 = ctx.request().getParam("addressLine1");
            String addressLine2 = ctx.request().getParam("addressLine2");
            String city = ctx.request().getParam("city");
            String country = ctx.request().getParam("country");

            Address address = Address.builder()
                    .addressLine1(addressLine1)
                    .addressLine2(addressLine2)
                    .city(city)
                    .postCode(postCode)
                    .country(country).build();

            Address addressWithId = addressRepository.create(address);
            Event event = eventRepository.findById(ctx.pathParam("id"));
            event = event.toBuilder().address(addressWithId).build();
            event = eventRepository.save(event);
            doRedirect(ctx,"/events/show/"+event.getId());
        };
    }

    @RequestMapping(path="/:id/addresses/:addressId/add",method = HttpMethod.PUT)
    public Handler<RoutingContext> addExistingAddressToEvent(){

        return ctx->{
            Event currentEvent = eventRepository.findById(ctx.pathParam("id"));
            Address address = addressRepository.findById(ctx.pathParam("addressId"));
            Event event = currentEvent.toBuilder()
                    .address(address)
                    .build();
            event = eventRepository.save(event);
            doRedirect(ctx,"/events/"+event.getId());
        };
    }

    @RequestMapping(path="/:id")
    public Handler<RoutingContext> showEvent(){
        return ctx->{
            ctx.put("event",eventRepository.findById(ctx.pathParam("id")));
            ctx.put("view","events/show").next();
        };
    }

    private Date getEventDate(String eventDate) {
        return Date.from(LocalDateTime.parse(eventDate, df).atZone(ZoneId.systemDefault()).toInstant());
    }
}
