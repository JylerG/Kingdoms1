package games.kingdoms.kingdoms.publiccmds.teleports;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarzoneCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            System.out.println("You must be a player to execute this command");
            return true;
        }
            try {
                for (int i = 10; i > -1; i--) {
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Teleporting to Warzone Spawn in " + ChatColor.WHITE + ChatColor.BOLD + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            return true;
    }
}
