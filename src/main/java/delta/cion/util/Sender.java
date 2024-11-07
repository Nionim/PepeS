package delta.cion.util;

public class Sender {

    private static final String prefix = Colorize.colorize("&7Delta&4Project &8>>&r ");
    private static final boolean debug = true;

    public static void send(int level, String content) {

        switch (level) {
            case 52 -> {
                if (debug) System.out.println(prefix+content+Colorize.colorize(" (DEBUG!)"));
            }
            case 0 -> System.out.print(Colorize.colorize(content));
            case 1 -> System.out.print(prefix+Colorize.colorize(content));
            case 2 -> System.out.println(Colorize.colorize(content));
            case 3 -> System.out.println(prefix+Colorize.colorize(content));
        }
    }
}
