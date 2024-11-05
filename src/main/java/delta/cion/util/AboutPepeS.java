package delta.cion.util;

import java.io.*;
import java.util.Properties;

public class AboutPepeS {

    private static final File confDir = new File("PepeServerScanner");
    private static final Properties c = new Properties();

    private static Properties Config() {
        return c;
    }

    public static void saveDefaultConfig() {
        if (!confDir.exists() || !confDir.isDirectory()) confDir.mkdir();

        Config().setProperty("DebugMode", "true");
        Config().setProperty("PortForProxy", "25565");
        try (FileOutputStream fileLocName = new FileOutputStream(confDir + "/config.properties")) {
            Config().store(fileLocName, "Configuration file");
        } catch (IOException ignored) {}
    }

    public static boolean reloadConfig() {
        File confFile = new File(confDir + "/config.properties");
        if (!confFile.exists()) {
            Sender.send(2, "&4[CODE_ERROR]: AboutPepeS.reloadConfig(). File config.properties don`t exists!");
            return false;
        } else {
            Config().clear();
            try (FileInputStream fileLocName = new FileInputStream(confDir + "/config.properties")) {
                Config().load(fileLocName);
                return true;
            } catch (IOException ignored) {
                Sender.send(2, "&4[CODE_ERROR]: AboutPepeS.reloadConfig(). WHAT?!");
                return false;
            }
        }
    }
}
