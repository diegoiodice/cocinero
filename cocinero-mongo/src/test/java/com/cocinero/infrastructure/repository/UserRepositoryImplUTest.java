package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.repository.schemas.MongoUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplUTest {

    public static final String USER_ID = "123456789012345678901234";
    @Mock
    private MongoUserRepository mongoUserRepository;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private UserRepositoryImpl testObject;

    private MongoUser mongoUser;
    private User user;
    private User userWithId;

    @Before
    public void setUp() throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        user = User.builder()
                .email("user@mail.com")
                .password("123456")
                .build();
        userWithId = User.builder()
                .id(USER_ID)
                .email("user@mail.com")
                .password("123456")
                .build();
        mongoUser = new MongoUser();
        mongoUser.setId(new ObjectId(USER_ID));
        mongoUser.setEmail("user@mail.com");
        mongoUser.setPassword("123456");
        doReturn(mongoUser).when(objectMapper).convertValue(user,MongoUser.class);
    }

    @Test
    public void create() throws Exception {
        when(mongoUserRepository.save(any(MongoUser.class))).thenReturn(mongoUser);
        assertThat(testObject.create(user).getId()).isEqualTo(USER_ID);
    }

    @Test
    public void save() throws Exception {
        when(mongoUserRepository.save(any(MongoUser.class))).thenReturn(mongoUser);
        assertThat(testObject.save(user).getId()).isEqualTo(USER_ID);
    }

    @Test
    public void find() throws Exception {
        List<MongoUser> mongoUsers = new ArrayList<>();
        mongoUsers.add(mongoUser);
        when(mongoUserRepository.findAll()).thenReturn(mongoUsers);
        assertThat(testObject.find()).hasSameSizeAs(mongoUsers);
        assertThat(testObject.find()).contains(userWithId);
    }

    @Test
    public void findById() throws Exception {
        when(mongoUserRepository.findById(USER_ID)).thenReturn(mongoUser);
        assertThat(testObject.findById(USER_ID)).isEqualTo(userWithId);
    }

    @Test
    public void findByEmail() throws Exception {
        when(mongoUserRepository.findByEmail("user@mail.com")).thenReturn(mongoUser);
        assertThat(testObject.findByEmail("user@mail.com")).isEqualTo(userWithId);
    }

}