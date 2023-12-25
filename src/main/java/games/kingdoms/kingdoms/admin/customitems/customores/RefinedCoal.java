package games.kingdoms.kingdoms.admin.customitems.customores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RefinedCoal {

    public static final Material REFINED_COAL_ORE = Material.COAL_BLOCK;
    public static final String NAME = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Refined Coal Ore";
    public static final int MAX_VEIN_SIZE = 4;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 16;
    public static final ItemStack DROPS = new ItemStack(Material.COAL, 1);
}
