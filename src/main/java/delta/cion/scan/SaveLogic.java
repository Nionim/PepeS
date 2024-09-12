package delta.cion.scan;

import club.minnced.discord.webhook.WebhookClient;
import com.fasterxml.jackson.databind.JsonNode;
import delta.cion.util.JsonNodes;
import delta.cion.util.Sender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class SaveLogic {

    public static void SaveFile(String ip, boolean status, String online, JsonNodes jsonNodes) {

        File Save;

        String pathResult = "logs/online-%s.txt";
        String pathNoResult = "logs/offline-%s.txt";

        String fileIP = ip.substring(0, ip.indexOf(":"));

        File onlineSave = new File(String.format(pathResult, fileIP));
        File offlineSave = new File(String.format(pathNoResult, fileIP));

        try {
            if (!offlineSave.exists()) offlineSave.createNewFile();
            if (!onlineSave.exists()) onlineSave.createNewFile();
        } catch (Exception e) {
            Sender.send(3, "File create error: " + e.getMessage());
        }

        if (status) {
            Save = onlineSave;
        } else {
            Save = offlineSave;
        }

        try (FileWriter fw = new FileWriter(Save.getAbsoluteFile(), true)) {
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.newLine();
                if (status) {
                    bw.write("Server: " + ip);
                    bw.newLine();
                    bw.write(jsonNodes.get("motd.clean") + "\nOnline: " + online);
                } else {
                    bw.write("Server: " + ip + " Not found!");
                }
            }
        } catch (Exception e) {
            Sender.send(3, "Save data error: "+e.getMessage());
        }
    }

    public static void Finaly(String ipRaw) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://discord.com/api/webhooks/1283149247574708296/OSYFjqpvuWa5AarzfzlE1N0v_U0aUAFcX-ldQcNnROUWfhU8TOycntUFifzV1S6zK8sq");
        urls.add("https://discord.com/api/webhooks/1283149404022378597/5H5buGIO6s89WnD_31FzpG8k4eFcJLDk4D9p3Y8XmOYsznKI7JyAaKJbbHryZaKOK8Tk");

        try (WebhookClient client = WebhookClient.withUrl(urls.get(0))) {
            File serverList = new File(String.format("online-%s.txt", ipRaw));
            boolean tr = !Files.readString(serverList.toPath()).trim().isEmpty();
            if (tr) {
                client.send(serverList);
                Sender.send(3, "&4Online list sent...");
            } else serverList.delete();
        } catch (Exception ignored) {}

        try (WebhookClient client = WebhookClient.withUrl(urls.get(1))) {
            File serverList = new File(String.format("offline-%s.txt", ipRaw));
            boolean tr = !Files.readString(serverList.toPath()).trim().isEmpty();
            if (tr) {
                client.send(serverList);
                Sender.send(3, "&4Offline list sent...");
            } else serverList.delete();
        } catch (Exception ignored) {}
    }
}
