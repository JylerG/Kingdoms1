package games.kingdoms.kingdoms.rankedcmds.fly;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Fly implements CommandExecutor {

    private final ArrayList<Player> list_of_flying_people = new ArrayList<>();
    public final Kingdoms plugin;

    public Fly(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute commands.");
            return true;
        }
        if (player.hasPermission("kingdoms.fly")) {
            if (list_of_flying_people.contains(player)) {
                list_of_flying_people.remove(player);
                player.sendMessage(ChatColor.GOLD + "Set fly mode " + ChatColor.RED + "disabled" + ChatColor.GOLD + " for " + ChatColor.DARK_RED + player.getDisplayName());
                player.setAllowFlight(false);
            } else if (!list_of_flying_people.contains(player)) {
                list_of_flying_people.add(player);
                player.sendMessage(ChatColor.GOLD + "Set fly mode " + ChatColor.RED + "enabled" + ChatColor.GOLD + " for " + ChatColor.DARK_RED + player.getDisplayName());
                player.setAllowFlight(true);
            }
        } else {
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You do not have permission to use this command");
        }
        return true;
    }
}