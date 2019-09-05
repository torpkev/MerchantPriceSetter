package work.torp.merchantpricesetter.util;

import javafx.util.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import work.torp.merchantpricesetter.MerchantPriceSetter;

import java.util.HashMap;


public class MPSConfig {

    public static class TradeItems {
        private Material material;
        private Enchantment enchantment;
        private Integer enchantmentLevel;
        private Integer amount;
        private Integer minPrice;
        private Integer maxPrice;

        public TradeItems(Material material, Enchantment enchantment, Integer enchantmentLevel, Integer amount, Integer minPrice, Integer maxPrice) {
            this.material = material;
            this.enchantment = enchantment;
            this.enchantmentLevel = enchantmentLevel;
            this.amount = amount;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;

        }

        public Material getMaterial() {
            return material;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public Integer getEnchantmentLevel() {
            return enchantmentLevel;
        }

        public Integer getAmount() {
            return amount;
        }

        public Integer getMinPrice() {
            return minPrice;
        }

        public Integer getMaxPrice() {
            return maxPrice;
        }

        public String toString() {
            String material = "null";
            if (material != null) {
                material = this.material.name();
            }
            String enchantment = "null";
            int level = 0;
            if (this.enchantment != null) {
                enchantment = this.enchantment.toString();
                level = this.enchantmentLevel;
            }
            int amount = this.amount;
            return "Material: " + material + " Enchantment: " + enchantment + " Level: " + level + " Amount: " + amount;
        }

        public int toHashCode() {
            return this.toString().hashCode();
        }
    }

    private HashMap<Integer, TradeItems> tradeItemsPairHashMap = new HashMap();

    public HashMap<Integer, TradeItems> getTradeItems() {
        return tradeItemsPairHashMap;
    }

