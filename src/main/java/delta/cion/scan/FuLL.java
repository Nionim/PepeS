package delta.cion.scan;

public class FuLL {

    public static void Start(String ipRaw, int portStart, int portFinal, int ipStart, int idFinal, int threadsz, int timer) {

        for (int io = ipStart; io <= idFinal; io++) {
            int lastIndex = ipRaw.lastIndexOf('.');

            String ipNon = ipRaw.substring(0, lastIndex + 1);
            String ipFin = ipNon + io;

            ScannerMainClass scannerMainClass = new ScannerMainClass();
            scannerMainClass.Start(ipFin, portStart, portFinal, threadsz, timer);
        }
    }
}