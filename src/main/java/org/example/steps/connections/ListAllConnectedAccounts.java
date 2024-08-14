package org.example.steps.connections;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.utils.ConfigLoader;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ListAllConnectedAccounts {

    private static final Logger logger = LoggerFactory.getLogger(ListAllConnectedAccounts.class);

    // Configuration values
    private static final String API_URL = ConfigLoader.getConnectionListBaseUrl();
    private static final String API_KEY = ConfigLoader.getApiKey();
    private static final String INTEGRATION_ID = ConfigLoader.getConnetionIntegrationId();

    private HttpResponse<String> response;

    @Given("I send a GET request to the connected accounts API")
    public void iSendAGETRequestToTheConnectedAccountsAPI() {
        String url = String.format("%s?pageSize=1&integrationId=%s", API_URL, INTEGRATION_ID);
        logger.info("Sending GET request to: {}", url);

        try {
            response = Unirest.get(url)
                    .header("X-API-Key", API_KEY)
                    .asString();

            // Log the response body for debugging
            logger.info("Response Body: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Error occurred while sending GET request", e);
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Then("the response should be valid and contain expected values")
    public void theResponseShouldBeValidAndContainExpectedValues() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Log the parsed JSON for debugging
        logger.debug("Parsed JSON Response: {}", jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        Assert.assertTrue("Response JSON does not contain 'items' field", jsonResponse.has("items"));

        // Validate pagination details
        int totalPages = jsonResponse.optInt("totalPages", -1);
        logger.info("Total Pages: {}", totalPages);

        // Example validation - Update this based on actual API response
        // If you expect the total pages to be a specific value, replace 18 with that value
        Assert.assertTrue("Total pages should be more than 0", totalPages > 0);

        JSONObject item = jsonResponse.getJSONArray("items").getJSONObject(0);
        Assert.assertEquals("appUniqueId does not match", "github", item.getString("appUniqueId"));
        Assert.assertEquals("appName does not match", "github", item.getString("appName"));
        Assert.assertFalse("Account is expected to be enabled", item.getBoolean("isDisabled"));
    }
}
