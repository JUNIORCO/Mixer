package com.ecse428.project.acceptance.steps.scenarioSteps.CreateAccount;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import io.cucumber.java.en.Then;

public class ValidCreateAccountSteps extends CucumberConfig {

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @Then("the system will have created my account with the correct email and an encrypted password")
  public void the_system_will_have_created_my_account_with_the_correct_email_and_an_encrypted_password() {
    User dbUser = userRepository.findByEmail(TestContext.valid_email).get();
    assertNotNull(dbUser);
    assertEquals(TestContext.valid_email, dbUser.getEmail());
    assertNotEquals(TestContext.valid_password, dbUser.getPassword()); // encrypted
  }
}
