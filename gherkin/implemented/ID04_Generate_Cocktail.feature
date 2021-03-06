# Author - Hong
Feature: Generate cocktail choice based on inventory and settings

  As a user of Mixer
  I would like to get cocktail recommendation from given inventory and settings
  So that I can start making a cocktail

  Background: User exists

    Given I am a signed up for Mixer
    Given I am logged in to Mixer

  Scenario: Cocktail generated with both alcohol and modifier (Success Flow)

    And I have an alcohol in my inventory
    And I have a modifier in my inventory
    When I try to generate a cocktail
    Then the system will successfully return a list of cocktails

  Scenario: Cocktail generated with only alcohol (Alternate Flow)

    And I have an alcohol in my inventory
    But I did not specify a modifier
    When I try to generate a cocktail
    Then the system will successfully return a list of cocktails

  Scenario: Cocktail not generated due to non-existing alcohol (Error Flow)

    And I specify a non-existing alcohol
    And I have a modifier in my inventory
    When I try to generate a cocktail
    Then the system fails to find any cocktail