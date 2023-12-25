package games.kingdoms.kingdoms.admin.customitems.customores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RefinedGold {

    public static final Material REFINED_GOLD_ORE = Material.GOLD_BLOCK;
    public static final String NAME = ChatColor.GOLD.toString() + ChatColor.BOLD + "Refined Gold Ore";
    public static final int MAX_VEIN_SIZE = 4;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 16;
    public static final ItemStack DROPS = new ItemStack(Material.GOLD_INGOT, 1);
}
