package delta.cion.scan;

import delta.cion.util.Colorize;
import delta.cion.util.Sender;
import me.dilley.MineStat;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScannerMainClass {

    public void Start(String ipRaw, int portStart, int portFinal, int threadsz, int timer) {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads * threadsz);


        try (CommandChecker commandChecker = new CommandChecker()) {
            commandChecker.start();
        } catch (Throwable ignore) {}

        for (int i = portStart; i <= portFinal; i++) {
            final int port = i;
            executor.execute(() -> {

                String ip = ipRaw + ":" + port;
                MineStat ms = new MineStat(ipRaw, port);

                try {
                    Random random = new Random(timer);
                    double randomz = random.nextDouble(101) * 100;
                    if (randomz >= 50) Thread.sleep(timer * 100);
                    if (port % 100 == 0) Thread.sleep(10000);

                    if (ms.isServerUp()) {
                        String motd = Colorize.colorize(ms.getStrippedMotd()).replaceAll(" {2}", " ");
                        String online = ms.getCurrentPlayers()+ "/" + ms.getMaximumPlayers();
                        String version = Colorize.colorize(ms.getVersion());

                        String connect = ms.getConnectionStatus().toString();
                        String connectionStat = ms.getConnectionStatusDescription();

                        String status = "\nConnect attempt: "+connect+"\nConnection description: "+connectionStat;

                        Sender.send(3, "Server: " + ip + " Found!\n" + motd + "\nVersion: " + version + "\nOnline: " + online + status);
                        SaveLogic.SaveFile(ip, true, online, motd, version, status);
                    } else {
                        Sender.send(3, "Server: " + ip + " Not found!");
                        SaveLogic.SaveFile(ip, false, null, null, null, null);
                    }
                } catch (Exception e) {
                    Sender.send(3, "Searching " + ip + " error: " + e.getMessage());
                    Sender.send(3, e.toString());
                }
            });
        }

        executor.shutdown();
        try {
            while (!executor.isTerminated()) {
                executor.awaitTermination(30, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        SaveLogic.Finaly(ipRaw);
    }

    static class CommandChecker extends Thread implements AutoCloseable {
        private static volatile boolean stopCommand = false;
        private final Scanner scanner = new Scanner(System.in);

        public static boolean isStopCommand() {
            return stopCommand;
        }



        @Override
        public void run() {
            while (!isStopCommand()) {
                String command = scanner.nextLine();
                switch (command) {
                    case "stop" -> {
                        stopCommand = true;
                        Sender.send(3, "Stopping process..");
                        Runtime.getRuntime().exit(0);
                    }
                }
            }
        }

        @Override
        public void close() throws Exception {
            scanner.close();
            stopCommand = true;
        }
    }
}

