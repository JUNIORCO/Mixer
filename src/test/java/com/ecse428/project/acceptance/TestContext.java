package com.ecse428.project.acceptance;

import com.ecse428.project.acceptance.steps.scenarioSteps.loginWithCredentials.LoginCreds;
import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;

@Component
@ScenarioScope
public class TestContext {

  public static String valid_email = "jimmy@hotmail.com";
  public static String valid_password = "12345678";
  public static String invalid_name = "Invalid Name";
  public static String invalid_password = "87654321";

  private ResponseEntity<String> response;
  private Modifier chosenModifier;
  private Alcohol chosenAlcohol;
  private User user;
  private LoginCreds logincreds;
  private String selectedCocktail;

  public ResponseEntity<String> getResponse() {
    return this.response;
  }

  public void setResponse(ResponseEntity<String> response) {
    this.response = response;
  }

  public Modifier getChosenModifier() {
    return this.chosenModifier;
  }

  public void setChosenModifier(Modifier chosenModifier) {
    this.chosenModifier = chosenModifier;
  }

  public Alcohol getChosenAlcohol() {
    return this.chosenAlcohol;
  }

  public void setChosenAlcohol(Alcohol chosenAlcohol) {
    this.chosenAlcohol = chosenAlcohol;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LoginCreds getLoginCreds() {
    return this.logincreds;
  }

  public void setLoginCreds(LoginCreds logincreds) {
    this.logincreds = logincreds;
  }

  public String getSelectedCocktail() {
    return this.selectedCocktail;
  }

  public void setSelectedCocktail(String selectedCocktail) {
    this.selectedCocktail = selectedCocktail;
  }
}