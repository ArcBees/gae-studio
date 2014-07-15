@Ignore
Feature: Profiler
  Scenario: Interact with the datastore
    Given I start recording
    And I interact with the datastore
    Then I should see requests in the Profiler
