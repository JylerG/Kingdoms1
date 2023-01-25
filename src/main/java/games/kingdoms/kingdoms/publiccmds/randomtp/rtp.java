package games.kingdoms.kingdoms.publiccmds.randomtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class rtp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.teleport(TeleportUtils.generateLocation(player));

                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();

                player.sendMessage(ChatColor.AQUA + "New Coordinates: " + ChatColor.LIGHT_PURPLE + " " + x + " " + y + " " + z);

            } else if (args.length == 1) {

                if (player.hasPermission("rtp.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    Objects.requireNonNull(target).teleport(TeleportUtils.generateLocation(target));

                    int x = target.getLocation().getBlockX();
                    int y = target.getLocation().getBlockY();
                    int z = target.getLocation().getBlockZ();

                    player.sendMessage(ChatColor.RED + "You teleported " + ChatColor.DARK_RED + target + ChatColor.RED + "to " + ChatColor.LIGHT_PURPLE + " " + x + " " + y + " " + z);


                }

            }
        }
        return true;
    }
}
