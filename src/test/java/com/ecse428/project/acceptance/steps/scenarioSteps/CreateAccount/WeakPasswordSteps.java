package com.ecse428.project.acceptance.steps.scenarioSteps.CreateAccount;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WeakPasswordSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I request to signup using a valid email but a password under 8 characters")
  public void i_request_to_signup_using_a_valid_email_but_a_password_under_characters() {
    User sentUser = new User(TestContext.valid_email, "1234567");
    context.setUser(sentUser);
    HttpEntity<User> userRequest = new HttpEntity<>(sentUser);
    ResponseEntity<String> response = restTemplate.exchange("/signup", HttpMethod.POST, userRequest, String.class);
    context.setResponse(response);
  }

  @Then("the system will not create a new account with that username")
  public void the_system_will_not_create_a_new_account_with_that_username() {
    assertFalse(userRepository.existsByEmail(context.getUser().getEmail()));
  }
}
