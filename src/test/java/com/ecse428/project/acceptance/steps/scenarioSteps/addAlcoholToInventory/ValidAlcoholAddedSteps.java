package com.ecse428.project.acceptance.steps.scenarioSteps.addAlcoholToInventory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.Alcohol;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidAlcoholAddedSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I select a valid alcohol type")
  public void i_select_an_alcohol() {
    ResponseEntity<Alcohol[]> response = restTemplate.getForEntity("/api/alcohol", Alcohol[].class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Select one
    context.setChosenAlcohol(response.getBody()[0]);
    assertNotNull(context.getChosenAlcohol());
  }

  @Then("the system will add the new alcohol to my inventory")
  public void the_system_will_add_the_alcohol_to_my_inventory() {
    assertEquals(HttpStatus.OK, context.getResponse().getStatusCode());
    final String uri_req = "/api/user/{userId}/alcohol/";
    Map<String, String> params = new HashMap<String, String>();
    params.put("userId", context.getUser().getId().toString());

    ResponseEntity<Alcohol[]> response = restTemplate.exchange(uri_req, HttpMethod.GET, null, Alcohol[].class,
        params);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertTrue(Arrays.asList(response.getBody()).contains(context.getChosenAlcohol()));
  }
}
