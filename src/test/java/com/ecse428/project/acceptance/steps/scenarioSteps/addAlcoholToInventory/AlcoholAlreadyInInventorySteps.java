package com.ecse428.project.acceptance.steps.scenarioSteps.addAlcoholToInventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AlcoholAlreadyInInventorySteps {

    @Autowired
    private TestContext context;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @When("the alcohol type is already in my inventory")
    public void the_alcohol_type_is_already_in_my_inventory() {
        // Put in inventory
        User user = context.getUser();
        user.addAlcoholToInventory(context.getChosenAlcohol());
        userRepository.save(user);

        context.setUser(userRepository.findByEmail(TestContext.valid_email).get());
        assertTrue(context.getUser().getAlcoholInInventory().contains(context.getChosenAlcohol()));
    }

    @Then("the system will notify me that the alcohol type already exists")
    public void the_system_will_notify_me_that_the_alcohol_type_already_exists() {
        assertEquals(HttpStatus.OK, context.getResponse().getStatusCode());

        assertTrue(context.getResponse().getBody().toString().contains("Alcohol already in inventory."));
    }

    @Then("only one instance of the alcohol type will be in my inventory")
    public void only_one_instance_of_the_alcohol_type_will_be_in_my_inventory() {
        assertEquals(HttpStatus.OK, context.getResponse().getStatusCode());
        final String uri_req = "/api/user/{userId}/alcohol/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", context.getUser().getId().toString());

        ResponseEntity<Alcohol[]> response = restTemplate.exchange(uri_req, HttpMethod.GET, null, Alcohol[].class,
                params);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Alcohol> list = Arrays.asList(response.getBody());
        assertEquals(1, list.size());
        assertTrue(list.contains(context.getChosenAlcohol()));
    }
}
