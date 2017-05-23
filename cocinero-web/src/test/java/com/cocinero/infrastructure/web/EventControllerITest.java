package com.cocinero.infrastructure.web;

import com.cocinero.infrastructure.SpringIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.*;


public class EventControllerITest extends SpringIntegrationTest{

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
}