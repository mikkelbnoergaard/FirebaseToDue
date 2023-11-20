Feature: Have I created a todo?
  I want to create a todo

  Scenario: Creating a to-do item with all required fields
    Given I create a to-do
    Then The to-do object is created
    When I view that to-do I should see "Cucumber title"