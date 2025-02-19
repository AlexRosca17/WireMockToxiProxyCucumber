package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ToxiProxyConfigReader {
    private static final String CONFIG_PATH = "src/test/resources/configs/toxiproxy-config.json";
    private static JsonNode configData;

    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            configData = objectMapper.readTree(new File(CONFIG_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ToxiProxy configuration file!", e);
        }
    }

    public static int getLatency(String featureName) {
        return configData.has(featureName) ? configData.get(featureName).get("latency").asInt()
                : configData.get("default").get("latency").asInt();
    }

    public static int getBandwidth(String featureName) {
        return configData.has(featureName) ? configData.get(featureName).get("bandwidth").asInt()
                : configData.get("default").get("bandwidth").asInt();
    }
}
