package delta.cion.scan;

import delta.cion.util.Colorize;
import delta.cion.util.JsonNodes;
import delta.cion.util.Sender;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScannerMainClass {

    public void Start(String ipRaw, int portStart, int portFinal, int threadsz, int timer) {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads * threadsz);

        for (int i = portStart; i <= portFinal; i++) {

            //CommandChecker commandChecker = new CommandChecker();
            //commandChecker.sendCommand();

            //if (CommandChecker.isStopCommand()) break;
            if (true) {
                final int port = i;
                executor.execute(() -> {
                    String ip = ipRaw + ":" + port;
                    String url = "https://api.mcstatus.io/v2/status/java/" + ip;
                    try {
                        Random random = new Random(timer);
                        double randomz = random.nextDouble(101) * 100;
                        if (randomz >= 50) Thread.sleep(timer * 100);
                        if (port % 100 == 0) Thread.sleep(10000);

                        JsonNodes jsonNodes = new JsonNodes(url);

                        boolean response = Boolean.getBoolean(jsonNodes.get("online"));

                        if (response && jsonNodes.get("online") != null) {
                            String motd = Colorize.colorize(jsonNodes.get("motd.raw")).replaceAll(" {2}", " ");
                            String online = jsonNodes.get("players.online") + "/" + jsonNodes.get("players.max");

                            Sender.send(3, "Server: " + ip + " Found!\n" + motd + "\nOnline: " + online);
                            SaveLogic.SaveFile(ip, true, online, jsonNodes);
                        } else {
                            Sender.send(3, "Server: " + ip + " Not found!");
                            SaveLogic.SaveFile(ip, false, null, jsonNodes);
                        }
                    } catch (Exception e) {
                        Sender.send(3, "Searching " + ip + " error: " + e.getMessage());
                    }
                });
            }
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

    static class CommandChecker extends Thread {
        private static volatile boolean stopCommand = false;
        private final Scanner scanner = new Scanner(System.in);

        public static boolean isStopCommand() {
            return stopCommand;
        }

        public void sendCommand() {
            while (!isStopCommand()) {
                String command = scanner.nextLine();
                switch (command) {
                    case "stop" -> {
                        stopCommand = true;
                        Sender.send(3, "Stopping process..");
                    }
                }
            }
        }
    }
}

