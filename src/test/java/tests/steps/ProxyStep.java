package tests.steps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import tests.Hooks;
import tests.services.ToxiProxyManager;

import java.io.IOException;

public class ProxyStep {

    private ToxiProxyManager proxyManager;

    @Given("I setup proxy with latency of {int} ms")
    public void setUpToxiProxy(int latency) {
        proxyManager = Hooks.getToxiProxyManager(); // Luăm instanța la runtime
        proxyManager.setLatency(latency);
        System.out.println("Set proxy latency to " + latency + " ms");
    }
}
