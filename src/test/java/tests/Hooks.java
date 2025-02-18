package tests;

import io.cucumber.java.*;
import services.WireMockService;
import tests.services.ToxiProxyManager;
import java.io.IOException;

public class Hooks {
    private static WireMockService wireMockService;
    private static ToxiProxyManager toxiProxyManager;
    private static String currentFeature;


    @Before
    public void setup(Scenario scenario) throws IOException {
        // Extragem numele feature-ului curent
        String detectedFeature = scenario.getUri().toString().replaceAll(".*\\/", "").replace(".feature", "");

        // Dacă e un feature nou, repornim WireMock și ToxiProxy
        if (!detectedFeature.equals(currentFeature)) {
            currentFeature = detectedFeature;
            System.out.println("🔄 New Feature Detected: " + currentFeature);

            // Dacă serverele sunt deja pornite, le oprim înainte să le restartăm
            if (wireMockService != null) {
                wireMockService.stopWireMockServer();
            }
            if (toxiProxyManager != null) {
                toxiProxyManager.stopProxy();
            }

            // Restart WireMock și ToxiProxy pentru noul feature
            wireMockService = new WireMockService();
            toxiProxyManager = new ToxiProxyManager(currentFeature);
            System.out.println("✅ WireMock și ToxiProxy reinițializate pentru feature-ul: " + currentFeature);
        }
    }

    @After
    public void tearDown() throws IOException {
        wireMockService.stopWireMockServer();
        toxiProxyManager.stopProxy();

        System.out.println("✅ Finalizing scenario in feature: " + currentFeature);
    }

    public static ToxiProxyManager getToxiProxyManager() {
        if (toxiProxyManager == null) {
            throw new IllegalStateException("❌ ToxiProxyManager has not been initialized!");
        }
        return toxiProxyManager;
    }
}
