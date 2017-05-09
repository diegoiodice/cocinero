package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.repository.mongo.MongoUserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplUTest {

    @Mock
    private MongoUserRepository mongoUserRepository;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private UserRepositoryImpl testObject;

    @Before
    public void setUp() throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void create() throws Exception {
        testObject.create(User.builder().build());
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void find() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void findByEmail() throws Exception {

    }

}