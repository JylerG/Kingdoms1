package games.kingdoms.kingdoms.admin.customitems.customores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WarzoneDiamond {

    public static final Material WARZONE_DIAMOND_ORE = Material.DIAMOND_ORE;
    public static final String NAME = ChatColor.AQUA.toString() + ChatColor.BOLD + "Warzone Diamond Ore";
    public static final int MAX_VEIN_SIZE = 4;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 16;
    public static final ItemStack DROPS = new ItemStack(Material.DIAMOND, 1);
}
