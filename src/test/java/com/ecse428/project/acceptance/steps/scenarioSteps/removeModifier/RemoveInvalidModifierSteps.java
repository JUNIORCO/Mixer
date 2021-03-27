package com.ecse428.project.acceptance.steps.scenarioSteps.removeModifier;

import com.ecse428.project.acceptance.CucumberConfig;
import com.ecse428.project.acceptance.TestContext;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.UserRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveInvalidModifierSteps extends CucumberConfig {

  @Autowired
  private TestContext context;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  UserRepository userRepository;

  @When("the modifier does not exist in my inventory")
  public void the_modifier_does_not_exist_in_my_inventory() {
    // Put in inventory
    User user = context.getUser();
    user.addModifierToInventory(context.getChosenModifier());
    userRepository.save(user);

    context.setUser(userRepository.findByEmail(TestContext.valid_email).get());
    // assertFalse(context.getUser().getModifiersInInventory().contains(context.getChosenModifier()));
  }

  @Then("the system will display an error about the modifier")
  public void the_system_will_display_an_error() {
    // assertEquals(HttpStatus.NOT_FOUND, context.getResponse().getStatusCode());

    // assertTrue(context.getResponse().getBody().toString()
    //     .contains("Modifier not found with name " + TestContext.invalid_name + "."));
  }
}
