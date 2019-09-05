package work.torp.merchantpricesetter.util;

import org.bukkit.ChatColor;

public class Locale {
    public static String colorToString(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
