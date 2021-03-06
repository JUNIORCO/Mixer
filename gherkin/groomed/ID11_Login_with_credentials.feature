# Author - Louis
Feature: Login with credentials

  As a user of Mixer Cocktail Recommender with an account
  I want to login using my credentials
  So that I can use the Mixer webapp until I logout.

  Scenario: User logged into account (Success Flow)

    When I submit a valid email and password pair
    Then the system will confirm the validity of the credentials
    And will return a valid login token
    And it will be automatically stored in my browser
    And I will be redirected to the main page of Mixer

  Scenario: Invalid credentials (Error Flow)

    When I submit an incorrect email and password pair
    Then the system will find that the password is incorrect
    And a new login token will not be created
    And the system will return the error 'Invalid credentials. Please try again.'

  Scenario: Account does not exist (Error Flow)

    When I submit a non-existant email and password pair
    Then the system will find that there is no account linked to the specified email
    And a new login token will not be created
    And the system will return the error 'Invalid credentials. Please try again.'

  Scenario: Empty email or password (Error Flow)

    When I enter an empty <cred>
      | cred     |
      | email    |
      | password |
    Then the system will return the error <cred>' cannot be Empty.'