package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import java.io.File;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockService {

    private WireMockServer wireMockServer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WireMockService(){
        startWireMockServer();
    }
    // Pornește serverul WireMock
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(options().port(8081)); // Portul pe care rulează WireMock
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081); // Configurează WireMock pentru a asculta pe localhost:8081

        try {
            // Încarcă setările WireMock din fișierul JSON
            setupWireMockFromConfig("src/test/resources/wiremockConfig.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Oprește serverul WireMock
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    // Configurarea WireMock dintr-un fișier JSON
    private void setupWireMockFromConfig(String configFilePath) throws IOException {
        JsonNode configNode = objectMapper.readTree(new File(configFilePath));  // Citește fișierul JSON de configurare

        // Configurarea stubs-urilor pentru GET
        for (JsonNode getConfig : configNode.get("get")) {
            String url = getConfig.get("url").asText();
            String response = getConfig.get("response").asText();
            int status = getConfig.get("status").asInt();
            WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                    .willReturn(aResponse().withBody(response).withStatus(status)));
        }

        // Configurarea stubs-urilor pentru POST
        for (JsonNode postConfig : configNode.get("post")) {
            String url = postConfig.get("url").asText();
            String response = postConfig.get("response").asText();
            int status = postConfig.get("status").asInt();
            WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(url))
                    .willReturn(aResponse().withBody(response).withStatus(status)));
        }
    }
}
