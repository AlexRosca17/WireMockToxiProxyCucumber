package tests.services;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.Toxic;
import eu.rekawek.toxiproxy.model.ToxicDirection;

import java.io.IOException;

public class ToxiProxyService {
    private  ToxiproxyClient client;
    private Proxy proxy;


    public ToxiProxyService() throws IOException {
        if(client == null){
            this.client = new ToxiproxyClient("localhost", 8474);
        }
    }

    public void initializeProxy(String name, String upStreamListen, String upStream) throws IOException {
        if (proxy == null) {
            proxy = client.createProxy(name, upStreamListen, upStream);
            System.out.println("Proxy started: " + name);
        }
    }

    public void addLatency(int latencyInMillis) throws IOException {
        if (proxy == null) throw new IllegalStateException("Proxy not initialized!");
        proxy.toxics().latency("latency", ToxicDirection.DOWNSTREAM, latencyInMillis);
        System.out.println("Latency injected: " + latencyInMillis + " ms");
    }

    public void addBandwidthLimit(float rate) throws IOException {
        if (proxy == null) throw new IllegalStateException("Proxy not initialized!");
        proxy.toxics().bandwidth("bandwidth", ToxicDirection.DOWNSTREAM, (long) rate);
        System.out.println("Bandwidth limit injected: " + rate + " KB/s");
    }

    public void removeAllToxics() throws IOException {
        if (proxy != null) {
            for (Toxic toxic : proxy.toxics().getAll()) {
                toxic.remove();
            }
        }
    }

    public void stopProxy() throws IOException {
        if (proxy != null) {
            proxy.delete();
            proxy = null;
            System.out.println("Proxy stopped.");
        }
    }
}
