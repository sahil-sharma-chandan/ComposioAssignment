package org.example.steps.actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.utils.ConfigLoader;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteAction.class);

    private HttpResponse<String> response;

    // Endpoints used
    private static final String ACTION_NAME = ConfigLoader.getActionName();
    private static final String GITHUB_API_ROOT_ENDPOINT = ConfigLoader.getActionGitHubApiRootPoint();
    private static final String X_API_KEY = ConfigLoader.getApiKey();
    private static final String CONNECTED_ACCOUNT_ID = ConfigLoader.getActionConnectedAccountId();
    private static final String APP_NAME = ConfigLoader.getAppName();

    @Given("I send a POST request to execute the action")
    public void iSendAPOSTRequestToExecuteTheAction() {
        LOGGER.info("Sending POST request to execute the {} action", ACTION_NAME);
        response = Unirest.post(GITHUB_API_ROOT_ENDPOINT.replace("{actionName}", ACTION_NAME))
                .header("X-API-Key", X_API_KEY)
                .header("Content-Type", "application/json")
                .body("{\n  \"connectedAccountId\": \"" + CONNECTED_ACCOUNT_ID + "\",\n  \"appName\": \"" + APP_NAME + "\"\n}")
                .asString();
        LOGGER.info("Response received: {}", response.getBody());
    }

    @Then("the response should contain the expected GitHub API root details")
    public void theResponseShouldContainTheExpectedGitHubAPIRootDetails() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        LOGGER.info("Status code: {}", statusCode);
        Assert.assertEquals("Expected status code 200 OK", 200, statusCode);

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());
        LOGGER.info("Response JSON: {}", jsonResponse);

        // Validate JSON content
        validateGitHubAPIRootDetails(jsonResponse);
    }

    private void validateGitHubAPIRootDetails(JSONObject response) {
        // Validate specific fields
        JSONObject executionDetails = response.getJSONObject("execution_details");
        LOGGER.info("Execution details: {}", executionDetails);
        Assert.assertTrue("Expected executed to be true", executionDetails.getBoolean("executed"));

        JSONObject responseData = response.getJSONObject("response_data");
        LOGGER.info("Response data: {}", responseData);
        Assert.assertNotNull("Expected response data to be not null", responseData);
    }
}