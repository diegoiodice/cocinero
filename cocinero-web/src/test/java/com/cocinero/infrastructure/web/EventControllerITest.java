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
    public void testRegisterUser() throws Exception{

        register("user@mail.com","test123");
        gotoPage("/event/new");
        assertTitleEquals("Cocinero New Event");
        assertTextPresent("NEW EVENT");
    }
}