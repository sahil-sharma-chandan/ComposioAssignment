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




public class DeleteConnectedAccount {

    private static final Logger logger = LoggerFactory.getLogger(DeleteConnectedAccount.class);

    // Configuration values
    private static final String BASE_URL = ConfigLoader.getConnectedBaseUrl();
    private static final String API_KEY = ConfigLoader.getApiKey();
    private static final String CONNECTED_ACCOUNT_ID = ConfigLoader.getConnectedAccountId();
    private static final String DELETE_ENDPOINT = String.format("%s/%s", BASE_URL, CONNECTED_ACCOUNT_ID);

    private HttpResponse<String> response;

    @Given("I send a DELETE request to the connected account API")
    public void iSendADELETERequestToTheConnectedAccountAPI() {
        logger.info("Sending DELETE request to: {}", DELETE_ENDPOINT);

        try {
            response = Unirest.delete(DELETE_ENDPOINT)
                    .header("X-API-Key", API_KEY)
                    .asString();

            // Log the response status and body for debugging
            logger.info("Response Status Code: {}", response.getStatus());
            logger.info("Response Body: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Error occurred while sending DELETE request", e);
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }

    @Then("the response should indicate success and the count should be 1")
    public void theResponseShouldIndicateSuccessAndTheCountShouldBeOne() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Log the parsed JSON for debugging
        logger.info("Parsed JSON Response: {}", jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        String status = jsonResponse.getString("status");
        Assert.assertEquals("success", status);
        logger.info("Status: {}", status);

        int count = jsonResponse.getInt("count");
        Assert.assertEquals(1, count);
        logger.info("Count: {}", count);
    }
}
