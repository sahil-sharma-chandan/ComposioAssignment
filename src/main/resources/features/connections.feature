Feature: Validate Connected Accounts API

  Scenario: Send GET request and validate response
    Given I send a GET request to the connected accounts API
    Then the response should be valid and contain expected values


  Scenario: Send POST request and validate the response
    Given I send a POST request to the connected accounts API
    Then the response should contain the expected redirect URL and status


  Scenario: Send GET request and validate the response
    Given I send a GET request to the connected account API
    Then the response should contain the expected account details and status

  Scenario: Send DELETE request and validate the response
    Given I send a DELETE request to the connected account API
    Then the response should indicate success and the count should be 1