package delta.cion.config;

import delta.cion.messages.Sender;

import java.io.FileOutputStream;
import java.util.Properties;

public class Configuration {

    private Properties conf;

    public Configuration() {
        conf = new Properties();
    }

    public void confCreate() {
        conf.setProperty("DebugMode", "true");
        conf.setProperty("HostMode", "false");
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            conf.store(fos, "Configuration file");
        } catch (Exception e) {
            Sender.send(0, e.getMessage());
        }
    }

}
