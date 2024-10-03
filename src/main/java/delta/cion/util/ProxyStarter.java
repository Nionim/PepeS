package delta.cion.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProxyStarter {

    public static void run() {

        String port = getPort();

        String jarPath = "modules/SevenKataNext.jar";
        String[] command = {"java", "-jar", jarPath, port};

        Runnable jarExecution = () -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                Process process = processBuilder.start();
                process.waitFor();

                int exitCode = process.exitValue();
                System.out.println("Exit code for proxy: " + exitCode);
            } catch (Exception ignored) {}
        };

        Thread jarThread = new Thread(jarExecution);
        jarThread.start();
    }

    private static String getPort() {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("server.properties")) {
            properties.load(input);
            String serverPort = properties.getProperty("server-port");

            if (serverPort != null) {
                Sender.send(3, "(ProxyStarter) Получен порт: "+ serverPort);
                return serverPort;
            } else {
                return null;
            }
        } catch (Exception ignored) {}
        return null;
    }
}
