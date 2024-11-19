package delta.cion.scan;

import club.minnced.discord.webhook.WebhookClient;
import delta.cion.util.Colorize;
import delta.cion.messages.Sender;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveLogic {

    public static void SaveFile(String ip, boolean status, String online, String motd, String version, String connect) {

        motd = Colorize.chrome(motd);

        File Save = getFile(ip, status);

        try (FileWriter fw = new FileWriter(Save.getAbsoluteFile(), true)) {
            if (!Save.exists()) Save.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.newLine();
                if (status) {
                    bw.write("Server: " + ip);
                    bw.newLine();
                    bw.write( motd + "Version: " + version + "\nOnline: " + online + connect);
                } else {
                    bw.write("Server: " + ip + " Not found!");
                }
            }
        } catch (Exception e) {
            Sender.send(3, "Save data error: "+e.getMessage());
        }
    }

    private static @NotNull File getFile(String ip, boolean status) {
        File Save;

        String fileIP = ip.substring(0, ip.indexOf(":"));

        File logs = new File("logs");
        if (!logs.exists()) logs.mkdir();
        File ipFolder = new File(String.format("logs/%s", fileIP));
        if (!ipFolder.exists()) ipFolder.mkdir();

        String pathResult = "logs/%s/online-%s.txt";
        String pathNoResult = "logs/%s/offline-%s.txt";

        File onlineSave = new File(String.format(pathResult, fileIP, fileIP));
        File offlineSave = new File(String.format(pathNoResult, fileIP, fileIP));

        if (status) {
            Save = onlineSave;
        } else {
            Save = offlineSave;
        }
        return Save;
    }

    public static void Finaly(String ipRaw) {
        Sender.send(3, "&4List sender started...");

        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://discord.com/api/webhooks/1283149247574708296/OSYFjqpvuWa5AarzfzlE1N0v_U0aUAFcX-ldQcNnROUWfhU8TOycntUFifzV1S6zK8sq");
        urls.add("https://discord.com/api/webhooks/1283149404022378597/5H5buGIO6s89WnD_31FzpG8k4eFcJLDk4D9p3Y8XmOYsznKI7JyAaKJbbHryZaKOK8Tk");

        try (WebhookClient client = WebhookClient.withUrl(urls.get(0))) {
            Sender.send(3, "&4Online-list webhook started...");
            File serverList = new File(String.format("logs/%s/online-%s.txt",ipRaw, ipRaw));
            boolean tr = !Files.readString(serverList.toPath()).trim().isEmpty();
            if (tr) {
                Sender.send(3, "&4Online list sent...");
                client.send(serverList);
            } else {
                Sender.send(3, "&4Offline list is empty... Deleting..");
                serverList.delete();
            }
        } catch (Exception e) {
            Sender.send(3, Arrays.toString(e.getStackTrace()));
        }

        try (WebhookClient client = WebhookClient.withUrl(urls.get(1))) {
            Sender.send(3, "&4Offline-list webhook started...");
            File serverList = new File(String.format("logs/%s/offline-%s.txt",ipRaw, ipRaw));
            boolean tr = !Files.readString(serverList.toPath()).trim().isEmpty();
            if (tr) {
                Sender.send(3, "&4Offline list sent...");
                client.send(serverList);
            } else {
                Sender.send(3, "&4Offline list is empty... Deleting..");
                serverList.delete();
            }
        } catch (Exception e) {
            Sender.send(3, Arrays.toString(e.getStackTrace()));
        }
    }
}
