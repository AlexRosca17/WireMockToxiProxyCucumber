package tests.services;

import tests.services.ToxiProxyService;

import java.io.IOException;

public class ToxiProxyManager {
    private final ToxiProxyService toxiProxyService;

    public ToxiProxyManager() throws IOException {
        this.toxiProxyService = new ToxiProxyService();
        this.toxiProxyService.initializeProxy("toxiProxy", "localhost:8080", "localhost:8081");
    }

    public void setLatency(int latency) {
        try {
            toxiProxyService.addLatency(latency);
            System.out.println("Applied latency: " + latency + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Failed to apply latency", e);
        }
    }

    public void setBandwidth(float bandwidth) {
        try {
            toxiProxyService.addBandwidthLimit(bandwidth);
            System.out.println("Applied bandwidth limit: " + bandwidth + " KB/s");
        } catch (IOException e) {
            throw new RuntimeException("Failed to apply bandwidth limit", e);
        }
    }

    public void stopProxy() throws IOException {
        toxiProxyService.stopProxy();
    }
}
