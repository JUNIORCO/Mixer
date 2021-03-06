package com.ecse428.project.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.ecse428.project.model.User;
import com.ecse428.project.repository.AlcoholRepository;
import com.ecse428.project.repository.ModifierRepository;
import com.ecse428.project.repository.UserRepository;
import com.ecse428.project.service.IUserService;
import com.ecse428.project.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SignupUnitTest {

    @Autowired
    private IUserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AlcoholRepository a;

    @MockBean
    private ModifierRepository m;

    @MockBean
    private BCryptPasswordEncoder b;

    @TestConfiguration
    static class IAuthServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new IUserService();
        }
    }

    @Test
    public void whenUserDoesntExist_thenSignupSucceed() {
        User ourguy = new User("johndoe@gmail.com", "12345678");
        when(userRepository.existsByEmail(ourguy.getEmail())).thenReturn(false);

        var res = userService.postSignup(ourguy);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals("Successful signup.", res.getBody());
    }

    @Test
    public void whenUserExists_thenSignupFail() {
        User ourguy = new User("johndoe@gmail.com", "12345678");
        when(userRepository.existsByEmail(ourguy.getEmail())).thenReturn(true);

        var res = userService.postSignup(ourguy);
        assertEquals(HttpStatus.CONFLICT, res.getStatusCode());
        assertEquals("Email already taken.", res.getBody());
    }
}