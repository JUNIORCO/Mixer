# Author - Haowei
Feature: Set serving size of cocktail

	As a user of the Mixer
	I would like to set the cocktail serving size
	So that Mixer can provide more accurate cocktail recommendation

	Background: User is logged in and is asked to set cocktail serving size

		Given I am a signed up for Mixer
		And I am logged in to Mixer
		And I am prompted to indicate my preferred cocktail serving size
		And the options listed are
			| individual | group |

	Scenario: Valid cocktail serving size set (Normal Flow)

		When I select a serving size of <size>
		And I confirm my serving size selection
		Then the system will save the preferred cocktail serving size as <size>
		And the system will display the next prompt

		Examples:
			| size       |
			| individual |
			| group      |

	Scenario: No cocktail serving size selected (Error Flow)

		When I have no serving size option selected
		And I confirm my selection to go to the next prompt
		Then the system will display an error
		And the system will stay in the serving size selection prompt