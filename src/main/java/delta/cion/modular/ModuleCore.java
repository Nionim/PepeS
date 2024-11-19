package delta.cion.modular;

import delta.cion.messages.Sender;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ModuleCore {

    private static String startCommand;

    public static void ModuleInitialize() {
        try {
            File mainDir = new File("modules");
            if (!mainDir.exists()) {
                if (mainDir.mkdir()) Sender.send(3, "\"modules\" folder has been created!");
            }
            else {
                File[] files = mainDir.listFiles();
                if (files != null) {
                    Sender.send(3, "Modules found! Starting...");
                    StartModule(files);
                }
                else Sender.send(3, "No modules detected!");
            }
        } catch (Exception ignored) {}
    }

    private static void StartModule(File[] modules) {

        String port = ModuleData.getPort();
        for (File module : modules) {
            if (!module.isDirectory() && module.getName().endsWith(".jar")) {
                String commandProxy = String.format("java -jar modules/%s %s", module.getName(), port);
                String command = String.format("java -jar modules/%s", module.getName());
                Sender.send(3, "Starting module -> " + module.getName());
                try {

                    if (module.getName().contains("SevenKataNext")) startCommand = commandProxy;
                    else startCommand = command;

                    Thread jarThread = getThread(module);
                    jarThread.start();
                } catch (Exception e) {
                    Sender.send(3, "&4Failed to start " + module.getName() + " is a not .jar or this file is broken!");
                    Sender.send(3, e.toString());
                }
            }
        }
    }

    private static @NotNull Thread getThread(File module) {
        Runnable jarExecution = () -> {
            try {
                Sender.send(3, "Trying to start -> "+ module.getName());
                Sender.send(3, "Start with flags: "+startCommand);
                ProcessBuilder processBuilder = new ProcessBuilder(startCommand.split(" "));
                Process process = processBuilder.start();

                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = errorReader.readLine()) != null) {
                    Sender.send(3, line);
                }

                process.waitFor();
                Sender.send(3, "Started "+module.getName());
                int exitCode = process.exitValue();
                Sender.send(3, String.format("Exit code for %s: %s", module.getName(), exitCode));
            } catch (Exception ignored) {}
        };

        return new Thread(jarExecution);
    }
}
