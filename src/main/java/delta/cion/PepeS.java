package delta.cion;

import delta.cion.config.Configuration;
import delta.cion.modular.ModuleCore;
import delta.cion.scan.FuLL;
import delta.cion.scan.ScannerMainClass;
import delta.cion.util.AboutPepeS;
import delta.cion.util.Sender;

import java.net.InetAddress;
import java.util.Scanner;

public class PepeS extends AboutPepeS {

    private static boolean valid = false;
    private static final Scanner scanner = new Scanner(System.in);

    private static String input(String msg) {
        Sender.send(1, msg);
        return scanner.nextLine();
    }

    public static void main(String[] args) {

        saveDefaultConfig();
        ModuleCore.ModuleInitialize();

        while (true) {
            while (!valid) {
                try {

                    String ip = input("Enter IP or Domain: ");
                    ip = InetAddress.getByName(ip).getHostAddress();
                    Sender.send(3, "IP set -> &6"+ip);

                    int portStart = Integer.parseInt(input("Enter portStart: "));
                    Sender.send(3, "Start from -> &6"+portStart);

                    int portFinal = Integer.parseInt(input("Enter portFinal: "));
                    Sender.send(3, "Final port -> &6"+portFinal);

                    if (portStart > portFinal) continue;

                    int threadsz = Integer.parseInt(input("Threads modifier (threads * X) (DON`T SET 0!!): "));
                    Sender.send(3, "Threads modifier -> &6"+threadsz);

                    int timer = Integer.parseInt(input("Enter sleep timer (s): "));
                    Sender.send(3, "Timer set -> &6"+timer);

                    boolean fullScan = Boolean.parseBoolean(input("Scan full ip node? (true/false) (0.0.0.0/255): "));
                    Sender.send(3, "Scan full ip? -> &6"+fullScan);

                    if (fullScan) {
                        int ipStart = Integer.parseInt(input("Enter start ip (0.0.0.X): "));
                        Sender.send(3, "Start ip -> &6X.X.X."+ipStart);

                        if (ipStart >255) Sender.send(3, "Max int - 255");
                        if (ipStart >255) continue;

                        int ipFinal = Integer.parseInt(input("Enter final ip (0.0.0.X/Y): "));
                        Sender.send(3, "Final ip -> &6X.X.X.X/"+ipFinal);

                        if (ipFinal >255) Sender.send(3, "Max int - 255");
                        if (ipFinal >255) continue;

                        FuLL.Start(ip, portStart, portFinal, ipStart, ipFinal, threadsz, timer);
                    } else {
                        ScannerMainClass scannerMainClass = new ScannerMainClass();
                        scannerMainClass.Start(ip, portStart, portFinal, threadsz, timer);
                    }
                    valid = true;
                } catch (Throwable e) {
                    Sender.send(3, e.getMessage());
                }
            }
        }
    }
}