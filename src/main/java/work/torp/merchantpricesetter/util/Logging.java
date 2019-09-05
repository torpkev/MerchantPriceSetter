package work.torp.merchantpricesetter.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import work.torp.merchantpricesetter.MerchantPriceSetter;

public class Logging {
    private static MerchantPriceSetter plugin = MerchantPriceSetter.getInstance();

    public static void dev(String className, String functionName, String message)
    {
        if (Bukkit.getMotd().equalsIgnoreCase("work.torp.testbed")) {
            ConsoleCommandSender consoleCommandSender = plugin.getServer().getConsoleSender();
            consoleCommandSender.sendMessage("!!! > " + ChatColor.RED + "[" + MerchantPriceSetter.displayName + ".Dev]" + ChatColor.BLUE + "[" + className + "." + functionName + "] " + ChatColor.WHITE + Locale.colorToString(message));
        }
    }

    public static void log(String function, String message)
    {
        ConsoleCommandSender ccsLog = plugin.getServer().getConsoleSender();
        ccsLog.sendMessage(ChatColor.DARK_PURPLE + "[" + MerchantPriceSetter.displayName + "]" + ChatColor.GOLD + "[" + function + "] " + ChatColor.WHITE + Locale.colorToString(message));
    }

}
