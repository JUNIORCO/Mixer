# Author - Victor
Feature: Add Alcohol New Type To Inventory

    As a user of Mixer
    I would like to add a new type of alcohol to my inventory
    So that I can check which new cocktails I can generate with it

    Background: User is logged in

        Given I am a signed up for Mixer
        And I am logged in to Mixer

    Scenario: Valid alcohol type added (Success Flow)

        When I select a valid alcohol type
        And I confirm adding the alcohol to my inventory
        Then the system will add the new alcohol to my inventory

    Scenario: Invalid alcohol type added (Error Flow)

        When I select an invalid alcohol type
        And I confirm adding the alcohol to my inventory
        Then the system will notify me that the alcohol type is invalid
        And the new alcohol type will not be in my inventory

    Scenario: Alcohol type added already in inventory (Error Flow)

        When I select a valid alcohol type
        And the alcohol type is already in my inventory
        And I confirm adding the alcohol to my inventory
        Then the system will notify me that the alcohol type already exists
        And only one instance of the alcohol type will be in my inventory