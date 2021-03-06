package com.ecse428.project.acceptance.steps.scenarioSteps.addAlcoholToInventory;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InvalidAlcoholAddedSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I select an invalid alcohol type")
  public void i_select_a_alcohol() {
    Alcohol invaidAlcohol = new Alcohol(TestContext.invalid_name);

    context.setChosenAlcohol(invaidAlcohol);
    assertNotNull(context.getChosenAlcohol());
  }

  @Then("the system will notify me that the alcohol type is invalid")
  public void the_system_will_notify_me_that_the_alcohol_is_invalid() {
    assertEquals(HttpStatus.NOT_FOUND, context.getResponse().getStatusCode());

    assertTrue(context.getResponse().getBody().toString().contains("Alcohol not found with name " + TestContext.invalid_name + "."));
  }

  @Then("the new alcohol type will not be in my inventory")
  public void the_alcohol_will_not_be_in_my_inventory() {
    final String uri_req = "/api/user/{userId}/alcohol/";
    Map<String, String> params = new HashMap<String, String>();
    params.put("userId", context.getUser().getId().toString());

    ResponseEntity<Alcohol[]> response = restTemplate.exchange(uri_req, HttpMethod.GET, null, Alcohol[].class,
        params);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(Arrays.asList(response.getBody()).contains(context.getChosenAlcohol()));
  }

}