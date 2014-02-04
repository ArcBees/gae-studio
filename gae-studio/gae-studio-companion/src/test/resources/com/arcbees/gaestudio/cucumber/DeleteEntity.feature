Feature: Delete an Entity
  Scenario: Delete an entity
    Given I create an entity for deletion
    And I delete the entity
    Then the entity should be deleted