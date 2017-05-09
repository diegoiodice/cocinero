package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.domain.UserRepository;
import com.cocinero.infrastructure.AppConfiguration;
import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserRepositoryImplITest.UserRepositoryConfig.class,AppConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryImplITest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreate() throws Exception {
        User user = User.builder().email("user@gmail.com").password("123").build();
        assertThat(userRepository.create(user).getId()).isNotNull();
        log.info(user.getId());
    }

    @Test
    public void testCreateNoEmailNoPassword() throws Exception {
        User user = User.builder().build();
        try {
            userRepository.create(user);
            fail("Exception expected");
        } catch (ConstraintViolationException e) {
            final List<String> messages = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages).contains("email is mandatory","password is mandatory");
        }
    }

    @Test
    public void testCreateMalFormedEmail() throws Exception {
        User user = User.builder().email("wrong email").build();
        try {
            userRepository.create(user);
            fail("Exception expected");
        } catch (ConstraintViolationException e) {
            final List<String> messages = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            assertThat(messages).contains("not a well-formed email address");
        }
    }

    @Test
    public void testSave() throws Exception {
        User user = User.builder().email("user@mail.com").password("1234").build();
        user = userRepository.create(user);
        User userToUpdate = User.builder().id(user.getId()).email("newemail@mail.com").password(user.getPassword()).build();
        userRepository.save(userToUpdate);
        assertThat(userRepository.findByEmail(user.getEmail())).isNull();
        assertThat(userRepository.findByEmail(userToUpdate.getEmail())).isNotNull();
    }

    @Test
    public void testFind() throws Exception {
        for (int i=0; i < 3; i++){
            userRepository.create(User.builder().email("user"+i+"@mail.com").password("1234").build());
        }
        Collection<User> users = userRepository.find();
        assertThat(users).hasSize(3);
    }

    @Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindByEmail() throws Exception {
        User user = User.builder().email("user@gmail.com").password("123").build();
        userRepository.create(user);
        assertThat(userRepository.findByEmail(user.getEmail())).isNotNull();
    }

    @Test
    public void testFindByEmail_notFound() throws Exception {
        assertThat(userRepository.findByEmail("user@gmail.com")).isNull();
    }

    @Configuration
    @ComponentScan("com.cocinero.infrastructure.repository")
    @EnableMongoRepositories("com.cocinero.infrastructure.repository.mongo")
    public static class UserRepositoryConfig extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "cocinerodb-test";
        }

        @Override
        public Mongo mongo() throws Exception {
            return new Fongo(getDatabaseName()).getMongo();
        }
    }
}