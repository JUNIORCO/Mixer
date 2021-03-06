# Author - Sami
Feature: Add Modifier To Inventory

    As a user of Mixer
    I would like to add the modifiers that I have on my bar to my inventory
    So that I can generate a cocktail

    Background: User is logged in

        Given I am a signed up for Mixer
        And I am logged in to Mixer

    Scenario: Valid modifier added (Success Flow)

        When I select a valid modifier
        And I confirm adding the modifier to my inventory
        Then the system will add the modifier to my inventory

    Scenario: Invalid modifier added (Error Flow)

        When I select an invalid modifier
        And I confirm adding the modifier to my inventory
        Then the system will notify me that the modifier is invalid
        And the modifier will not be in my inventory

    Scenario: Modifier added already in inventory (Error Flow)

        When I select a valid modifier
        And the modifier is already in my inventory
        And I confirm adding the modifier to my inventory
        Then the system will notify me that the modifier already exists
        And only one instance of the modifier will be in my inventory
