package com.ecse428.project.acceptance.steps.scenarioSteps.CreateAccount;

import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.When;

public class CommonSteps {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I request to signup using a valid email and password")
  public void i_request_to_signup_using_a_valid_email_and_password() {
    User sentUser = new User(TestContext.valid_email, TestContext.valid_password);
    HttpEntity<User> userRequest = new HttpEntity<>(sentUser);
    ResponseEntity<String> response = restTemplate.exchange("/signup", HttpMethod.POST, userRequest, String.class);
    context.setResponse(response);
  }
}
