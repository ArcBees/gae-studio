Feature: Edit an Entity
  Scenario: Edit a Car
    Given I create a Car
    And I modify the Car
    Then the Car should be modified

  Scenario: Create a Car
    Given I create a Car
