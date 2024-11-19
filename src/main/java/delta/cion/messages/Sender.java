package delta.cion.messages;

import delta.cion.util.Colorize;

public class Sender {

    private static Logger logger;

    private static final String prefix = Colorize.colorize("&7Katara&404 &8>>&r ");
    private static final boolean debug = true;

    private static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
            return logger;
        }
        else return logger;
    }

    public static void send(int level, String content) {
        getLogger().log(content);
        switch (level) {
            case 52 -> {
                if (debug) System.out.println(prefix + content + Colorize.colorize(" (DEBUG!)"));
            }
            case 0 -> System.out.print(Colorize.colorize(content));
            case 1 -> System.out.print(prefix + Colorize.colorize(content));
            case 2 -> System.out.println(Colorize.colorize(content));
            case 3 -> System.out.println(prefix + Colorize.colorize(content));
        }
    }

    public static void sendLogo() {
        send(2, logo);
    }

    private static final String logo = """
                  ##########*########+=#***++++#+=**+####*##########
                  *******#*******++*:--:=::.:-:-+*=+****************
                  **************===+::----------::==:=+-+***********
                  **********==+-:-----=::-==--=-:---::=*-=-+********
                  **********+------==---===::::=--:==---:-++***+****
                  ********------=-:=--:-=-::-::----==-:-----:=+***+*
                  +*++*==+-----=++====-:-=--+==:======+=+==--=-++*+*
                  ++++=-+=====+--=+==+=-+##++*=+=+*+++*++++=+++==+++
                  +++++-=--::-:---:--=*++***+=+=-*#*-=---=-:--++++++
                  ++=++=-:::-+--=+=+++==*+++===+-+==---:--:-:-==+=++
                  =====::===+*+==+===*+=-+%*@=-=+#+=+*=++*+=-:::====
                  ====-::=-==-=---=+*+#*--=%@==**++++=-=-=+=+=-:====
                  -------=-=----+---=+%#--=%@#**+--:=-==---===------
                  --------------------=%%#%@%=---:------=-----------
                  -----------------------#@@#-----------------------
                  ----------------------*@@@@*----------------------
                  +++++++++===++++++*#%@@@@@@@%%#*+++++===++++++=+++
                  +++++++++**#%%%%%%%@@@@@@@@@@@%%%%%%%%%%%#*+++++++
                  ****************##%%%%%%%%%%%%%%%%#***************

            8888b.  888888 88     888888    db        dP""b8 88  dP"Yb  88b 88
             8I  Yb 88__   88       88     dPYb      dP   `" 88 dP   Yb 88Yb88
             8I  dY 88""   88  .o   88    dP__Yb     Yb      88 Yb   dP 88 Y88
            8888Y"  888888 88ood8   88   dP    Yb     YboodP 88  YbodP  88  Y8
    """;
}
