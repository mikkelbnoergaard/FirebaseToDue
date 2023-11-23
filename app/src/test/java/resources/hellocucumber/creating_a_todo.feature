Feature: Have I created a to-do?
  I want to create a todo

  Scenario: Creating a to-do item with all required fields
    Given The user creates a to-do with title "Cucumber title", description "Cucumber description", tag "Cucumber tag" and dueDate "20-11-2023\n22:30"
    Then The to-do object is created
    When I view that to-do I should see "Cucumber title", "Cucumber description", "Cucumber tag" and "20-11-2023\n22:30"




