package com.ecse428.project.acceptance.steps.scenarioSteps.CreateAccount;

import static org.junit.Assert.assertEquals;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;

import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import io.cucumber.java.en.Then;

public class EmailExistsSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @Then("the system will not create a new account or edit my existing account")
  public void the_system_will_not_create_a_new_account_or_edit_my_existing_account() {
    assertEquals(context.getUser(), userRepository.findByEmail(TestContext.valid_email).get()); 
  }
}