    public MPSConfig(MerchantPriceSetter plugin) {
        Logging.dev(
                "Config",
                "Constructor(Plugin)",
                "Constructing Config"
        );


        // Getting config
        try {
            if (plugin.getConfig().contains("items")) {
                Logging.log("", "Found items");
                for (String path : plugin.getConfig().getConfigurationSection("items").getKeys(false)) {
                    Logging.log("", "Found items - " + path);
                    if (plugin.getConfig().contains("items." + path + ".material")) {
                        if (plugin.getConfig().isString("items." + path + ".material")) {
                            Material material = Material.AIR;
                            Enchantment enchantment = null;
                            int level = 1;
                            int amount = 1;
                            int minPrice = 1;
                            int maxPrice = 64;
                            material = materialFromName(plugin.getConfig().getString("items." + path + ".material"));
                            if (material != Material.AIR) {
                                if (material.equals(Material.ENCHANTED_BOOK)) {
                                    if (plugin.getConfig().contains("items." + path + ".enchantment")) {
                                        if (plugin.getConfig().isString("items." + path + ".enchantment")) {
                                            enchantment = enchantmentFromName(plugin.getConfig().getString("items." + path + ".enchantment"));
                                            if (enchantment != null) {
                                                if (plugin.getConfig().contains("items." + path + ".level")) {
                                                    if (plugin.getConfig().isInt("items." + path + ".level")) {
                                                        level = plugin.getConfig().getInt("items." + path + ".level");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (plugin.getConfig().contains("items." + path + ".amount")) {
                                    if (plugin.getConfig().contains("items." + path + ".amount")) {
                                        if (plugin.getConfig().isInt("items." + path + ".amount")) {
                                            amount = plugin.getConfig().getInt("items." + path + ".amount");
                                            if (amount < 0 || amount > 64) {
                                                amount = 1;
                                            }
                                        }
                                    }
                                }
                                if (plugin.getConfig().contains("items." + path + ".min_price")) {
                                    if (plugin.getConfig().contains("items." + path + ".min_price")) {
                                        if (plugin.getConfig().isInt("items." + path + ".min_price")) {
                                            minPrice = plugin.getConfig().getInt("items." + path + ".min_price");
                                            if (minPrice < 0 || minPrice > 64) {
                                                minPrice = 1;
                                            }
                                        }
                                    }
                                }
                                if (plugin.getConfig().contains("items." + path + ".max_price")) {
                                    if (plugin.getConfig().contains("items." + path + ".max_price")) {
                                        if (plugin.getConfig().isInt("items." + path + ".max_price")) {
                                            maxPrice = plugin.getConfig().getInt("items." + path + ".max_price");
                                            if (maxPrice < 0 || maxPrice > 64) {
                                                maxPrice = 64;
                                            }
                                        }
                                    }
                                }
                                TradeItems tradeItems = new TradeItems(material, enchantment, level, amount, minPrice, maxPrice);

                                Logging.dev("MPSConfig", "Constructor", "HashCode: " + tradeItems.toHashCode() + " - " + tradeItems.toString());

                                tradeItemsPairHashMap.put(tradeItems.toHashCode(), tradeItems);
                            } else {
                                Logging.log("", "Material returned as AIR");
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logging.log("Loading Config", "Unexpected Error: " + ex.getMessage());
        }
    }

    private Material materialFromName(String materialName) {
        Material mat;
        if (materialName != null) {
            try {
                mat = Material.getMaterial(materialName);
            } catch (Exception ex) {
                Logging.dev("MPSConfig", "materialFromName", "Invalid Material name: " + materialName + " - " + ex.getMessage());
                mat = Material.AIR;
            }
        } else{
            Logging.dev("MPSConfig", "materialFromName", "Invalid Material name: " + materialName);
            mat = Material.AIR;
        }
        return mat;
    }
    private Enchantment enchantmentFromName(String enchantmentName) {
        Enchantment enchantment;
        // Ugly switch statement - work out how to get it by NameSpacedKey
        switch (enchantmentName.toUpperCase()) {
            case "ARROW_DAMAGE":
                enchantment = Enchantment.ARROW_DAMAGE;
                break;
            case "ARROW_FIRE":
                enchantment = Enchantment.ARROW_FIRE;
                break;
            case "ARROW_INFINITE":
                enchantment = Enchantment.ARROW_INFINITE;
                break;
            case "ARROW_KNOCKBACK":
                enchantment = Enchantment.ARROW_KNOCKBACK;
                break;
            case "BINDING_CURSE":
                enchantment = Enchantment.BINDING_CURSE;
                break;
            case "CHANNELING":
                enchantment = Enchantment.CHANNELING;
                break;
            case "DAMAGE_ALL":
                enchantment = Enchantment.DAMAGE_ALL;
                break;
            case "DAMAGE_ARTHROPODS":
                enchantment = Enchantment.DAMAGE_ARTHROPODS;
                break;
            case "DAMAGE_UNDEAD":
                enchantment = Enchantment.DAMAGE_UNDEAD;
                break;
            case "DEPTH_STRIDER":
                enchantment = Enchantment.DEPTH_STRIDER;
                break;
            case "DIG_SPEED":
                enchantment = Enchantment.DIG_SPEED;
                break;
            case "DURABILITY":
                enchantment = Enchantment.DURABILITY;
                break;
            case "FIRE_ASPECT":
                enchantment = Enchantment.FIRE_ASPECT;
                break;
            case "FROST_WALKER":
                enchantment = Enchantment.FROST_WALKER;
                break;
            case "IMPALING":
                enchantment = Enchantment.DURABILITY;
                break;
            case "KNOCKBACK":
                enchantment = Enchantment.FIRE_ASPECT;
                break;
            case "LOOT_BONUS_BLOCKS":
                enchantment = Enchantment.FROST_WALKER;
                break;
            case "LOOT_BONUS_MOBS":
                enchantment = Enchantment.LOOT_BONUS_MOBS;
                break;
            case "LOYALTY":
                enchantment = Enchantment.LOYALTY;
                break;
            case "LUCK":
                enchantment = Enchantment.LUCK;
                break;
            case "LURE":
                enchantment = Enchantment.LURE;
                break;
            case "MENDING":
                enchantment = Enchantment.MENDING;
                break;
            case "MULTISHOT":
                enchantment = Enchantment.MULTISHOT;
                break;
            case "OXYGEN":
                enchantment = Enchantment.OXYGEN;
                break;
            case "PIERCING":
                enchantment = Enchantment.PIERCING;
                break;
            case "PROTECTION_ENVIRONMENTAL":
                enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
                break;
            case "PROTECTION_EXPLOSIONS":
                enchantment = Enchantment.PROTECTION_EXPLOSIONS;
                break;
            case "PROTECTION_FALL":
                enchantment = Enchantment.PROTECTION_FALL;
                break;
            case "PROTECTION_FIRE":
                enchantment = Enchantment.PROTECTION_FIRE;
                break;
            case "PROTECTION_PROJECTILE":
                enchantment = Enchantment.PROTECTION_PROJECTILE;
                break;
            case "QUICK_CHARGE":
                enchantment = Enchantment.QUICK_CHARGE;
                break;
            case "RIPTIDE":
                enchantment = Enchantment.RIPTIDE;
                break;
            case "SILK_TOUCH":
                enchantment = Enchantment.SILK_TOUCH;
                break;
            case "SWEEPING_EDGE":
                enchantment = Enchantment.SWEEPING_EDGE;
                break;
            case "THORNS":
                enchantment = Enchantment.THORNS;
                break;
            case "VANISHING_CURSE":
                enchantment = Enchantment.VANISHING_CURSE;
                break;
            case "WATER_WORKER":
                enchantment = Enchantment.WATER_WORKER;
                break;
            default:
                enchantment = null;
                break;
        }
        /*
        * THIS ISN'T WORKING - GET IT SORTED AND GET RID OF THE CASE STATEMENT ABOVE
        if (enchantmentName != null) {
            try {
                NamespacedKey key = NamespacedKey.minecraft(enchantmentName);
                enchantment = Enchantment.getByKey(key);
            } catch (Exception ex) {
                Logging.dev("MPSConfig", "enchantmentFromName", "Invalid Enchantment name: " + enchantmentName + " - " + ex.getMessage());
                enchantment = null;
            }
        } else{
            Logging.dev("MPSConfig", "enchantmentFromName", "Invalid Enchantment name: " + enchantmentName);
            enchantment = null;
        }
        */
        return enchantment;
    }
}
