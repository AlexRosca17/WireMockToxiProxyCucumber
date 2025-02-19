package tests.services;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class RestClient {

    private String baseUri = "http://localhost:8080";

    public RestClient() {
        RestAssured.baseURI = baseUri;
    }

    public Response sendGetRequest(String endpoint) {
        return RestAssured.given()
                .header("Accept", "application/json")
                .contentType("application/json")
                .get(endpoint);
    }
}
