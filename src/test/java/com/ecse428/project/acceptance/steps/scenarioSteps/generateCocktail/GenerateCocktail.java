package com.ecse428.project.acceptance.steps.scenarioSteps.generateCocktail;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Cocktail;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class GenerateCocktail extends CucumberConfig {

    @Autowired
    private TestContext context;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @And("I have an alcohol in my inventory")
    public void i_have_alcohol_in_inventory() {
        final String uri_req = "/api/user/{userId}/alcohol/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", context.getUser().getId().toString());

        restTemplate.exchange(uri_req, HttpMethod.GET, null, Alcohol[].class, params);

        ResponseEntity<Alcohol[]> response = restTemplate.getForEntity("/api/alcohol", Alcohol[].class);
        context.setChosenAlcohol(response.getBody()[0]);
    }

    @And("I have a modifier in my inventory")
    public void i_have_modifier_in_inventory() {
        final String uri_req = "/api/user/{userId}/modifier/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", context.getUser().getId().toString());

        restTemplate.exchange(uri_req, HttpMethod.GET, null, Modifier[].class, params);

        ResponseEntity<Modifier[]> response = restTemplate.getForEntity("/api/modifier", Modifier[].class);
        context.setChosenModifier(response.getBody()[0]);
    }

    @But("I did not specify a modifier")
    public void i_did_not_specify_modifier() {
        context.setChosenModifier(new Modifier("", Modifier.ModifierType.SODA));
    }

    @And("I specify a non-existing alcohol")
    public void i_did_not_specify_alcohol_or_modifier() {
        context.setChosenAlcohol(new Alcohol("SHLACK"));
    }

    @When("I try to generate a cocktail")
    public void try_to_generate_cocktail() {
        String uri_req = "/api/cocktail";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("cName", "");
        map.add("alcohol", context.getChosenAlcohol().getName());
        map.add("modifier", context.getChosenModifier().getName());
        map.add("tasteType", "");
        map.add("strengthType", "");
        map.add("servingSize", "");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        context.setResponse(restTemplate.postForEntity(uri_req, request, String.class));
    }

    @Then("the system will successfully return a list of cocktails")
    public void successfully_finds_list_of_cocktails() {
        assertEquals(HttpStatus.OK, context.getResponse().getStatusCode());
        assertTrue(Arrays.asList(context.getResponse().getBody()).size() > 0);
    }

    @Then("the system fails to find any cocktail")
    public void no_cocktail_generated() {
        System.out.println("Response ::: " + context.getResponse().toString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, context.getResponse().getStatusCode());
    }

}
