# Author - Louis
Feature: Set Cocktail Strength

  As a User of the Mixer Cocktail Recommender
  I would like to set my preferred cocktail alcohol strength
  So that Mixer can better recommend me a cocktail that is fit to my taste

  Background: User is logged in and is prompted for cocktail strength

    Given I am a signed up for Mixer
    And I am logged in to Mixer
    And I am prompted to indicate my preferred cocktail alcohol strength in the recommender questionnaire
    And the options listed are
      | No preference | Weak | Medium | Strong |

  Scenario: Valid cocktail strength option set (Success Flow)

    When I select a cocktail strength of <strength>
    And I confirm my selection and request to go to the next prompt
    Then the system will show the next prompt
    And the system will have saved my selection when I request to go backwards in the questionnaire
    And the system will generate a cocktail with <strength> alcohol strength at the end of the questionnaire

    Examples:
      | strength      |
      | No preference |
      | Weak          |
      | Medium        |
      | Strong        |

  Scenario: No cocktail strength option selected (Error Flow)

    When I have no option selected
    And I confirm my selection and request to go to the next prompt
    Then the system will show the error 'Please select a cocktail strength preference.'
    And the system will remain at the cocktail strength prompt