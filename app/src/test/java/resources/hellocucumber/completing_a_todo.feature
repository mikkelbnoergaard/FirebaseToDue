Feature: Can I complete a to-do?
  I want to complete a to-do

  Scenario: Marking a to-do as completed
    Given The user has already created a to-do that needs to be finished
    When The user edits the to-do finished value to true
    Then I view the edited to-do and I should see "true"

