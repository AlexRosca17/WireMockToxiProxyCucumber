package tests;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import services.WireMockService;
import tests.services.ToxiProxyManager;

import java.io.IOException;

public class Hooks {

    private static WireMockService wireMockService;
    private static ToxiProxyManager toxiProxyManager;

    @Before (order = 0)
    public static void setup() throws IOException {
        System.out.println("Hooks setup executed"); // 🔹 Debugging

        if (wireMockService == null) {
            wireMockService = new WireMockService();
        }
        if (toxiProxyManager == null) {
            toxiProxyManager = new ToxiProxyManager();
        }

        System.out.println("Waiting for WireMock & ToxiProxy to fully initialize...");
    }

    @AfterAll
    public static void tearDown() throws IOException {
        System.out.println("Hooks teardown executed"); // 🔹 Log pentru debugging

        if (wireMockService != null) {
            wireMockService.stopWireMockServer();
        }
        if (toxiProxyManager != null) {
            toxiProxyManager.stopProxy();
        }
    }

    public static ToxiProxyManager getToxiProxyManager() {
        if (toxiProxyManager == null) {
            try {
                System.out.println("ToxiProxyManager was null, reinitializing..."); // 🔹 Debugging
                toxiProxyManager = new ToxiProxyManager();
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize ToxiProxyManager", e);
            }
        }
        return toxiProxyManager;
    }
}
