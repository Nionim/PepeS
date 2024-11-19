package delta.cion.modular;

import delta.cion.messages.Sender;

import java.io.FileInputStream;
import java.util.Properties;

public class ModuleData {

    public static String getPort() {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("server.properties")) {
            properties.load(input);
            String serverPort = properties.getProperty("server-port");

            if (serverPort != null) {
                Sender.send(3, "(ProxyStarter) Получен порт: " + serverPort);
                return serverPort;
            } else {
                return null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
