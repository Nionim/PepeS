package delta.cion.config;

import delta.cion.util.Sender;

import java.io.FileOutputStream;
import java.util.Properties;

public class CreateConf {

    public static void confCreate() {
        Properties conf = new Properties();
        conf.setProperty("DebugMode", "true");
        conf.setProperty("HostMode", "false");
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            conf.store(fos, "Configuration file");
        } catch (Exception e) {
            Sender.send(0, e.getMessage());
        }
    }

}
