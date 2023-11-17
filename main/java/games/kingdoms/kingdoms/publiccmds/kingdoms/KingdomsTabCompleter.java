package games.kingdoms.kingdoms.publiccmds.kingdoms;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KingdomsTabCompleter implements TabCompleter {

    private final Kingdoms plugin;

    public KingdomsTabCompleter(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> args1 = new ArrayList<>();
            args1.add("claim");
            args1.add("unclaim");
            args1.add("create");
            args1.add("disband");
            args1.add("invite");
            args1.add("uninvite");
            args1.add("transfer");
            args1.add("kick");
            args1.add("promote");
            args1.add("demote");
            args1.add("join");
            args1.add("leave");
            args1.add("kick");
            args1.add("map");
            args1.add("setspawn");
            args1.add("spawn");
            args1.add("rename");

            return args1;
        }

        if (args.length == 2) {
            List<String> args2 = new ArrayList<>();
            if (args[0].equalsIgnoreCase("transfer") || args[0].equals("disband")) {
                args2.add(plugin.getKingdoms().get(player.getUniqueId().toString()));
            }
            return args2;
        }
        return null;
    }
}
