package com.ecse428.project.acceptance.steps.commonSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ecse428.project.acceptance.TestContext;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Then;

public class ReturnedMessage {
  @Autowired
  private TestContext context;

  @Then("the system will return the message {string} with code {string}")
  public void the_system_will_return_the_message_with_code(String message, String code) {
    assertEquals(Integer.parseInt(code), context.getResponse().getStatusCode().value());

    assertTrue(context.getResponse().getBody().contains(message));
  }
}
