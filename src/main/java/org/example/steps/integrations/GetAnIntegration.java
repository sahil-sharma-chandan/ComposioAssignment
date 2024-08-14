package org.example.steps.integrations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.utils.ConfigLoader;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetAnIntegration {

    private static final Logger logger = LoggerFactory.getLogger(GetAnIntegration.class);

    // Configuration values
    private static final String BASE_URL = ConfigLoader.getIntegrationBaseUrl();
    private static final String API_KEY = ConfigLoader.getApiKey();
    private static final String INTEGRATION_ID = ConfigLoader.getIntegrationId();
    private static final String GET_INTEGRATION_ENDPOINT = String.format("%s/%s", BASE_URL, INTEGRATION_ID);

    private HttpResponse<String> response;

    @Given("I send a GET request to retrieve integration details")
    public void iSendAGETRequestToRetrieveIntegrationDetails() {
        logger.info("Sending GET request to: {}", GET_INTEGRATION_ENDPOINT);

        try {
            response = Unirest.get(GET_INTEGRATION_ENDPOINT)
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

    @Then("the response should contain the expected integration details")
    public void theResponseShouldContainTheExpectedIntegrationDetails() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Log the parsed JSON for debugging
        logger.info("Parsed JSON Response: {}", jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        Assert.assertEquals("f4aa3382-ed0f-4602-92a2-5ba179c6dca4", jsonResponse.getString("id"));
        Assert.assertEquals("github_horrible_amethyst", jsonResponse.getString("name"));
        Assert.assertTrue(jsonResponse.getBoolean("enabled"));
        Assert.assertFalse(jsonResponse.getBoolean("deleted"));
        Assert.assertEquals("01e22f33-dc3f-46ae-b58d-050e4d2d1909", jsonResponse.getString("appId"));
        Assert.assertEquals("test-github-connector", jsonResponse.getString("defaultConnectorId"));
        Assert.assertEquals("OAUTH2", jsonResponse.getString("authScheme"));

        // Validate nested JSON object for authConfig
        JSONObject authConfig = jsonResponse.getJSONObject("authConfig");
        Assert.assertEquals("https://api.github.com", authConfig.getString("base_url"));
        Assert.assertEquals("********", authConfig.getString("client_id")); // Use actual expected client_id or mask for sensitive information

        Assert.assertEquals("https://github.githubassets.com/assets/GitHub-Mark-ea2971cee799.png", jsonResponse.getString("logo"));
        Assert.assertEquals("github", jsonResponse.getString("appName"));
        Assert.assertTrue(jsonResponse.getBoolean("useComposioAuth"));
    }
}
