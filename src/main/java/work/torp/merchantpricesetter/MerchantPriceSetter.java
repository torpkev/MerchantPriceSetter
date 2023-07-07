package work.torp.merchantpricesetter;

import com.ibexmc.internal.Internal;
import com.ibexmc.internal.data.Data;
import com.ibexmc.internal.messaging.Debug;
import com.ibexmc.internal.messaging.Error;
import com.ibexmc.internal.messaging.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import work.torp.merchantpricesetter.listeners.InventoryListener;
import work.torp.merchantpricesetter.util.MPSConfig;

public class MerchantPriceSetter extends JavaPlugin {

    private Internal ibexInternal = null;
    private Data data = null;
    private Debug debug = null;
    private Log log = null;
    private Error error = null;

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
        debug.log("MerchantPriceSetter", "loadEventListeners", "Starting event listeners");
        try {
            Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        } catch (Exception ex) {
            error.save(
                    "MPS_LEL_001",
                    "MerchantPriceSetter",
                    "loadEventListeners",
                    "Unexpected Error",
                    ex.getMessage(),
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );
        }
    }

    public Debug getDebug() {
        return debug;
    }

    public Log getLog() {
        return log;
    }

    public Error getError() {
        return error;
    }

    @Override
    public void onEnable() {

        instance = this;

        ibexInternal = new Internal(this, displayName);
        data = ibexInternal.getData();
        debug = ibexInternal.getDebug();
        log = ibexInternal.getLog();
        error = ibexInternal.getError();

        //data.setDebugMode(true);

        log.log("&6>&b=========================================");
        log.log("&6>&f" + displayName + " - " + getDescription().getVersion());
        log.log("&6>&b=========================================");

        saveDefaultConfig();

        config = new MPSConfig(this);

        loadEventListeners();

    }
    @Override
    public void onDisable() {

    }
}
