package com.cocinero.infrastructure.web.security;

import com.cocinero.infrastructure.Server;
import com.cocinero.infrastructure.SpringIntegrationTest;
import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jayway.restassured.RestAssured.given;


public class AuthControllerITest extends SpringIntegrationTest {

    @Autowired
    private Server server;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = 9000;
        //System.setProperty("http.port","9000");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testRegisterUser() {

        given()
                .queryParam("email","user@gmail.com")
                .queryParam("password","123")
                .post("/register")
                .then()
                .statusCode(302);
    }
}