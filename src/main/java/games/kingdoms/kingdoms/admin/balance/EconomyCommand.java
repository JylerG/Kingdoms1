package games.kingdoms.kingdoms.admin.balance;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyCommand implements CommandExecutor {

    private Kingdoms plugin;

    public EconomyCommand(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            // /eco give <player> <amount>
            if (player.hasPermission("kingdoms.economy")) {
                switch (args.length) {
                    case 0:
                    case 1:
                    case 2:
                        player.sendMessage(ChatColor.GOLD + "Usage: /eco give <player> <amount>");
                        break;
                    case 3:
                        Player target = Bukkit.getPlayer(args[1]);
                        if (!args[1].equalsIgnoreCase("*") && target == null) {
                            player.sendMessage(args[1] + ChatColor.RED + " is not online");
                        } else {
                            if (args[0].equalsIgnoreCase("give")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        plugin.getMoney().put(p.getUniqueId().toString(), plugin.getMoney().get(p.getUniqueId().toString()) + Integer.parseInt(args[2]));
                                        p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.GREEN + " coins from " + ChatColor.WHITE + "CONSOLE");
                                    }
                                } else {
                                    plugin.getMoney().put(target.getUniqueId().toString(), plugin.getMoney().get(target.getUniqueId().toString()) + Integer.parseInt(args[2]));
                                    target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.GREEN + " coins from " + ChatColor.WHITE + "CONSOLE");
                                }
                                return true;
                            }
                            if (args[0].equalsIgnoreCase("set")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        if (Integer.parseInt(args[2]) > plugin.getMoney().get(p.getUniqueId().toString())) {
                                            p.sendMessage(ChatColor.GREEN + "Coins set to " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.GREEN + " by " + ChatColor.WHITE + "CONSOLE");
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Coins set to " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.RED + " by " + ChatColor.WHITE + "CONSOLE");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), Integer.parseInt(args[2]));

                                    }
                                } else {
                                    if (Integer.parseInt(args[2]) > plugin.getMoney().get(target.getUniqueId().toString())) {
                                        target.sendMessage(ChatColor.GREEN + "Coins set to " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.GREEN + " by " + ChatColor.WHITE + "CONSOLE");
                                    } else {
                                        target.sendMessage(ChatColor.RED + "Coins set to " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.RED + " by " + ChatColor.WHITE + "CONSOLE");
                                    }
                                    plugin.getMoney().put(target.getUniqueId().toString(), Integer.parseInt(args[2]));
                                }
                                return true;
                            }
                            if (args[0].equalsIgnoreCase("remove")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.RED + " coins by " + ChatColor.WHITE + "CONSOLE");
                                        plugin.getMoney().put(p.getUniqueId().toString(), plugin.getMoney().get(p.getUniqueId().toString()) - Integer.parseInt(args[2]));
                                    }
                                } else {
                                    plugin.getMoney().put(target.getUniqueId().toString(), plugin.getMoney().get(target.getUniqueId().toString()) - Integer.parseInt(args[2]));
                                    target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Integer.parseInt(args[2]) + ChatColor.RED + " coins by " + ChatColor.WHITE + "CONSOLE");
                                }
                            }
                        }
                        break;
                }
            }

        } else {
            sender.sendMessage("You must be a player to execute this command");
        }
        return true;
    }
}
