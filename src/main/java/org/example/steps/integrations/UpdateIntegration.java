package org.example.steps.integrations;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Assert;
import org.json.JSONObject;

public class UpdateIntegration {

    private HttpResponse<String> response;

    @Given("I send a PATCH request to update the integration status")
    public void iSendAPATCHRequestToUpdateTheIntegrationStatus() {
        response = Unirest.patch("https://backend.composio.dev/api/v1/integrations/ff6d73b1-ad6e-4313-9513-4e30fb429e91")
                .header("X-API-Key", "fl1fpxfwnggtksb2s13kf")
                .header("Content-Type", "application/json")
                .body("{ \"enabled\": true }")
                .asString();

        // Log the response status and body for debugging
        System.out.println("Response Status Code: " + response.getStatus());
        System.out.println("Response Body: " + response.getBody());
    }

    @Then("the response should indicate success")
    public void theResponseShouldIndicateSuccess() {
        // Check for HTTP status
        int statusCode = response.getStatus();
        Assert.assertEquals(200, statusCode); // Ensure status code is 200 OK

        // Parse response body as JSON
        JSONObject jsonResponse = new JSONObject(response.getBody());

        // Log the parsed JSON for debugging
        System.out.println("Parsed JSON Response: " + jsonResponse.toString(2)); // Pretty-print with an indentation of 2 spaces

        // Validate JSON content
        String status = jsonResponse.getString("status");
        Assert.assertEquals("success", status);
        System.out.println("Status: " + status);
    }
}

