Feature: Have I edited a to-do?
  I want to edit a to-do

  Scenario: Editing an already existing to-do
    Given The user has already created a to-do
    When The user edits the to-do information to "Edited title", "Edited description", "Edited tag" and "22-22-2222\n22:22"
    Then I view the edited to-do and I should see "Edited title", "Edited description", "Edited tag" and "22-22-2222\n22:22"