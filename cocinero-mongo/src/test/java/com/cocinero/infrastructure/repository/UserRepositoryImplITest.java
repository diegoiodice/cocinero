package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.SpringIntegrationTest;
import com.cocinero.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryImplITest extends SpringIntegrationTest{

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
}