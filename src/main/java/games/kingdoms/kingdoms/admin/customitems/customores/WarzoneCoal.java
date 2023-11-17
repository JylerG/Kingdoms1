package games.kingdoms.kingdoms.admin.customitems.customores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WarzoneCoal {

    public static final Material WARZONE_COAL_ORE = Material.COAL_ORE;
    public static final String NAME = ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Warzone Coal Ore";
    public static final int MAX_VEIN_SIZE = 4;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 16;
    public static final ItemStack DROPS = new ItemStack(Material.COAL, 1);
}
