package games.kingdoms.kingdoms.admin.vanish.commands;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCMD implements CommandExecutor {

    Kingdoms plugin;

    public VanishCMD(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("kingdoms.vanish")) {
                if (plugin.getInvisiblePlayers().contains(p)) {
                    for (Player online_players : Bukkit.getOnlinePlayers()) {
                        online_players.showPlayer(plugin, p);
                    }
                    plugin.getInvisiblePlayers().remove(p);
                    p.sendMessage("§6Vanish §cdisabled");
                } else if (!plugin.getInvisiblePlayers().contains(p)) {
                    for (Player online_players : Bukkit.getOnlinePlayers()) {
                        online_players.hidePlayer(plugin, p);
                    }
                    plugin.getInvisiblePlayers().add(p);
                    p.sendMessage("§6Vanish §aenabled");
                }
            }
        }
        return true;
    }
}
