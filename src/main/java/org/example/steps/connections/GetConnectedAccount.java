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


public class GetConnectedAccount {

    private static final Logger logger = LoggerFactory.getLogger(GetConnectedAccount.class);

    // Configuration values
    private static final String API_URL = ConfigLoader.getConnectedBaseUrl();
    private static final String API_KEY = ConfigLoader.getApiKey();
    private static final String CONNECTED_ACCOUNT_ID = ConfigLoader.getConnectedAccountId(); // Updated ID
    private static final String ENDPOINT = String.format("%s/%s", API_URL, CONNECTED_ACCOUNT_ID);

    private HttpResponse<String> response;

    @Given("I send a GET request to the connected account API")
    public void iSendAGETRequestToTheConnectedAccountAPI() {
        logger.info("Sending GET request to: {}", ENDPOINT);

        try {
            response = Unirest.get(ENDPOINT)
                    .header("X-API-Key", API_KEY)
                    .asString();

            // Log the response status and body for debugging
            logger.info("Response Status Code: {}", response.getStatus());
            logger.info("Response Body: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Error occurred while sending GET request", e);
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Then("the response should contain the expected account details and status")
    public void theResponseShouldContainExpectedAccountDetailsAndStatus() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Validate JSON content
        Assert.assertEquals("Incorrect integrationId", "f4aa3382-ed0f-4602-92a2-5ba179c6dca4", jsonResponse.getString("integrationId")); // Updated ID
        Assert.assertEquals("Incorrect appUniqueId", "github", jsonResponse.getString("appUniqueId"));
        Assert.assertFalse("Account should not be disabled", jsonResponse.getBoolean("isDisabled"));
        Assert.assertEquals("Incorrect status", "ACTIVE", jsonResponse.getString("status"));
        Assert.assertTrue("Account should be enabled", jsonResponse.getBoolean("enabled"));

        // Validate nested JSON object for connectionParams
        JSONObject connectionParams = jsonResponse.getJSONObject("connectionParams");
        Assert.assertEquals("Incorrect base_url", "https://api.github.com", connectionParams.getString("base_url"));

        // Validate specific parts within nested JSON
        Assert.assertTrue("Authorization header should be present", connectionParams.getJSONObject("headers").has("Authorization"));
    }
}
