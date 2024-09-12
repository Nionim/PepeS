package delta.cion.scan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import delta.cion.util.Colorize;
import delta.cion.util.Sender;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ScannerMainClass {

    public static void Start(String ipRaw, int portStart, int portFinal, int threadsz, int timer) {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads * threadsz);

        for (int i = portStart; i <= portFinal; i++) {
            final int port = i;
            executor.execute(() -> {
                String ip = ipRaw + ":" + port;
                String url = "https://api.mcstatus.io/v2/status/java/" + ip;
                try {
                    Random random = new Random(timer);
                    double randomz = random.nextDouble(101) * 100;
                    if (randomz >= 50) Thread.sleep(timer * 100);
                    if (port % 100 == 0) Thread.sleep(10000);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(new URL(url));

                    boolean response = jsonNode.get("online").booleanValue();

                    if (response && jsonNode.get("online") != null) {
                        String motd = Colorize.colorize(jsonNode.get("motd").get("raw").asText().replaceAll(" {2}", " "));
                        String online = jsonNode.get("players").get("online") + "/" + jsonNode.get("players").get("max");

                        Sender.send(3, "Server: " + ip + " Found!\n" + motd + "\nOnline: " + online);
                        SaveLogic.SaveFile(ip, jsonNode, true, online);
                    } else {
                        Sender.send(3, "Server: " + ip + " Not found!");
                        SaveLogic.SaveFile(ip, jsonNode, false, null);
                    }
                } catch (Exception e) {
                    Sender.send(3, "Searching " + ip + " error: " + e.getMessage());
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
}

