package delta.cion;

import delta.cion.config.CreateConf;
import delta.cion.scan.FuLL;
import delta.cion.scan.ScannerMainClass;
import delta.cion.util.Sender;

import java.net.InetAddress;
import java.util.Scanner;

public class PepeS {
    private static boolean valid = false;
    private static boolean finaly = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CreateConf.confCreate();

        while (!finaly) {
            while (!valid) {
                try {
                    Sender.send(1, "Enter IP or Domain: ");
                    String ip = scanner.nextLine();
                    ip = InetAddress.getByName(ip).getHostAddress();
                    Sender.send(3, "IP set -> &6"+ip);

                    Sender.send(1, "Enter portStart: ");
                    int portStart = Integer.parseInt(scanner.nextLine());
                    Sender.send(3, "Start from -> &6"+portStart);

                    Sender.send(1, "Enter portFinal: ");
                    int portFinal = Integer.parseInt(scanner.nextLine());
                    Sender.send(3, "Final port -> &6"+portFinal);

                    Sender.send(1, "Threads modifier (threads * X) (DON`T SET 0!!): ");
                    int threadsz = Integer.parseInt(scanner.nextLine());
                    Sender.send(3, "Threads modifier -> &6"+threadsz);

                    Sender.send(1, "Enter sleep timer (s): ");
                    int timer = Integer.parseInt(scanner.nextLine());
                    Sender.send(3, "Timer set -> &6"+timer);

                    Sender.send(1, "Scan full ip node? (true/false) (0.0.0.0/255): ");
                    boolean fullScan = Boolean.parseBoolean(scanner.nextLine());
                    Sender.send(3, "Scan full ip? -> &6"+fullScan);

                    if (fullScan) {
                        Sender.send(1, "Enter start ip (0.0.0.X): ");
                        int ipStart = Integer.parseInt(scanner.nextLine());
                        Sender.send(3, "Start ip -> &6X.X.X."+ipStart);

                        if (ipStart >255) Sender.send(3, "Max int - 255");
                        if (ipStart >255) break;

                        Sender.send(1, "Enter final ip (0.0.0.X/Y): ");
                        int ipFinal = Integer.parseInt(scanner.nextLine());
                        Sender.send(3, "Final ip -> &6X.X.X.X/"+ipFinal);

                        if (ipFinal >255) Sender.send(3, "Max int - 255");
                        if (ipFinal >255) break;

                        FuLL.Start(ip, portStart, portFinal, ipStart, ipFinal, threadsz, timer);
                    } else {
                        ScannerMainClass scannerMainClass = new ScannerMainClass();
                        scannerMainClass.Start(ip, portStart, portFinal, threadsz, timer);
                    }
                    valid = true;
                    finaly = true;
                } catch (Throwable e) {
                    Sender.send(3, e.getMessage());
                }
            }
        }
    }
}