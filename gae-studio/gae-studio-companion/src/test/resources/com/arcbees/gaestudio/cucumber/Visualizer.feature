Feature: Using the visualizer
  Scenario: Edit an entity
    Given I create an entity
    And I modify the entity
    Then the entity should be modified

  Scenario: Delete an entity
    Given I create an entity for deletion
    And I delete the entity
    Then the entity should be deleted