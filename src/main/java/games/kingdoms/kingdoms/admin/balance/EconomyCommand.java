package games.kingdoms.kingdoms.admin.balance;

import games.kingdoms.kingdoms.Kingdoms;
import games.kingdoms.kingdoms.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyCommand implements CommandExecutor {

    private final Kingdoms plugin;

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
                        player.sendMessage(ChatColor.GOLD + "Usage: /eco <command> <player> <amount>");
                        break;
                    case 3:

                        //TODO: Figure out why give and set display the message three times upon command execution
                        //TODO: Figure out why remove command doesn't display anything

                        Player target = Bukkit.getPlayer(args[1]);
                        if (!args[1].equalsIgnoreCase("*") && target == null) {
                            player.sendMessage(args[1] + ChatColor.RED + " is not online");
                        } else {
                            if (args[0].equalsIgnoreCase("give")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        long moneyValue = Long.parseLong(args[2]);

                                        String formattedMoney;
                                        if (Long.parseLong(args[2]) == 0) {
                                            formattedMoney = "0";
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        }
                                        if (Long.parseLong(args[2]) == 1) {
                                            formattedMoney = "1";
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coin");
                                        }
                                        if (Long.parseLong(args[2]) > 1 && Long.parseLong(args[2]) < 1_000) {
                                            // Just display the number as is
                                            formattedMoney = String.valueOf(moneyValue);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000.0 && Long.parseLong(args[2]) < 1_000_000.0) {
                                            // Display in thousands (K)
                                            formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000.0) {
                                            // Display in millions (M)
                                            formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000.0) {
                                            // Display in billions (B)
                                            formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000.0) {
                                            // Display in trillions (T)
                                            formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000.0) {
                                            // Display in quadrillions (Q)
                                            formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000.0) {
                                            // Display in quintillions (QU)
                                            formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (Long.parseLong(args[2]) >= 1_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000_000.0) {
                                            // Display in sextillions (S)
                                            formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.GREEN +"[+] " + ChatColor.WHITE + formattedMoney + " coins");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), plugin.getMoney().get(p.getUniqueId().toString()) + Long.parseLong(args[2]));
                                    }
                                } else {
                                    long moneyValue = Long.parseLong(args[2]);

                                    String formattedMoney;
                                    if (Long.parseLong(args[2]) == 0) {
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + 0 + ChatColor.GREEN + " coins");                                    }
                                    if (Long.parseLong(args[2]) == 1) {
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + 1 + ChatColor.GREEN + " coins");
                                    }
                                    if (Long.parseLong(args[2]) > 1 && Long.parseLong(args[2]) < 1_000) {
                                        // Just display the number as is
                                        formattedMoney = String.valueOf(moneyValue);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000.0 && Long.parseLong(args[2]) < 1_000_000.0) {
                                        // Display in thousands (K)
                                        formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000.0) {
                                        // Display in millions (M)
                                        formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000.0) {
                                        // Display in billions (B)
                                        formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000.0) {
                                        // Display in trillions (T)
                                        formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000.0) {
                                        // Display in quadrillions (Q)
                                        formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000.0) {
                                        // Display in quintillions (QU)
                                        formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    } else if (Long.parseLong(args[2]) >= 1_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000_000.0) {
                                        // Display in sextillions (S)
                                        formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.GREEN + "[+] " + ChatColor.WHITE + formattedMoney + ChatColor.GREEN + " coins");
                                    }
                                    plugin.getMoney().put(target.getUniqueId().toString(), plugin.getMoney().get(target.getUniqueId().toString()) + Long.parseLong(args[2]));
                                }
                                return true;
                            }

                            if (args[0].equalsIgnoreCase("set")) {
                                if (args[1].equalsIgnoreCase("*")) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        long moneyValue = Long.parseLong(args[2]);

                                        String formattedMoney;
                                        if (moneyValue == 0) {
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + 0 + " coins");
                                        }
                                        if (moneyValue == 1) {
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + 1 + " coin");
                                        }
                                        if (moneyValue > 1 && moneyValue < 1_000.0) {
                                            // Just display the number as is
                                            formattedMoney = String.valueOf(moneyValue);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000.0 && moneyValue < 1_000_000.0) {
                                            // Display in thousands (K)
                                            formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
                                            player.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000.0 && moneyValue < 1_000_000_000.0) {
                                            // Display in millions (M)
                                            formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000.0 && moneyValue < 1_000_000_000_000.0) {
                                            // Display in billions (B)
                                            formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000.0) {
                                            // Display in trillions (T)
                                            formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000.0) {
                                            // Display in quadrillions (Q)
                                            formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000.0) {
                                            // Display in quintillions (QU)
                                            formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000_000.0) {
                                            // Display in sextillions (S)
                                            formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), Long.parseLong(args[2]));
                                    }
                                } else {
                                    long moneyValue = plugin.getMoney().get(player.getUniqueId().toString());

                                    String formattedMoney;
                                    if (moneyValue == 0) {
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + 0 + " coins");
                                    }
                                    if (moneyValue == 1) {
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + 1 + " coin");
                                    }
                                    if (moneyValue > 1 && moneyValue < 1_000.0) {
                                        // Just display the number as is
                                        formattedMoney = String.valueOf(moneyValue);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000.0 && moneyValue < 1_000_000.0) {
                                        // Display in thousands (K)
                                        formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000.0 && moneyValue < 1_000_000_000.0) {
                                        // Display in millions (M)
                                        formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000.0 && moneyValue < 1_000_000_000_000.0) {
                                        // Display in billions (B)
                                        formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000.0) {
                                        // Display in trillions (T)
                                        formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000.0) {
                                        // Display in quadrillions (Q)
                                        formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000.0) {
                                        // Display in quintillions (QU)
                                        formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000_000.0) {
                                        // Display in sextillions (S)
                                        formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.YELLOW + "Your balance was set to " + ChatColor.WHITE + formattedMoney + " coins");
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
                                        if (moneyValue == 0 && Long.parseLong(args[2]) == 0) {
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + 0 + ChatColor.RED + " coins");
                                        }
                                        if (moneyValue == 1 && Long.parseLong(args[2]) == 1) {
                                            p.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + 1 + ChatColor.RED + " coin");
                                        }
                                        if (moneyValue > 1 && moneyValue < 1_000.0 && Long.parseLong(args[2]) > 1 && Long.parseLong(args[2]) < 1_000) {
                                            // Just display the number as is
                                            formattedMoney = String.valueOf(moneyValue);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000.0 && moneyValue < 1_000_000.0 && Long.parseLong(args[2]) >= 1_000.0 && Long.parseLong(args[2]) < 1_000_000.0) {
                                            // Display in thousands (K)
                                            formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000.0 && moneyValue < 1_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000.0) {
                                            // Display in millions (M)
                                            formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000.0 && moneyValue < 1_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000.0) {
                                            // Display in billions (B)
                                            formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000.0) {
                                            // Display in trillions (T)
                                            formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000.0) {
                                            // Display in quadrillions (Q)
                                            formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000.0) {
                                            // Display in quintillions (QU)
                                            formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        } else if (moneyValue >= 1_000_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000_000.0) {
                                            // Display in sextillions (S)
                                            formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
                                            p.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                        }
                                        plugin.getMoney().put(p.getUniqueId().toString(), plugin.getMoney().get(p.getUniqueId().toString()) - Long.parseLong(args[2]));

                                    }
                                } else {
                                    long moneyValue = plugin.getMoney().get(target.getUniqueId().toString());

                                    String formattedMoney;
                                    if (moneyValue == 0 && Long.parseLong(args[2]) == 0) {
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + 0 + ChatColor.RED + " coins");
                                    }
                                    if (moneyValue == 1 && Long.parseLong(args[2]) == 1) {
                                        target.sendMessage(ChatColor.RED + "[-] " + ChatColor.WHITE + 1 + ChatColor.RED + " coin");
                                    }
                                    if (moneyValue > 1 && moneyValue < 1_000.0 && Long.parseLong(args[2]) > 1 && Long.parseLong(args[2]) < 1_000) {
                                        // Just display the number as is
                                        formattedMoney = String.valueOf(moneyValue);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000.0 && moneyValue < 1_000_000.0 && Long.parseLong(args[2]) >= 1_000.0 && Long.parseLong(args[2]) < 1_000_000.0) {
                                        // Display in thousands (K)
                                        formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000.0 && moneyValue < 1_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000.0) {
                                        // Display in millions (M)
                                        formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000.0 && moneyValue < 1_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000.0) {
                                        // Display in billions (B)
                                        formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000.0) {
                                        // Display in trillions (T)
                                        formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000.0) {
                                        // Display in quadrillions (Q)
                                        formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000.0) {
                                        // Display in quintillions (QU)
                                        formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    } else if (moneyValue >= 1_000_000_000_000_000_000_000.0 && moneyValue < 1_000_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) >= 1_000_000_000_000_000_000_000.0 && Long.parseLong(args[2]) < 1_000_000_000_000_000_000_000_000.0) {
                                        // Display in sextillions (S)
                                        formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
                                        target.sendMessage(ChatColor.RED +"[-] " + ChatColor.WHITE + formattedMoney + " coins");
                                    }
                                    plugin.getMoney().put(target.getUniqueId().toString(), plugin.getMoney().get(target.getUniqueId().toString()) - Long.parseLong(args[2]));
                                }
                            }
                        }
                        break;
                }
            }

        } else {
            MessageManager.consoleBad("You must be a player to execute this command.");
        }
        return true;
    }
}
