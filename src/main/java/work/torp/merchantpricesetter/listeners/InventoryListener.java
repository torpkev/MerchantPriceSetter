package work.torp.merchantpricesetter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import work.torp.merchantpricesetter.MerchantPriceSetter;
import work.torp.merchantpricesetter.util.MPSConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event)
    {
        if (event.getInventory().getType().equals(InventoryType.MERCHANT)) {
           // Logging.dev("InventoryListener", "onInventoryOpen", "Started: " + event.getInventory().getType().name());
            // Check if the inventory is a Merchant type
            if (event.getInventory().getHolder() instanceof Merchant) {
                // Get the Merchant
                Merchant merchant = (Merchant) event.getInventory().getHolder();
                // Get the recipe list
                List<MerchantRecipe> merchantRecipeList = merchant.getRecipes();

                HashMap<Integer, MPSConfig.TradeItems> tradesMap = MerchantPriceSetter.getInstance().getMPSConfig().getTradeItems();
                if (tradesMap == null) {
                    //Logging.dev("InventoryListener", "onInventoryOpen", "Config list is null");
                    return;
                }

                int recipePosition = 0;
                for (MerchantRecipe merchantRecipe : merchantRecipeList) {
                    Material material;
                    material = merchantRecipe.getResult().getType();
                    if (!material.equals(Material.AIR)) {
                        Enchantment enchantment = null;
                        int level = 1;
                        int amount;
                        int min_price;
                        int max_price;

                        if (merchantRecipe.getResult().getItemMeta() instanceof EnchantmentStorageMeta) {
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) merchantRecipe.getResult().getItemMeta();
                            if (meta.hasStoredEnchants()) {
                                for (Map.Entry<Enchantment, Integer> enchantmentEntry : meta.getStoredEnchants().entrySet()) {
                                    // We should only ever have 1, but if there is more for some insane reason, just get first one
                                    enchantment = enchantmentEntry.getKey();
                                    level = enchantmentEntry.getValue();
                                    if (enchantment != null) {
                                        break;
                                    }
                                }
                            }
                        }
                        amount = merchantRecipe.getResult().getAmount();

                        MPSConfig.TradeItems tradeItems = new MPSConfig.TradeItems(material, enchantment, level, amount, 0, 0);
                        if (tradesMap.containsKey(tradeItems.toHashCode())) {
                            MPSConfig.TradeItems trades = tradesMap.get(tradeItems.toHashCode());
                            min_price = trades.getMinPrice();
                            max_price = trades.getMaxPrice();

                            List<ItemStack> ingredients = merchantRecipe.getIngredients();
                            List<ItemStack> newIngredients = new ArrayList<>();
                            for (ItemStack i : ingredients) {
                                //Logging.log("Enchanted Storage", "Ingredient: " + i.getType().name() + " x" + i.getAmount());
                                if (i.getType().equals(Material.EMERALD)) {
                                    if (i.getAmount() < min_price) {
                                        i.setAmount(min_price);
                                    }
                                    if (i.getAmount() > max_price) {
                                        i.setAmount(max_price);
                                    }
                                }
                                newIngredients.add(i);
                            }
                            merchantRecipe.setIngredients(newIngredients);
                            merchant.setRecipe(recipePosition, merchantRecipe);

                        } else {
                            // Trade item not found
                            MerchantPriceSetter.getInstance().getDebug().log(
                                    "InventoryListener",
                                    "onInventoryOpen",
                                    "Trade Item Not Found"
                            );
                        }

                    } else {
                        // Item is air
                        MerchantPriceSetter.getInstance().getDebug().log(
                                "InventoryListener",
                                "onInventoryOpen",
                                "Trade Item Material is AIR"
                        );
                    }
                }
            } else {
                // merchant is null
                MerchantPriceSetter.getInstance().getDebug().log(
                        "InventoryListener",
                        "onInventoryOpen",
                        "Merchant returned null"
                );
            }
        }
    }
}
