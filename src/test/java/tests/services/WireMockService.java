package tests.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import utils.WireMockConfigReader;

import java.io.File;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockService {

    private WireMockServer wireMockServer;
    private static final String CONFIG_PATH = "src/test/resources/configs/wiremockConfig.json"; // Calea către fișierul JSON


    public WireMockService() throws IOException {
        startWireMockServer();
    }

    // Start WireMock
    public void startWireMockServer() throws IOException {
        wireMockServer = new WireMockServer(options().port(8081));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081);

        JsonNode configNode = WireMockConfigReader.readConfig(CONFIG_PATH);
        if (configNode != null) {
            setupMappings(configNode);
        }
    }

    // Stop WireMock server
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    // Map configs for wiremock
    private void setupMappings(JsonNode configNode) {
        if (configNode.has("get")) {
            for (JsonNode getConfig : configNode.get("get")) {
                String url = getConfig.get("url").asText();
                String response = getConfig.get("response").asText();
                int status = getConfig.get("status").asInt();

                WireMock.stubFor(get(urlEqualTo(url))
                        .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(response).withStatus(status)));
            }
        }

        if (configNode.has("post")) {
            for (JsonNode postConfig : configNode.get("post")) {
                String url = postConfig.get("url").asText();
                String response = postConfig.get("response").asText();
                int status = postConfig.get("status").asInt();

                WireMock.stubFor(post(urlEqualTo(url))
                        .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(response).withStatus(status)));
            }
        }

        // FallBack rule for request that don't match
        if (configNode.has("default")) {
            int defaultStatus = configNode.get("default").get("status").asInt();
            String defaultResponse = configNode.get("default").get("response").asText();

            System.out.println("Fallback rule set for unknown requests: " + defaultStatus);
            WireMock.stubFor(any(anyUrl())
                    .atPriority(1000)
                    .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(defaultStatus).withBody(defaultResponse)));
        }
    }
}
