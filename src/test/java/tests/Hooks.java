package tests;

import io.cucumber.java.*;
import tests.services.ToxiProxyManager;
import tests.services.WireMockService;

import java.io.IOException;

public class Hooks {
    private static tests.services.WireMockService wireMockService;
    private static ToxiProxyManager toxiProxyManager;
    private static String currentFeature;


    @Before
    public void setup(Scenario scenario) throws IOException {
        // Feature that runs
        String detectedFeature = scenario.getUri().toString().replaceAll(".*\\/", "").replace(".feature", "");

        // new feature restart wiremock and toxi proxy
        if (!detectedFeature.equals(currentFeature)) {
            currentFeature = detectedFeature;
            System.out.println("New Feature Detected: " + currentFeature);

            // Dacă serverele sunt deja pornite, le oprim înainte să le restartăm
            if (wireMockService != null) {
                wireMockService.stopWireMockServer();
            }
            if (toxiProxyManager != null) {
                toxiProxyManager.stopProxy();
            }

            // Restart WireMock și ToxiProxy pentru noul feature
            wireMockService = new tests.services.WireMockService();
            toxiProxyManager = new ToxiProxyManager(currentFeature);
            System.out.println(" WireMock and ToxiProxy for " + currentFeature);
        }
    }

    @After
    public void tearDown() throws IOException {
        wireMockService.stopWireMockServer();
        toxiProxyManager.stopProxy();

        System.out.println("Finalizing scenario in feature: " + currentFeature);
    }

    public static ToxiProxyManager getToxiProxyManager() {
        if (toxiProxyManager == null) {
            throw new IllegalStateException("ToxiProxyManager has not been initialized!");
        }
        return toxiProxyManager;
    }
}
