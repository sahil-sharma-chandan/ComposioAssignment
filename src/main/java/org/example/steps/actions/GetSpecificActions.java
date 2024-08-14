package org.example.steps.actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.utils.ConfigLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetSpecificActions {

    private static final Logger logger = LoggerFactory.getLogger(GetSpecificActions.class);

    // Configuration values
    private static final String API_BASE_URL = ConfigLoader.getActionApiUrl();
    private static final String API_KEY = ConfigLoader.getApiKey();
    private static final String ACTION_NAME = ConfigLoader.getActionName();
    private static final String FULL_API_URL = API_BASE_URL + ACTION_NAME;

    private HttpResponse<String> response;

    @Given("I send a GET request to retrieve the GitHub API root action")
    public void iSendAGETRequestToRetrieveTheGitHubApiRootAction() {
        logger.info("Sending GET request to: {}", FULL_API_URL);

        try {
            response = Unirest.get(FULL_API_URL)
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

    @Then("the response should contain the expected action details")
    public void theResponseShouldContainTheExpectedActionDetails() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONArray jsonResponse = new JSONArray(response.getBody());

        // Log the parsed JSON for debugging
        logger.debug("Parsed JSON Response: {}", jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        Assert.assertEquals("Expected one item in the response", 1, jsonResponse.length());

        // Validate the content of the first item
        JSONObject firstItem = jsonResponse.getJSONObject(0);
        validateActionDetails(firstItem);
    }

    private void validateActionDetails(JSONObject action) {
        // Validate specific fields (replace with actual expected values)
        Assert.assertEquals(ACTION_NAME, action.getString("name"));
        Assert.assertEquals("github_api_root", action.getString("display_name"));
        Assert.assertEquals("Get Hypermedia links to resources accessible in GitHub's REST API", action.getString("description"));
        Assert.assertEquals("01e22f33-dc3f-46ae-b58d-050e4d2d1909", action.getString("appId"));
        Assert.assertEquals("github", action.getString("appKey"));
        Assert.assertEquals("https://github.githubassets.com/assets/GitHub-Mark-ea2971cee799.png", action.getString("logo"));
        Assert.assertTrue(action.getBoolean("enabled"));

        // Log details of the action for debugging
        logger.info("Action Name: {}", action.getString("name"));
        logger.info("Display Name: {}", action.getString("display_name"));
        logger.info("Description: {}", action.getString("description"));
        logger.info("App ID: {}", action.getString("appId"));
        logger.info("App Key: {}", action.getString("appKey"));
        logger.info("Logo: {}", action.getString("logo"));
        logger.info("Enabled: {}", action.getBoolean("enabled"));
    }
}
