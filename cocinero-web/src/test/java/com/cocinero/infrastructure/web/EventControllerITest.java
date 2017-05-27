package com.cocinero.infrastructure.web;

import com.cocinero.domain.Event;
import com.cocinero.infrastructure.SpringIntegrationTest;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;


@Ignore
public class EventControllerITest extends SpringIntegrationTest{

    @Autowired
    private DateFormat df;

    @Before
    public void setUp() throws Exception {
        setBaseUrl("http://localhost:9000");
    }

    @Test
    public void renderNewEventViewTest() throws Exception{
        register("eventuser@mail.com","test123");
        gotoPage("/events/new");
        assertTitleEquals("Cocinero New Event");
        assertTextPresent("New Event");
    }

    @Test
    public void createNewEventTest() throws Exception{
        register("eventuser1@mail.com","test123");
        gotoPage("/events/new");
        assertTitleEquals("Cocinero New Event");
        setTextField("type", "dinner");
        setTextField("maxAttendants", "10");
        setTextField("amount", "5");
        setTextField("name", "BBQ Dinner");
        setTextField("description", "dinner con los panas");
        setTextField("eventDate", "03/23/2017");
        submit();
        assertTitleEquals("Cocinero event detail");
        assertTextPresent("Event BBQ Dinner created");
        assertTextPresent("My Event");
    }

    @Test
    public void createNewEventAddNewAddressTest() throws Exception{
        register("eventuser2@mail.com","test123");
        gotoPage("/events/new");
        assertTitleEquals("Cocinero New Event");
        setTextField("type", "dinner");
        setTextField("maxAttendants", "10");
        setTextField("amount", "5");
        setTextField("name", "BBQ Dinner");
        setTextField("description", "dinner con los panas");
        setTextField("eventDate", "03/23/2017");
        submit();

        clickLinkWithExactText("Add new address");

        setTextField("postCode", "SW152JW");
        setTextField("addressLine1", "flat 1");
        setTextField("addressLine2", "fulham street");
        setTextField("city", "london");
        setTextField("country", "UK");

        submit();

    }

    /*@Test
    public void createNewEventAddExistingAddressTest() throws Exception{
        register("eventuser1@mail.com","test123");
        gotoPage("/events/new");
        assertTitleEquals("Cocinero New Event");
        setTextField("type", "dinner");
        setTextField("maxAttendants", "10");
        setTextField("amount", "5");
        setTextField("name", "BBQ Dinner");
        setTextField("description", "dinner con los panas");
        setTextField("eventDate", "03/23/2017");
        submit();
        assertTitleEquals("Cocinero event detail");
        assertTextPresent("Event BBQ Dinner created");
        assertTextPresent("My Event");
    }*/

    @Test
    public void userSubscribesToEvent() throws Exception{
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault()).plusDays(1);
        Date tomorrow = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        String eventId = createEvent("userSubscribesToEvent@mail.com",Event.builder()
                .type("dinner")
                .maxAttendants(10)
                .amount(new BigDecimal(5))
                .name("my bbq")
                .description("my bbq with friends")
                .eventDate(tomorrow)
                .build());

        register("guest1@mail.com","test123");
        gotoPage("/events/"+eventId+"/subscribe");
        assertTitleEquals("Cocinero upcoming events");
        assertTextPresent("Guest subscribed successfully");
    }

    @Test
    public void guestNotLoggedInTriesToSubscribeToEvent_redirectsToLogin() throws Exception{

        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault()).plusDays(1);
        Date tomorrow = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        String eventId = createEvent("notLoggedInTest@mail.com",Event.builder()
                .type("dinner")
                .maxAttendants(10)
                .amount(new BigDecimal(5))
                .name("my bbq")
                .description("my bbq with friends")
                .eventDate(tomorrow)
                .build());
        gotoPage("/events/"+eventId+"/subscribe");
        assertTitleEquals("Cocinero Login");
    }

    public String createEvent(String email, Event event){
        register(email,"test123");
        gotoPage("/events/new");
        setTextField("type",event.getType());
        setTextField("maxAttendants", event.getMaxAttendants().toString());
        setTextField("amount", event.getAmount().toPlainString());
        setTextField("name", event.getName());
        setTextField("description", event.getDescription());
        setTextField("eventDate", df.format(event.getEventDate()));
        submit();
        String eventId = JWebUnit.getServerResponse().split("Location")[1].split("\n")[0].split("/")[5];
        clickLinkWithExactText("Logout");
        return eventId;
    }
}