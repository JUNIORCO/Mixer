package com.ecse428.project.unit.controller;

import com.ecse428.project.auth.UserDetailsServiceImpl;
import com.ecse428.project.controller.AuthController;
import com.ecse428.project.service.IUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService service;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void givenValidUserRequest_whenPostSignup_thenSuccess() throws Exception {

        given(service.postSignup(any())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Successful signup."));

        mvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", "johndoe@hotmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string("Successful signup."));
    }

    @Test
    public void givenMissingEmail_whenPostSignup_thenError() throws Exception {

        given(service.postSignup(any())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Successful signup."));

        mvc.perform(MockMvcRequestBuilders.post("/signup").content(String.format("{\"password\":\"%s\"}", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(content().json("{'email': 'Email is mandatory'}"));
    }

    @Test
    public void givenMissingPassword_whenPostSignup_thenError() throws Exception {

        given(service.postSignup(any())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Successful signup."));

        mvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(String.format("{\"email\":\"%s\"}", "johndoe@hotmail.com"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(content().json("{'password': 'Password is mandatory'}"));
    }

    @Test
    public void givenShortPassword_whenPostSignup_thenSuccess() throws Exception {

        given(service.postSignup(any())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Successful signup."));

        mvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", "johndoe@hotmail.com", "1234567"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(content().json("{'password': 'Password must be at least 8 characters'}"));
    }

    @Test
    public void givenNoBody_whenPostSignup_thenError() throws Exception {

        given(service.postSignup(any())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Successful signup."));
        mvc.perform(MockMvcRequestBuilders.post("/signup").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        // can't test automatically generated error messages with MockMvc since Spring
        // Boot handles them:
        // https://github.com/spring-projects/spring-framework/issues/17290
    }
}