# Author - Sami / Louis
Feature: Create Account

    As somebody interested in Mixer,
    I want to create account with Mixer
    so that I can use the Mixer webapp.

    Scenario: User account generated (Success Flow)

        When I request to signup using a valid email and password
        Then the system will return the message 'Successful signup.' with code '200'
        And the system will have created my account with the correct email and an encrypted password

    Scenario: Email already in the database (Error Flow)

        Given my email is already linked to an account
        When I request to signup using a valid email and password
        Then the system will not create a new account or edit my existing account
        And the system will return the message 'Email already taken.' with code '409'

    Scenario: Weak password (Error Flow)
    
        When I request to signup using a valid email but a password under 8 characters
        Then the system will not create a new account with that username
        And the system will return the message 'Password must be at least 8 characters' with code '400'