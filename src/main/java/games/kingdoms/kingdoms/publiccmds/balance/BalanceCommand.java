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
                    long moneyValue = plugin.getMoney().get(player.getUniqueId().toString());

                    String formattedMoney;
                    if (moneyValue == 0) {
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + 0 + ChatColor.GREEN + "coins");
                    }
                    if (moneyValue == 1) {
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + 1 + ChatColor.GREEN + "coin");
                    }
                    if (moneyValue > 1 && moneyValue < 1_000.0) {
                        // Just display the number as is
                        formattedMoney = String.valueOf(moneyValue);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0) {
                        // Display in thousands (K)
                        formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000) {
                        // Display in millions (M)
                        formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L) {
                        // Display in billions (B)
                        formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                        // Display in trillions (T)
                        formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                        // Display in quadrillions (Q)
                        formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                        // Display in quintillions (QU)
                        formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L) {
                        // Display in sextillions (S)
                        formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                        player.sendMessage(ChatColor.GREEN + "Your balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    }
                    break;

                case 1:
                    Player target = Bukkit.getPlayer(args[0]);
                    moneyValue = plugin.getMoney().get(player.getUniqueId().toString());

                    formattedMoney = null;

                    if (moneyValue == 0) {
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + 0 + ChatColor.GREEN + "coins");
                    }
                    if (moneyValue == 1) {
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + 1 + ChatColor.GREEN + "coin");
                    }
                    if (moneyValue > 1 && moneyValue < 1_000.0) {
                        // Just display the number as is
                        formattedMoney = String.valueOf(moneyValue);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0) {
                        // Display in thousands (K)
                        formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000) {
                        // Display in millions (M)
                        formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L) {
                        // Display in billions (B)
                        formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                        // Display in trillions (T)
                        formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                        // Display in quadrillions (Q)
                        formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                        // Display in quintillions (QU)
                        formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    } else if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L) {
                        // Display in sextillions (S)
                        formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                        player.sendMessage(target.getName() + ChatColor.GREEN + "'s balance is " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                    }
                    break;
            }

        } else {
            sender.sendMessage("You do not have permission to execute this command");
        }

        return true;
    }
}
