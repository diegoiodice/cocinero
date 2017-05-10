package com.cocinero.infrastructure.web.security;

import com.cocinero.domain.UserRepository;
import io.vertx.ext.auth.AuthProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerUTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthProvider authProvider;

    @InjectMocks
    private AuthController testObject;

    @Test
    public void renderRegisterView() throws Exception {

    }

    @Test
    public void register() throws Exception {

    }

    @Test
    public void loginRedirect() throws Exception {

    }

    @Test
    public void renderLoginView() throws Exception {

    }

    @Test
    public void login() throws Exception {

    }

    @Test
    public void loginFailureHandler() throws Exception {

    }

    @Test
    public void logout() throws Exception {

    }

    @Test
    public void refreshUser() throws Exception {

    }

    @Test
    public void setAuthProvider() throws Exception {

    }

}