package com.ecse428.project.acceptance.steps.scenarioSteps.addAlcoholToInventory;

import java.util.HashMap;
import java.util.Map;

import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import io.cucumber.java.en.When;

public class CommonSteps {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("I confirm adding the alcohol to my inventory")
  public void i_add_confirm_adding_it_to_my_inventory() {
    // Send request
    final String uri_req = "/api/user/{userId}/alcohol/{alcoholName}";
    Map<String, String> params = new HashMap<String, String>();
    params.put("userId", context.getUser().getId().toString());
    params.put("alcoholName", context.getChosenAlcohol().getName());

    context.setResponse(restTemplate.exchange(uri_req, HttpMethod.PUT, null, String.class, params));
  }
}
