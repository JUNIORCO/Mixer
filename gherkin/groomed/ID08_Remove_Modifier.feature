#Author - Misha
Feature: Remove Modifier From Inventory

   As a user of Mixer
   I would like to remove a modifier from my inventory
   So that I can generate cocktail recipe recommendations based only on ingredients I possess

   Background: User is logged in and has modifier item(s) in inventory

      Given I am a signed up for Mixer
      And I am logged in to Mixer
      And I have item(s) in my inventory

   Scenario: Remove a valid modifier from inventory (Success Flow)

      When I select a modifier
      And the modifier exists in my inventory
      And I confirm removing it from my inventory
      Then the system will remove the modifier from my inventory

   Scenario: Modifier does not exist in inventory (Error Case)

      When I select a modifier
      And the modifier does not exist in my inventory
      Then the system will display an error
      And no item will be removed


