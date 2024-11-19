package delta.cion.messages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Logger implements AutoCloseable {

    private static final ArrayList<String> noLog = new ArrayList<>();
    private final File logFolder = new File("sys-logs");
    private File logFile;

    static {
        noLog.add("Server: .* Not found!.*");
        noLog.add("Server: .* Found!.*");
    }

    public Logger() {
        createFile();
    }

    private void createFile() {
        if (!logFolder.exists() || !logFolder.isDirectory()) {
            if (logFolder.mkdir()) Sender.send(3, "Logs folder created");
            else Sender.send(3, "Logs folder don`t created");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");
        String currentTime = currentDateTime.format(format);

        try {
            logFile = new File(logFolder+"/"+currentTime+".txt");
            if (!logFile.exists() || logFile.isDirectory()) {
                if (logFile.createNewFile()) Sender.send(3, "Logs file created!");
            }
        } catch (Exception e) {
            Sender.send(3, e.getMessage());
        }
    }

    public void log(String content) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH-mm-ss");
        String currentTime = currentDateTime.format(format);

        try (FileWriter fw = new FileWriter(logFile.getAbsoluteFile(), true)) {
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.newLine();
                bw.write("["+currentTime+"] "+content);
            }
        } catch (Exception e) {
            Sender.send(3, "Save data error: "+e.getMessage());
        }

    }

    @Override
    public void close() {
        noLog.clear();
    }
}
