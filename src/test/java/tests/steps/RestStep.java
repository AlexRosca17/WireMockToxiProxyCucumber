package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;
import tests.services.RestClient;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class RestStep {

    private final RestClient restClient = new RestClient();
    private Response response;


    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint){
        response = restClient.sendGetRequest(endpoint);

    }

    @Then("the response should be {string}")
    public void verifyResponse(String expectedMessage){
        assertEquals(200, response.getStatusCode());
        assertTrue(response.body().asString().contains(expectedMessage));
    }




}
