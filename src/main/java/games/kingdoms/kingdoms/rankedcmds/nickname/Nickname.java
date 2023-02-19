package games.kingdoms.kingdoms.rankedcmds.nickname;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Nickname implements CommandExecutor {

    private Kingdoms plugin;

    public Nickname(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute this command.");
            return true;
        } else {
            switch (args.length) {
                case 0:
                    if (player.hasPermission("kingdoms.nickname")) {
                        player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Usage: /nick <nickName>");
                        return true;
                    } if (player.hasPermission("kingdoms.nickname.admin")) {
                        player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Usage: /nick <nickName> || /nick <player> <nickName>");
                        return true;
                    }
                    break;

                case 1:
                    if (player.hasPermission("kingdoms.nickname")) {
                        player.setDisplayName(args[0]);
                        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Your nickname was set to " + ChatColor.WHITE + ChatColor.BOLD + args[0]);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("reset") && player.hasPermission("kingdoms.nickname")) {
                        player.setDisplayName(player.getName());
                        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Your name was set to " + ChatColor.WHITE + ChatColor.BOLD + player.getName());
                        return true;
                    }
                    break;

                case 2:
                    if (player.hasPermission("kingdoms.nickname.admin")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        target.setDisplayName(args[1]);
                        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + "'s name to " + ChatColor.WHITE + ChatColor.BOLD + args[1]);
                        target.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Your nickname was set to "  + ChatColor.WHITE + ChatColor.BOLD + args[1] + ChatColor.DARK_PURPLE + ChatColor.BOLD + " by " + ChatColor.WHITE + ChatColor.BOLD + player.getName());
                        return true;
                    }
                    break;
            }
        }
        return true;
    }
}
