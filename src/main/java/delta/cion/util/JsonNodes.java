package delta.cion.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

public class JsonNodes  implements AutoCloseable {

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
            return jsonNode.asText();
        }


        if (path.contains(".")) {

            jsonNode = jsonNode.get(path.substring(0, path.indexOf(".")));
            path = path.substring(path.indexOf(".")+1);

            if (path.contains(".")) return recursiveGet(jsonNode, path);
            else return jsonNode.get(path).asText();
        } else return jsonNode.get(path).asText();
    }

    private String recursiveGet(JsonNode jsonNode, String path) {
        if (path.contains(".")) {

            jsonNode = jsonNode.get(path.substring(0, path.indexOf(".")));
            path = path.substring(path.indexOf(".")+1);

            return recursiveGet(jsonNode, path);
        } else return jsonNode.get(path).asText();
    }

    @Override
    public void close() throws Exception {

    }
}
