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
                                        long moneyValue = plugin.getMoney().get(player.getUniqueId().toString());

                                        String formattedMoney;
                                        if (moneyValue == 0) {
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + 0 + ChatColor.GREEN + "coins");
                                        }
                                        if (moneyValue == 1) {
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + 1 + ChatColor.GREEN + "coin");
                                        }
                                        if (moneyValue > 1 && moneyValue < 1_000.0) {
                                            // Just display the number as is
                                            formattedMoney = String.valueOf(moneyValue);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0) {
                                            // Display in thousands (K)
                                            formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000) {
                                            // Display in millions (M)
                                            formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L) {
                                            // Display in billions (B)
                                            formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                            // Display in trillions (T)
                                            formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                            // Display in quadrillions (Q)
                                            formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                            // Display in quintillions (QU)
                                            formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        } else if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L) {
                                            // Display in sextillions (S)
                                            formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), plugin.getMoney().get(p.getUniqueId().toString()) + Long.parseLong(args[2]));
                                    }
                                } else {
                                    plugin.getMoney().put(target.getUniqueId().toString(), plugin.getMoney().get(target.getUniqueId().toString()) + Long.parseLong(args[2]));
                                    target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.GREEN + " coins");
                                }
                                return true;
                            }
                            if (args[0].equalsIgnoreCase("set")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        long moneyValue = plugin.getMoney().get(player.getUniqueId().toString());

                                        String formattedMoney;
                                        if (moneyValue == 0) {
                                            p.sendMessage(ChatColor.GREEN + "Your balance was set to " + ChatColor.WHITE + 0 + ChatColor.GREEN + "coins");
                                        }
                                        if (moneyValue == 1) {
                                            p.sendMessage(ChatColor.GREEN + "Your balance was set to " + ChatColor.WHITE + 1 + ChatColor.GREEN + "coin");
                                        }
                                        if (moneyValue > 1 && moneyValue < 1_000.0) {
                                            // Just display the number as is
                                            formattedMoney = String.valueOf(moneyValue);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0) {
                                            // Display in thousands (K)
                                            formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000) {
                                            // Display in millions (M)
                                            formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L) {
                                            // Display in billions (B)
                                            formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                            // Display in trillions (T)
                                            formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                            // Display in quadrillions (Q)
                                            formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                            // Display in quintillions (QU)
                                            formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        } else if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L) {
                                            // Display in sextillions (S)
                                            formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), Long.parseLong(args[2]));
                                    }
                                } else {
                                    long moneyValue = plugin.getMoney().get(player.getUniqueId().toString());

                                    String formattedMoney;
                                    if (moneyValue == 0) {
                                        target.sendMessage(ChatColor.GREEN + "Your balance was set to " + ChatColor.WHITE + 0 + ChatColor.GREEN + " coins");
                                    }
                                    if (moneyValue == 1) {
                                        target.sendMessage(ChatColor.GREEN + "Your balance was set to " + ChatColor.WHITE + 1 + ChatColor.GREEN + " coin");
                                    }
                                    if (moneyValue > 1 && moneyValue < 1_000.0) {
                                        // Just display the number as is
                                        formattedMoney = String.valueOf(moneyValue);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0) {
                                        // Display in thousands (K)
                                        formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000) {
                                        // Display in millions (M)
                                        formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L) {
                                        // Display in billions (B)
                                        formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                        // Display in trillions (T)
                                        formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                        // Display in quadrillions (Q)
                                        formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L) {
                                        // Display in quintillions (QU)
                                        formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    } else if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L) {
                                        // Display in sextillions (S)
                                        formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + ChatColor.YELLOW + " coins");
                                    }
                                    plugin.getMoney().put(target.getUniqueId().toString(), Long.parseLong(args[2]));
                                }
                                return true;
                            }
                            if (args[0].equalsIgnoreCase("remove")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        long moneyValue = plugin.getMoney().get(p.getUniqueId().toString());

                                        String formattedMoney;
                                        if (moneyValue == 0) {
                                            target.sendMessage(ChatColor.RED + "Someone tried to remove money from you but couldn't as you don't have anything");
                                        }
                                        if (moneyValue == 1 && Long.parseLong(args[2]) <= 1) {
                                            target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coin");
                                        }
                                        if (moneyValue > 1 && moneyValue < 1_000.0 && Long.parseLong(args[2]) <= moneyValue) {
                                            // Just display the number as is
                                            formattedMoney = String.valueOf(moneyValue);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0 && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in thousands (K)
                                            formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000 && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in millions (M)
                                            formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in billions (B)
                                            formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in trillions (T)
                                            formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in quadrillions (Q)
                                            formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in quintillions (QU)
                                            formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        } else if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                            // Display in sextillions (S)
                                            formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), plugin.getMoney().get(p.getUniqueId().toString()) - Long.parseLong(args[2]));

                                    }
                                } else {
                                    long moneyValue = plugin.getMoney().get(target.getUniqueId().toString());

                                    String formattedMoney;
                                    if (moneyValue == 0) {
                                        target.sendMessage(ChatColor.RED + "Someone tried to remove money from you but couldn't as you don't have anything");
                                    }
                                    if (moneyValue == 1 && Long.parseLong(args[2]) <= 1) {
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coin");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    }
                                    if (moneyValue > 1 && moneyValue < 1_000.0 && Long.parseLong(args[2]) <= moneyValue) {
                                        // Just display the number as is
                                        formattedMoney = String.valueOf(moneyValue);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000.0 && moneyValue < 1_000_000.0 && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in thousands (K)
                                        formattedMoney = String.format("%.1fK", moneyValue / 1_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000_000.0 && moneyValue < 1_000_000_000 && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in millions (M)
                                        formattedMoney = String.format("%.1fM", moneyValue / 1_000_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000_000_000.0 && moneyValue < 1_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in billions (B)
                                        formattedMoney = String.format("%.1fB", moneyValue / 1_000_000_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in trillions (T)
                                        formattedMoney = String.format("%.1fT", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in quadrillions (Q)
                                        formattedMoney = String.format("%.1fQ", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000_000_000_000L && moneyValue < 1_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in quintillions (QU)
                                        formattedMoney = String.format("%.1fQU", moneyValue / 1_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You cannot remove more than" + ChatColor.WHITE + moneyValue + "coin from this person");
                                    } if (moneyValue > 1_000_000_000_000_000L && moneyValue < 1_000_000_000_000_000_000L && Long.parseLong(args[2]) <= moneyValue) {
                                        // Display in sextillions (S)
                                        formattedMoney = String.format("%.1fS", moneyValue / 1_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + Long.parseLong(args[2]) + ChatColor.RED + " coins");
                                    }
                                    plugin.getMoney().put(target.getUniqueId().toString(), plugin.getMoney().get(target.getUniqueId().toString()) - Long.parseLong(args[2]));
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
