package games.kingdoms.kingdoms.admin.gamemodes;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Adventure implements CommandExecutor {

    private final Kingdoms plugin;

    public Adventure(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (sender.hasPermission("kingdoms.set.gamemode")) {
                if (args.length == 0) {
                    if (!player.getGameMode().equals(GameMode.ADVENTURE)) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ChatColor.GRAY + "Gamemode set to " + ChatColor.LIGHT_PURPLE + "Adventure");
                    } else {
                        player.sendMessage(ChatColor.RED + "You are already in gamemode " + ChatColor.LIGHT_PURPLE + "Adventure");
                    }
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!target.getGameMode().equals(GameMode.ADVENTURE)) {
                            target.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage(ChatColor.GRAY + "You set " + ChatColor.WHITE + target.getName() + ChatColor.GRAY + "'s gamemode to " + ChatColor.LIGHT_PURPLE + "Adventure");
                            target.sendMessage(ChatColor.GRAY + "Your gamemode was set to " + ChatColor.LIGHT_PURPLE + "Adventure ");
                        } else {
                            player.sendMessage(target.getName() + ChatColor.RED + " is already in gamemode " + ChatColor.LIGHT_PURPLE + "Adventure");
                        }
                    } else {
                        player.sendMessage(args[0] + ChatColor.GRAY + " is not online");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            }
        } else {
            sender.sendMessage("You must be a player to execute this command");
        }
        return true;
    }
}
