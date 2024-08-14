Feature: Validate GET request to retrieve integration details

  Scenario: Send GET request and validate the response for a specific integration
    Given I send a GET request to retrieve integration details
    Then the response should contain the expected integration details


  Scenario: Send PATCH request and validate the response
    Given I send a PATCH request to update the integration status
    Then the response should indicate success

  Scenario: Send GET request and validate the integrations list
    Given I send a GET request to retrieve the integrations
    Then the response should contain the expected integrations and details