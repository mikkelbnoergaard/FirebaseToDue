Feature: Create a todo, revised
  The user wants to create a new todo

  Scenario: Creating a to-do item with all required fields
    Given That I press the "+" button
    Then the app should accept inputs
    When the user clicks the "Create" button
    Then the app should create the to-do item and add it to the user's to-do list

