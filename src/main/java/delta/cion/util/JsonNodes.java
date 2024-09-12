package delta.cion.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

public class JsonNodes {

    private JsonNode jsonNode;

    public JsonNodes(String url) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.jsonNode = objectMapper.readTree(new URL(url));
        } catch (Exception ignored) {}
    }

    public String get(String path) {
        String[] pathes = path.split("\\.");

        for (String key : pathes) {
            if (jsonNode.has(key)) jsonNode = jsonNode.get(key);
            else return null;
        }
        return jsonNode.asText();
    }
}
