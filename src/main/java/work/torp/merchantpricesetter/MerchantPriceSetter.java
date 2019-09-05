package work.torp.merchantpricesetter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import work.torp.merchantpricesetter.listeners.InventoryListener;
import work.torp.merchantpricesetter.util.Logging;
import work.torp.merchantpricesetter.util.MPSConfig;

public class MerchantPriceSetter extends JavaPlugin {

    // Naming
    public static final String displayName = "MerchantPriceSetter";
    public static final String internalName = "merchantpricesetter";

    // Instance
    private static MerchantPriceSetter instance;
    public static MerchantPriceSetter getInstance() {
        return instance;
    }

    // Config
    private MPSConfig config;
    public MPSConfig getMPSConfig() {
        return this.config;
    }
    public void setMPSConfig(MPSConfig config) {
        this.config = config;
    }

    // Listeners
    public void loadEventListeners() {
        Logging.dev("Stereo", "loadEventListeners()", "Starting Event Listeners");
        try {
            Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        } catch (Exception ex) {
            Logging.log("Load Event Listeners", "Unexpected Error - " + ex.getMessage());
        }
    }

    @Override
    public void onEnable() {

        instance = this;

        Logging.log(
                ">",
                ChatColor.AQUA + "=========================================" + ChatColor.GOLD + " [<]"
        );

        Logging.log(
                "",
                ChatColor.AQUA + "          " + displayName + " - " + getDescription().getVersion() + ChatColor.GOLD + "             "
        );
        Logging.log(
                ">",
                ChatColor.AQUA + "=========================================" + ChatColor.GOLD + " [<]"
        );

        saveDefaultConfig();

        config = new MPSConfig(this);

        loadEventListeners();

    }
    @Override
    public void onDisable() {

    }
}
