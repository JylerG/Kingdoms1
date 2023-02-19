package games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.economy;

import games.kingdoms.kingdoms.Kingdoms;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateAccount implements CommandExecutor {

    public CreateAccount(Kingdoms plugin) {
        this.plugin = plugin;
    }

    private Kingdoms plugin;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute this command");
        } else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("createaccount") || args[0].equalsIgnoreCase("ca")) {
                    if (!plugin.getPlayerManagerHashMap().containsKey(player.getUniqueId())) {
                        plugin.getPlayerManagerHashMap().put(player.getUniqueId(), new PlayerManager(player.getUniqueId().toString(), 0, 0));
                        MessageManager.playerGood(player, "You created an account successfully");
                    }
                } else {
                    MessageManager.playerBad(player, "You already have an account");
                }
            }
        }
        return true;
    }
}
