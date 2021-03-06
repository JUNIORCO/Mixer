package com.ecse428.project.acceptance.steps.commonSteps;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;
import com.ecse428.project.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;

public class UserLoggedInSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Given("I am a signed up for Mixer")
  @Given("my email is already linked to an account")
  public void i_am_a_user() {
    // Create user and persist
    userService.postSignup(new User(TestContext.valid_email, TestContext.valid_password));

    // Get the user
    context.setUser(userRepository.findByEmail(TestContext.valid_email).get());
  }

  @Given("I am logged in to Mixer")
  public void i_am_logged_in_to_mixer() {
    HttpEntity<User> userRequest = new HttpEntity<>(new User(TestContext.valid_email, TestContext.valid_password));
    ResponseEntity<String> response = restTemplate.exchange("/login", HttpMethod.POST, userRequest, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Set the token in header for all following requests
    restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
      request.getHeaders().add("Authorization", response.getHeaders().get("Authorization").get(0));
      return execution.execute(request, body);
    }));
  }
}
