package delta.cion.util;

import java.util.HashMap;
import java.util.Map;

public class Colorize {

    private static final Map<String, String> colorMap = new HashMap<>();

    static {
        colorMap.put("\\§0", "\u001B[30m");          // Черный
        colorMap.put("\\§1", "\u001B[0;34m");        // Темно-Синий
        colorMap.put("\\§2", "\u001B[32m");          // Темно-Зеленый
        colorMap.put("\\§3", "\u001B[0;36m");        // Темно голубой
        colorMap.put("\\§4", "\u001B[0;31m");        // Темно-Красный
        colorMap.put("\\§5", "\u001B[0;35m");        // Фиолетовый
        colorMap.put("\\§6", "\u001B[0;33m");        // Зеленый
        colorMap.put("\\§7", "\u001B[0;37m");        // Серый
        colorMap.put("\\§8", "\u001B[0;90m");        // Темно-Серый
        colorMap.put("\\§9", "\u001B[0;94m");        // Синий
        colorMap.put("\\§a", "\u001B[0;92m");        // Зелёный
        colorMap.put("\\§b", "\u001B[0;96m");        // Голубой
        colorMap.put("\\§c", "\u001B[0;91m");        // Красный
        colorMap.put("\\§d", "\u001B[0;95m");        // Розовый
        colorMap.put("\\§e", "\u001B[0;93m");        // Желтый
        colorMap.put("\\§f", "\u001B[0;97m");        // Белый

        colorMap.put("\\§r", "\u001B[0m");           // Ресет


        colorMap.put("\\§l", "");                    // удалено
        colorMap.put("\\§k", "");                    // удалено
        colorMap.put("\\§m", "");                    // удалено
        colorMap.put("\\§o", "");                    // удалено
        colorMap.put("\\§n", "");                    // удалено
    }

    public static String colorize(String content) {
        content = content+"§r";
        return colorMap.entrySet().stream()
                .reduce(content.replaceAll("&", "§"), (str, entry) ->
                        str.replaceAll(entry.getKey(), entry.getValue()), String::concat);
    }
}
