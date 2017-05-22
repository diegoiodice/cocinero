package com.cocinero.infrastructure.web.security;

import com.cocinero.infrastructure.SpringIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class AuthControllerITest extends SpringIntegrationTest {

    @Before
    public void setUp() throws Exception {
        setBaseUrl("http://localhost:9000");
    }

    @Test
    public void testRegisterUser() throws Exception{

        register("user@mail.com","test123");
        assertTitleEquals("Cocinero Home");
        assertLinkPresentWithExactText("Signed In As user@mail.com");
        assertLinkPresentWithExactText("Logout");
    }

    @Test
    public void testLoginUser() throws Exception{

        register("user_logi@mail.com","test123");
        clickLinkWithExactText("Logout");
        assertLinkPresentWithExactText("Login");

        beginAt("/index.html");
        clickLinkWithExactText("Login");
        assertTitleEquals("Cocinero Login");
        setTextField("email", "user_logi@mail.com");
        setTextField("password", "test123");
        submit();
        assertLinkPresentWithExactText("Signed In As user_logi@mail.com");
        assertLinkPresentWithExactText("Logout");
    }

    @Test
    public void testLoginUserWrongCredentials() throws Exception{

        beginAt("/index.html");
        clickLinkWithExactText("Login");
        assertTitleEquals("Cocinero Login");
        setTextField("email", "wrong_user@mail.com");
        setTextField("password", "test123");
        submit();
        assertTextPresent("Invalid Credentials");
    }

    protected void register(String email,String password){
        beginAt("/index.html");
        clickLinkWithExactText("Sign Up");
        setTextField("email", email);
        setTextField("password", password);
        submit();
    }
}