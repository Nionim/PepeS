package delta.cion.util;

import delta.cion.messages.Sender;
import delta.cion.scan.SaveLogic;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandChecker extends Thread implements AutoCloseable {

    private static volatile boolean stopCommand = false;
    private final Scanner scanner = new Scanner(System.in);

    public static boolean isStopCommand() {
        return stopCommand;
    }

    public CommandChecker() {

    }

    @Override
    public void run() {
        try (ExecutorService executor = Executors.newFixedThreadPool(1)) {
            executor.execute(() -> {
                while (!isStopCommand()) {
                    String command = scanner.nextLine();
                    switch (command) {
                        case "stop" -> {
                            stopCommand = true;
                            Sender.send(3, "Stopping process..");
                            // TODO
                            // saver(ScannerMainClass.scannedIP);
                            Runtime.getRuntime().exit(0);
                        }
                    }
                }
            });
        }
    }

    public void saver(String ipRaw) {
        if (stopCommand && ipRaw != null && !ipRaw.isBlank()) SaveLogic.Finaly(ipRaw);
        else Sender.send(3, "No content for send to Discord");
    }

    @Override
    public void close() throws Exception {
        scanner.close();
        stopCommand = true;
    }

}
