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


public class ConnectAnAccount {

    private static final Logger logger = LoggerFactory.getLogger(ConnectAnAccount.class);

    // Configuration values
    private static final String API_BASE_URL = ConfigLoader.getConnetionBaseUrl();
    private static final String API_KEY = ConfigLoader.getApiKey();
    private static final String INTEGRATION_ID = ConfigLoader.getConnetionIntegrationId();
    private static final String REDIRECT_URI = ConfigLoader.getConnetionRedirectUrl();

    private HttpResponse<String> response;

    @Given("I send a POST request to the connected accounts API")
    public void iSendAPOSTRequestToTheConnectedAccountsAPI() {
        logger.info("Sending POST request to: {}", API_BASE_URL);

        try {
            response = Unirest.post(API_BASE_URL)
                    .header("X-API-Key", API_KEY)
                    .header("Content-Type", "application/json")
                    .body(createRequestBody())
                    .asString();

            // Log the response body for debugging
            logger.info("Response Body: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Error occurred while sending POST request", e);
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Then("the response should contain the expected redirect URL and status")
    public void theResponseShouldContainExpectedRedirectURLAndStatus() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Log the parsed JSON for debugging
        logger.debug("Parsed JSON Response: {}", jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        Assert.assertEquals("INITIATED", jsonResponse.getString("connectionStatus"));

        // Get actual redirect URL
        String actualRedirectUrl = jsonResponse.getString("redirectUrl");
    }

    private String createRequestBody() {
        return new JSONObject()
                .put("integrationId", INTEGRATION_ID)
                .put("redirectUri", REDIRECT_URI)
                .toString();
    }
}
