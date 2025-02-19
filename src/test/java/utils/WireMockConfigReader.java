package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class WireMockConfigReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readConfig(String configFilePath) {
        File configFile = new File(configFilePath);

        // 🛠 Verifică dacă fișierul există
        if (!configFile.exists()) {
            System.out.println("❌ ERROR: JSON config file not found at: " + configFilePath);
            return null;
        }

        try {
            // 🛠 Încarcă JSON-ul și verifică conținutul
            JsonNode configNode = objectMapper.readTree(configFile);
            return configNode;
        } catch (IOException e) {
            System.out.println("❌ ERROR: Failed to read JSON file: " + e.getMessage());
            return null;
        }
    }
}
