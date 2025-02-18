package tests.services;

import java.io.IOException;
import utils.ToxiProxyConfigReader;

public class ToxiProxyManager {
    private final ToxiProxyService toxiProxyService;
    private final String featureName;
    private int currentLatency;
    private int currentBandwidth;

    public ToxiProxyManager(String featureName) throws IOException {
        this.toxiProxyService = new ToxiProxyService();
        this.toxiProxyService.initializeProxy("toxiProxy", "localhost:8080", "localhost:8081");
        this.featureName = featureName;

        // Setează valorile inițiale din JSON
        this.currentLatency = ToxiProxyConfigReader.getLatency(featureName);
        this.currentBandwidth = ToxiProxyConfigReader.getBandwidth(featureName);

        applyToxics(); // Aplică toxicitățile inițiale
    }

    private void applyToxics() {
        try {
            // Eliminăm toxicitatea existentă, dacă există deja
            removeExistingToxics();

            // Aplicăm noua latență și noul bandwidth
            toxiProxyService.addLatency(currentLatency);
            toxiProxyService.addBandwidthLimit(currentBandwidth);

            System.out.println("Applied latency: " + currentLatency + " ms | Bandwidth: " + currentBandwidth + " KB/s");
        } catch (IOException e) {
            throw new RuntimeException("Failed to apply toxics", e);
        }
    }

    public void setLatency(int latency) {
        this.currentLatency = latency;
        applyToxics();
    }

    public void setBandwidth(int bandwidth) {
        this.currentBandwidth = bandwidth;
        applyToxics();
    }

    private void removeExistingToxics() {
        try {
            toxiProxyService.removeAllToxics();
            System.out.println("Removed existing toxics before applying new ones.");
        } catch (IOException e) {
            System.err.println("Failed to remove existing toxics: " + e.getMessage());
        }
    }

    public void stopProxy() throws IOException {
        toxiProxyService.stopProxy();
    }
}
