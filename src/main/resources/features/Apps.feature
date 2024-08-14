Feature: API Response Validation

  Scenario: Verify the application details from API response
    Given I have retrieved the API response
    Then the response should contain "Asana" with description "HubSpot is a developer and marketer of software products for inbound marketing, sales, and customer service."
    And the response should contain "Asana" with description "HubSpot is a developer and marketer of software products for inbound marketing, sales, and customer service."