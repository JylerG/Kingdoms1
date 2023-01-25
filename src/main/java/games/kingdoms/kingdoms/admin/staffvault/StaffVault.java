package games.kingdoms.kingdoms.admin.staffvault;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class StaffVault implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("kingdoms.staffvault")) {

                ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
                pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
                pickaxe.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                pickaxe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
                pickaxe.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
                pickaxe.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
                pickaxe.addUnsafeEnchantment(Enchantment.MENDING, 10);

                ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
                axe.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
                axe.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
                axe.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
                axe.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
                axe.addUnsafeEnchantment(Enchantment.MENDING, 10);

                ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
                shovel.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
                shovel.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                shovel.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
                shovel.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
                shovel.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
                shovel.addUnsafeEnchantment(Enchantment.MENDING, 10);

                ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
                sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                sword.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
                sword.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
                sword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
                sword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
                sword.addUnsafeEnchantment(Enchantment.MENDING, 10);


                player.getInventory().addItem(pickaxe);
                player.getInventory().addItem(axe);
                player.getInventory().addItem(shovel);
                player.getInventory().addItem(sword);
            }
        }
        return true;
    }
}
