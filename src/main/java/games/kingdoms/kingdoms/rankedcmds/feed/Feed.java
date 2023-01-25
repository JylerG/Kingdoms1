package games.kingdoms.kingdoms.rankedcmds.feed;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Feed implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute commands.");
            return true;
        }
        if (player.hasPermission("kingdoms.feed")) {
            player.setFoodLevel(25);
            player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You filled your hunger completely");
        } else {
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You do not have permission to use this command");
        }
        return true;
    }
}
