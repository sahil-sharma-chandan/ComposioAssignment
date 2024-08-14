package org.example.steps.integrations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.utils.ConfigLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

public class ListAllIntegrations {

    private HttpResponse<String> response;

    // Endpoints used
    private static final String INTEGRATIONS_ENDPOINT = ConfigLoader.getIntegrationBaseUrl();
    private static final String X_API_KEY = ConfigLoader.getApiKey();

    @Given("I send a GET request to retrieve the integrations")
    public void iSendAGETRequestToRetrieveTheIntegrations() {
        response = Unirest.get(INTEGRATIONS_ENDPOINT)
                .header("X-API-Key", X_API_KEY)
                .asString();

        // Log the response status and body for debugging
        System.out.println("Response Status Code: " + response.getStatus());
        System.out.println("Response Body: " + response.getBody());
    }

    @Then("the response should contain the expected integrations and details")
    public void theResponseShouldContainTheExpectedIntegrationsAndDetails() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals(200, statusCode); // Ensure status code is 200 OK

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Log the parsed JSON for debugging
        System.out.println("Parsed JSON Response: " + jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        JSONArray items = jsonResponse.getJSONArray("items");
        Assert.assertTrue(items.length() > 0); // Ensure that there are items in the response

    }
}