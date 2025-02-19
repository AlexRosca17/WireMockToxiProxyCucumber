package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;
import tests.response.ResponseGETSuccess;
import tests.services.RestClient;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class RestStep {

    private final RestClient restClient = new RestClient();
    private Response response;
    ResponseGETSuccess responseGETSuccess;


    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint){
        response = restClient.sendGetRequest(endpoint);
        responseGETSuccess = response.body().as(ResponseGETSuccess.class);

    }

    @Then("The response should be {int} with message {string}")
    public void verifyResponse(int status, String expectedMessage){
        assertEquals(status, response.getStatusCode());
        assertTrue(responseGETSuccess.getMessage().contains(expectedMessage));
    }
}
