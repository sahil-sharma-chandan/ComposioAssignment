package org.example.steps.lists;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.utils.ConfigLoader;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListAllApps {
    private static final Logger logger = LoggerFactory.getLogger(ListAllApps.class);
    private HttpResponse<String> response;
    private static final String apiUrl = ConfigLoader.getApiUrl();
    private static final String apiKey = ConfigLoader.getApiKey();

    @Given("I have retrieved the API response")
    public void i_have_retrieved_the_api_response() {
        logger.info("Sending request to API: {}", apiUrl);
        response = Unirest.get(apiUrl)
                .header("X-API-Key", apiKey)
                .asString();
        logger.info("Received API response with status: {}", response.getStatus());
    }

    @Then("the response should contain {string} with description {string}")
    public void the_response_should_contain_with_description(String appName, String description) {
        String responseBody = response.getBody();

        // Log the response body for debugging
        logger.debug("API Response Body: {}", responseBody);

        // Check if the response contains the expected values
        Assert.assertTrue("Expected app name not found in response", responseBody.contains(appName));
        Assert.assertTrue("Expected description not found in response", responseBody.contains(description));
    }
}
