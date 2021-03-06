# Author - Yiming
Feature: Set taste option(s)

	As a user of Mixer Cocktail Recommender
	I would like to set one or multiple taste preference(s) before querying cocktail recipes

	Background: User is logged in

		Given I am a signed up for Mixer
		And I am logged in to Mixer
		And I am prompted to indicate my optional taste preference(s) in the recommender questionnaire
		And the options listed are
			| SWEET | SOUR | BITTER | SPICY | MINTY | FRUITY |

	Scenario: Valid taste preference(s) set (Success Flow)

		Given there exists valid taste options in the system
		When I select the taste option(s)
		Then the system will save the taste option(s) selected as preference
		And the taste option(s) set cannot be selected again