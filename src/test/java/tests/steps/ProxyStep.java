package tests.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import tests.Hooks;
import tests.services.ToxiProxyManager;

public class ProxyStep {
    private ToxiProxyManager proxyManager;

    @Given("I setup proxy with latency of {int} ms")
    public void setUpToxiProxy(int latency) {
        proxyManager = Hooks.getToxiProxyManager(); // Luăm instanța de manager
        proxyManager.setLatency(latency); // Suprascriem valoarea din JSON
        System.out.println("Set proxy latency to " + latency + " ms (overriding JSON)");
    }
}
