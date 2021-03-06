package com.ecse428.project.acceptance.steps.scenarioSteps.addModifierToInventory;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.Modifier.ModifierType;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InvalidModifierAddedSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I select an invalid modifier")
  public void i_select_a_modifier() {
    // Creating invalid modifier
    Modifier invaidModifier = new Modifier(TestContext.invalid_name, ModifierType.JUICE);

    context.setChosenModifier(invaidModifier);
    assertNotNull(context.getChosenModifier());
  }

  @Then("the system will notify me that the modifier is invalid")
  public void the_system_will_notify_me_that_the_modifier_is_invalid() {
    assertEquals(HttpStatus.NOT_FOUND, context.getResponse().getStatusCode());

    assertTrue(context.getResponse().getBody().toString().contains("Modifier not found with name " + TestContext.invalid_name + "."));
  }

  @Then("the modifier will not be in my inventory")
  public void the_modifier_will_not_be_in_my_inventory() {
    final String uri_req = "/api/user/{userId}/modifier/";
    Map<String, String> params = new HashMap<String, String>();
    params.put("userId", context.getUser().getId().toString());

    ResponseEntity<Modifier[]> response = restTemplate.exchange(uri_req, HttpMethod.GET, null, Modifier[].class,
        params);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(Arrays.asList(response.getBody()).contains(context.getChosenModifier()));

  }

}