package games.kingdoms.kingdoms.admin.customitems.customores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RefinedIron {

    public static final Material REFINED_IRON_ORE = Material.IRON_BLOCK;
    public static final String NAME = ChatColor.GRAY.toString() + ChatColor.BOLD + "Refined Iron Ore";
    public static final int MAX_VEIN_SIZE = 4;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 16;
    public static final ItemStack DROPS = new ItemStack(Material.IRON_INGOT, 1);
}
