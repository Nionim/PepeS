package delta.cion.scan;

import delta.cion.util.Colorize;
import delta.cion.util.CommandChecker;
import delta.cion.messages.Sender;
import me.dilley.MineStat;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScannerMainClass {

    public static String scannedIP;

    public void Start(String ipRaw, int portStart, int portFinal, int threadsz, int timer) {
        scannedIP = ipRaw;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads * threadsz);
        try (CommandChecker commandChecker = new CommandChecker()) {
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
                            String online = ms.getCurrentPlayers() + "/" + ms.getMaximumPlayers();
                            String version = Colorize.colorize(ms.getVersion());

                            String connect = ms.getConnectionStatus().toString();
                            String connectionStat = ms.getConnectionStatusDescription();

                            String status = "\nConnect attempt: " + connect + "\nConnection description: " + connectionStat;

                            Sender.send(3, "Server: " + ip + " Found!\n" + motd + "\nVersion: " + version + "\nOnline: " + online + status);
                            SaveLogic.SaveFile(ip, true, online, motd, version, status);
                        } else {
                            Sender.send(3, "Server: " + ip + " Not found!");
                            SaveLogic.SaveFile(ip, false, null, null, null, null);
                        }
                    } catch (Exception e) {
                        Sender.send(3, "Searching " + ip + " error:\n" + Arrays.toString(e.getStackTrace()));
                    }
                });

                // TODO
                // commandChecker.saver(ipRaw);
            }
        } catch (Exception e) {
            Sender.send(3, e.getMessage());
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
        Sender.send(3, "Finally!");
        SaveLogic.Finaly(ipRaw);
    }
}

