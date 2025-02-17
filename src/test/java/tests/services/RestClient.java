package tests.services;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class RestClient {

    private String baseUri = "http://localhost:8080";  // URL-ul de bază

    // Constructor
    public RestClient() {
        RestAssured.baseURI = baseUri; // Setează URL-ul de bază pentru toate cererile
    }

    // Trimite cerere GET
    public Response sendGetRequest(String endpoint) {
        return RestAssured.get(endpoint);  // RestAssured.get() face deja GET și returnează direct răspunsul
    }

    // Trimite cerere POST cu set de date extras din fișierul JSON
    public Response sendPostRequest(String endpoint, String dataSetKey) throws IOException {
        // Definește calea fișierului JSON
        String jsonFilePath = "src/test/resources/wiremock/BodyJson.json"; // Calea către fișierul JSON cu toate seturile de date

        // Citește conținutul fișierului JSON
        File jsonFile = new File(jsonFilePath);
        String jsonContent = FileUtils.readFileToString(jsonFile, "UTF-8");

        // Creează un ObjectMapper pentru a deserializa JSON-ul
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonContent);

        // Extrage setul de date dorit din JSON
        JsonNode dataSetNode = rootNode.path(dataSetKey); // Căutăm setul de date după cheia dataSetKey

        // Verifică dacă setul de date există
        if (dataSetNode.isMissingNode()) {
            throw new IllegalArgumentException("Setul de date nu a fost găsit: " + dataSetKey);
        }

        // Convertim setul de date într-un string JSON
        String jsonBody = objectMapper.writeValueAsString(dataSetNode);

        // Trimite cererea POST cu body-ul extraz din fișierul JSON
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .post(endpoint);
    }
}
