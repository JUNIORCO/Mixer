package com.ecse428.project.acceptance.steps.scenarioSteps.addModifierToInventory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidModifierAddedSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I select a valid modifier")
  public void i_select_a_modifier() {
    // Query all modifiers
    ResponseEntity<Modifier[]> response = restTemplate.getForEntity("/api/modifier", Modifier[].class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Select one
    context.setChosenModifier(response.getBody()[0]);
    assertNotNull(context.getChosenModifier());
  }

  @Then("the system will add the modifier to my inventory")
  public void the_system_will_add_the_modifier_to_my_inventory() {
    assertEquals(HttpStatus.OK, context.getResponse().getStatusCode());
    final String uri_req = "/api/user/{userId}/modifier/";
    Map<String, String> params = new HashMap<String, String>();
    params.put("userId", context.getUser().getId().toString());

    ResponseEntity<Modifier[]> response = restTemplate.exchange(uri_req, HttpMethod.GET, null, Modifier[].class,
        params);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertTrue(Arrays.asList(response.getBody()).contains(context.getChosenModifier()));
  }
}
