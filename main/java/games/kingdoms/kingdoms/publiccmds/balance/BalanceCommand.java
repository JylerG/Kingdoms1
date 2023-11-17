package games.kingdoms.kingdoms.publiccmds.balance;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    private Kingdoms plugin;

    public BalanceCommand(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            switch (args.length) {
                case 0:
                    if (plugin.getMoney().get(player.getUniqueId().toString()) == 1) {
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + plugin.getMoney().get(player.getUniqueId().toString()) + ChatColor.GREEN + " coin");
                    } else {
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + plugin.getMoney().get(player.getUniqueId().toString()) + ChatColor.GREEN + " coins");
                    }
                    break;

                case 1:
                    Player target = Bukkit.getPlayer(args[0]);
                    if (plugin.getMoney().get(target.getUniqueId().toString()) == 1) {
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + plugin.getMoney().get(target.getUniqueId().toString()) + ChatColor.GREEN + " coin");
                    } else {
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + plugin.getMoney().get(target.getUniqueId().toString()) + ChatColor.GREEN + " coins");
                    }
                    break;
            }

        } else {
            sender.sendMessage("You do not have permission to execute this command");
        }

        return true;
    }
}
