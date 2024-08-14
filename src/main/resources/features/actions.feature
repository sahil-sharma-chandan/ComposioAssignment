Feature: Retrieve GitHub API Root Action

  Scenario: Send GET request and validate the GitHub API root action details
    Given I send a GET request to retrieve the GitHub API root action
    Then the response should contain the expected action details


  Scenario: Execute GitHub API root action
    Given I send a POST request to execute the action
    Then the response should contain the expected GitHub API root details

