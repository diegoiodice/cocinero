package com.cocinero.infrastructure.web;

import com.cocinero.infrastructure.SpringIntegrationTest;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;


public class HomeControllerITest extends SpringIntegrationTest{

    @Test
    public void renderHomeViewTest() throws Exception{
        beginAt("/index.html");
        assertTitleEquals("Cocinero Home");
        assertTextPresent("Welcome to Cocinero");
    }
}