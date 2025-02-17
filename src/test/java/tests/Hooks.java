package tests;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import services.WireMockService;
import tests.services.ToxiProxyManager;

import java.io.IOException;

public class Hooks {

    private static services.WireMockService wireMockService;
    private static ToxiProxyManager toxiProxyManager;

    @BeforeAll
    public static void setup() throws IOException {
        if (wireMockService == null) {
            wireMockService = new WireMockService();
        }
        if (toxiProxyManager == null) {
            toxiProxyManager = new ToxiProxyManager();
        }
    }

    @AfterAll
    public static void tearDown() throws IOException {
        wireMockService.stopWireMockServer();
        toxiProxyManager.stopProxy();
    }

    // Adăugăm un getter static pentru acces global
    public static ToxiProxyManager getToxiProxyManager() {
        if (toxiProxyManager == null) {
            throw new IllegalStateException("ToxiProxyManager has not been initialized. Make sure @Before in Hooks is executed.");
        }
        return toxiProxyManager;
    }
}
