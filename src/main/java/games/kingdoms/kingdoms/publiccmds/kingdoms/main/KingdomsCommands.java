package games.kingdoms.kingdoms.publiccmds.kingdoms.main;

import games.kingdoms.kingdoms.Kingdoms;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.MongoConnect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KingdomsCommands implements CommandExecutor {

    public KingdomsCommands(Kingdoms plugin) {
        this.plugin = plugin;
    }

    private final Kingdoms plugin;
    private MongoConnect mongoConnect;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute this command.");
            return true;
        }
        switch (args.length) {
            case 0:
                player.sendMessage(ChatColor.RED + "—————— " + ChatColor.YELLOW + "SUGGESTED COMMANDS " + ChatColor.RED + "——————");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom create <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom rename <name>");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom transfer <player> <kingdom>");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom disband <kingdom>");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom invite <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom uninvite <player>");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom join <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom spawn");
                player.sendMessage(ChatColor.YELLOW + "/kingdom setspawn");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom leave <kingdom>");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom kick <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom map");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom claim");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom unclaim");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom promote <player>");
//                player.sendMessage(ChatColor.YELLOW + "/kingdom demote <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom rank [command] [args]");
                player.sendMessage(ChatColor.YELLOW + "/kingdom raid [start:end:logs]");
                player.sendMessage(ChatColor.YELLOW + "/kingdom set <attribute> [args]");
                player.sendMessage(ChatColor.YELLOW + "/kingdom info <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom walls <show:hide:upgrade>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom list [page]");
                break;

            case 1:
                Chunk chunk = player.getLocation().getChunk();
                String kingdom = plugin.getKingdoms().get(player.getUniqueId().toString());
                String chunkID = chunk.getX() + "," + chunk.getZ();
                if (args[0].equalsIgnoreCase("claim")) {
                    if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                        if (plugin.getCanClaim().containsKey(player.getUniqueId().toString())) {
                            if (plugin.getCanClaim().containsValue(kingdom)) {
                                if (!plugin.getClaimedChunks().containsKey(chunkID)) {
                                    plugin.getClaimedChunks().put(chunkID, kingdom);
                                    player.sendMessage(ChatColor.GREEN + "Chunk claimed");
                                } else {
                                    player.sendMessage(ChatColor.RED + "Chunk is claimed by " + ChatColor.WHITE + plugin.getClaimedChunks().get(chunkID));
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to claim chunks for " + ChatColor.WHITE + kingdom);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                }
                if (args[0].equalsIgnoreCase("unclaim")) {
                    try {
                        if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                            if (plugin.getCanUnclaim().containsKey(player.getUniqueId().toString())) {
                                if (plugin.getCanUnclaim().containsValue(kingdom)) {
                                    if (plugin.getClaimedChunks().get(chunkID).equals(kingdom)) {
                                        plugin.getClaimedChunks().remove(chunkID, kingdom);
                                        player.sendMessage(ChatColor.GREEN + "Chunk unclaimed");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You are not a member of " + ChatColor.WHITE + plugin.getClaimedChunks().get(chunkID));
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You do not have permission to unclaim chunks for " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                        }
                    } catch (CommandException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 2:
                chunk = player.getLocation().getChunk();
                Player target = Bukkit.getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("create")) {
                    if (!plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                        if (!plugin.getKingdoms().containsValue(args[1])) {
                            plugin.getOwner().put(player.getUniqueId().toString(), args[1]);
                            plugin.getAdmin().put(player.getUniqueId().toString(), args[1]);
                            plugin.getMember().put(player.getUniqueId().toString(), args[1]);
                            plugin.getCanClaim().put(player.getUniqueId().toString(), args[1]);
                            plugin.getCanUnclaim().put(player.getUniqueId().toString(), args[1]);
                            plugin.getKingdoms().put(player.getUniqueId().toString(), args[1]);
                            player.sendMessage(args[1] + ChatColor.GREEN + " created");
                        } else {
                            player.sendMessage(args[1] + ChatColor.RED + " already exists");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are already in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("disband")) {
                    chunkID = chunk.getX() + "," + chunk.getZ();
                    if (plugin.getKingdoms().containsKey(player.getUniqueId().toString()) || player.hasPermission("kingdoms.disband.admin")) {
                        if (plugin.getKingdoms().get(player.getUniqueId().toString()).equals(args[1]) || player.hasPermission("kingdoms.disband.admin")) {
                            if (plugin.getOwner().get(player.getUniqueId().toString()).equals(args[1]) || player.hasPermission("kingdoms.disband.admin")) {
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    plugin.getKingdoms().remove(p.getUniqueId().toString(), args[1]);
                                    plugin.getOwner().remove(p.getUniqueId().toString(), args[1]);
                                    plugin.getAdmin().remove(p.getUniqueId().toString(), args[1]);
                                    plugin.getMember().remove(p.getUniqueId().toString(), args[1]);
                                    plugin.getCanClaim().remove(p.getUniqueId().toString(), args[1]);
                                    plugin.getCanUnclaim().remove(p.getUniqueId().toString(), args[1]);
                                    plugin.getClaimedChunks().remove(chunkID, args[1]);
                                }
                                player.sendMessage(args[1] + ChatColor.GREEN + " disbanded");
                            } else {
                                player.sendMessage(ChatColor.RED + "You are not the owner of " + ChatColor.WHITE + args[1]);
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not a member of " + ChatColor.WHITE + args[1]);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("invite")) {
                    if (!plugin.getKingdoms().containsKey(target.getUniqueId().toString())) {
                        if (plugin.getOwner().containsKey(player.getUniqueId().toString()) || plugin.getAdmin().containsKey(player.getUniqueId().toString())) {
                            if (plugin.getOwner().containsValue(plugin.getKingdoms().get(player.getUniqueId().toString())) || plugin.getAdmin().containsValue(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                if (!plugin.getInviteList().containsKey(target.getUniqueId().toString())) {
                                    if (!plugin.getInviteList().containsValue(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                        plugin.getInviteList().put(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                        player.sendMessage(ChatColor.GREEN + "You invited " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " to " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                                        target.sendMessage(ChatColor.GREEN + "You were invited to " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.GREEN + " by " + ChatColor.WHITE + player.getName());
                                    } else {
                                        player.sendMessage(target.getName() + ChatColor.RED + " has already been invited to " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                                    }
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to invite players to " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                        }
                    } else {
                        player.sendMessage(target.getName() + ChatColor.RED + " is already in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("uninvite")) {
                    if (plugin.getOwner().containsKey(player.getUniqueId().toString()) || plugin.getAdmin().containsKey(player.getUniqueId().toString())) {
                        if (plugin.getOwner().containsValue(plugin.getKingdoms().get(player.getUniqueId().toString())) || plugin.getAdmin().containsValue(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            if (plugin.getInviteList().containsKey(target.getUniqueId().toString())) {
                                if (plugin.getInviteList().containsValue(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                    plugin.getInviteList().remove(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                    player.sendMessage(ChatColor.GREEN + "You uninvited " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " from " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                                    target.sendMessage(ChatColor.RED + "You were uninvited from " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                                }
                            }
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("join")) {
                    if (plugin.getInviteList().containsKey(player.getUniqueId().toString()) && plugin.getInviteList().containsValue(args[1])) {
                        plugin.getInviteList().remove(player.getUniqueId().toString());
                        plugin.getKingdoms().put(player.getUniqueId().toString(), args[1]);
                        plugin.getMember().put(player.getUniqueId().toString(), args[1]);
                        player.sendMessage(ChatColor.GREEN + "You joined " + ChatColor.WHITE + args[1]);
                    } else {
                        player.sendMessage(ChatColor.RED + "You have not been invited to " + ChatColor.WHITE + args[1]);
                    }
                }

                if (args[0].equalsIgnoreCase("leave")) {
                    if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                        if (plugin.getKingdoms().containsValue(args[1])) {
                            if (!plugin.getOwner().containsKey(player.getUniqueId().toString())) {
                                plugin.getKingdoms().remove(player.getUniqueId().toString(), args[1]);
                                plugin.getAdmin().remove(player.getUniqueId().toString(), args[1]);
                                plugin.getMember().remove(player.getUniqueId().toString(), args[1]);
                                plugin.getCanClaim().remove(player.getUniqueId().toString(), args[1]);
                                plugin.getCanUnclaim().remove(player.getUniqueId().toString(), args[1]);
                                player.sendMessage(ChatColor.GREEN + "You left " + ChatColor.WHITE + args[1]);
                            } else {
                                player.sendMessage(ChatColor.RED + "You must transfer " + ChatColor.WHITE + args[1] + ChatColor.RED + " to another member before you can leave");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not a member of " + ChatColor.WHITE + args[1]);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("promote")) {
                    if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                        if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            if (!plugin.getAdmin().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                plugin.getAdmin().put(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                plugin.getMember().remove(target.getUniqueId().toString());
                                player.sendMessage(ChatColor.GREEN + "You promoted " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " to " + ChatColor.WHITE + "admin");
                                target.sendMessage(ChatColor.GREEN + "You were promoted to " + ChatColor.WHITE + "admin" + ChatColor.GREEN + " by " + ChatColor.WHITE + player.getName());
                            } else {
                                player.sendMessage(target.getName() + ChatColor.RED + " is already a admin");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must be the owner of " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.RED + " to demote members");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("demote")) {
                    if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                        if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            if (!plugin.getMember().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                plugin.getAdmin().remove(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                plugin.getMember().put(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                player.sendMessage(ChatColor.GREEN + "You demoted " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " to " + ChatColor.WHITE + "member");
                                target.sendMessage(ChatColor.GREEN + "You were demoted to " + ChatColor.WHITE + "member" + ChatColor.GREEN + " by " + ChatColor.WHITE + player.getName());
                            } else {
                                player.sendMessage(target.getName() + ChatColor.RED + " is already a member of " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You must be the owner of " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.RED + " to demote members");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("kick")) {
                    if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                        if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString())) || plugin.getAdmin().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            plugin.getKingdoms().remove(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                            plugin.getMember().remove(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                            plugin.getAdmin().remove(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                            player.sendMessage(ChatColor.GREEN + "You kicked " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " from " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                            target.sendMessage(ChatColor.RED + "You were kicked from " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.RED + " by " + ChatColor.WHITE + player.getName());
                        } else {
                            player.sendMessage(ChatColor.GREEN + "You do not have permission to kick players from " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                        }
                    } else {
                        player.sendMessage("You are not in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("transfer")) {
                    if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                        if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            plugin.getOwner().remove(player.getUniqueId().toString());
                            plugin.getOwner().put(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                            player.sendMessage(ChatColor.GREEN + "You transferred " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.GREEN + " to " + ChatColor.WHITE + target.getName());
                            target.sendMessage(player.getName() + ChatColor.GREEN + " transferred " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.GREEN + " to you");
                        }
                    }
                }
                break;


            case 3:
                break;

        }
        return true;
    }
}

