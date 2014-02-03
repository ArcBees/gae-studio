Feature: Delete an Entity
  Scenario: Delete a Car
    Given I create a Car for deletion
    And I delete the Car
    Then the Car should be deleted