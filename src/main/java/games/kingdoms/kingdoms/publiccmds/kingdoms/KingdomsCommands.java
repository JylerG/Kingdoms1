package games.kingdoms.kingdoms.publiccmds.kingdoms;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class KingdomsCommands implements CommandExecutor {

    public KingdomsCommands(Kingdoms plugin) {
        this.plugin = plugin;
    }

    private final Kingdoms plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to execute this command.");
            return true;
        }
        Player player = (Player) sender;
        switch (args.length) {
            case 0:
                //TODO: RANK, RAID, SET, INFO, WALLS, LIST, MAP
                player.sendMessage(ChatColor.RED + "—————— " + ChatColor.YELLOW + "SUGGESTED COMMANDS " + ChatColor.RED + "——————");
                player.sendMessage(ChatColor.YELLOW + "/kingdom create <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom rename <name>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom transfer <kingdom> <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom disband <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom invite <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom uninvite <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom join <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom spawn");
                player.sendMessage(ChatColor.YELLOW + "/kingdom setspawn");
                player.sendMessage(ChatColor.YELLOW + "/kingdom leave <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom kick <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom map");
                player.sendMessage(ChatColor.YELLOW + "/kingdom claim");
                player.sendMessage(ChatColor.YELLOW + "/kingdom unclaim");
                player.sendMessage(ChatColor.YELLOW + "/kingdom promote <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom demote <player>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom rank [command] [args]");
                player.sendMessage(ChatColor.YELLOW + "/kingdom raid [start:end:logs]");
                player.sendMessage(ChatColor.YELLOW + "/kingdom set <attribute> [args]");
                player.sendMessage(ChatColor.YELLOW + "/kingdom info <kingdom>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom walls <show:hide:upgrade>");
                player.sendMessage(ChatColor.YELLOW + "/kingdom list [page]");
                break;

            case 1:
                World world = Bukkit.getWorld("kingdoms");
                Chunk chunk = player.getLocation().getChunk();
                String kingdom = plugin.getKingdoms().get(player.getUniqueId().toString());
                String chunkID = chunk.getX() + "," + chunk.getZ();
                Location spawn = new Location(world, player.getLocation().getX(), player.getLocation().getBlockY(), player.getLocation().getZ());
                if (args[0].equalsIgnoreCase("setspawn")) {
                    if (plugin.getAdmin().get(player.getUniqueId().toString()).equals(kingdom) || plugin.getOwner().get(player.getUniqueId().toString()).equals(kingdom)) {
                        plugin.getKingdomSpawn().put(kingdom, spawn);
                        player.sendMessage(ChatColor.GREEN + "You set " + ChatColor.WHITE + kingdom + ChatColor.GREEN + "'s spawn");
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to set " + ChatColor.WHITE + kingdom + ChatColor.RED + "'s spawn");
                        return true;
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("spawn")) {
                    if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                        if (plugin.getKingdomSpawn().containsKey(kingdom)) {
                            player.teleport(plugin.getKingdomSpawn().get(kingdom));
                            player.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.WHITE + kingdom + ChatColor.GREEN + "'s spawn");
                        } else {
                            player.sendMessage(kingdom + ChatColor.RED + "'s spawn has not been set");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("claim")) {
                    if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                        if (plugin.getCanClaim().containsKey(player.getUniqueId().toString())) {
                            if (!plugin.getClaimedChunks().containsKey(chunkID)) {
                                //plugin.getAltClaimedChunks().put(kingdom, chunkID);
                                plugin.getClaimedChunks().put(chunkID, kingdom);
                                player.sendMessage(ChatColor.GREEN + "Chunk claimed");
                            } else {
                                String ownerKingdom = plugin.getClaimedChunks().get(chunkID);
                                if (ownerKingdom.equals(kingdom)) {
                                    player.sendMessage(ChatColor.RED + "Chunk is already claimed by your kingdom");
                                } else {
                                    player.sendMessage(ChatColor.RED + "Chunk is claimed by " + ChatColor.WHITE + ownerKingdom);
                                }
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to claim chunks for " + ChatColor.WHITE + kingdom);
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                        return true;
                    }
                }

                if (args[0].equalsIgnoreCase("unclaim")) {
                    try {
                        if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                            if (plugin.getCanUnclaim().containsKey(player.getUniqueId().toString())) {
                                if (plugin.getCanUnclaim().containsValue(kingdom)) {
                                    if (plugin.getClaimedChunks().get(chunkID).equals(kingdom)) {
                                        plugin.getClaimedChunks().remove(chunkID, kingdom);
                                        //plugin.getAltClaimedChunks().remove(kingdom, chunkID);
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
                    } catch (NullPointerException e) {
                        player.sendMessage(ChatColor.RED + "This chunk is not claimed");
                    }
                }
                break;

            case 2:
                chunk = player.getLocation().getChunk();
                Player target = Bukkit.getPlayer(args[1]);
                world = Bukkit.getWorld("kingdoms");
                chunkID = chunk.getX() + "," + chunk.getZ();
                spawn = new Location(world, player.getLocation().getX(), player.getLocation().getBlockY(), player.getLocation().getZ());

                if (args[0].equalsIgnoreCase("create")) {
                    if (!plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                        if (!plugin.getKingdoms().containsValue(args[1])) {
                            plugin.getKingdoms().put(player.getUniqueId().toString(), args[1]);
                            plugin.getOwner().put(player.getUniqueId().toString(), args[1]);
                            plugin.getAdmin().put(player.getUniqueId().toString(), args[1]);
                            plugin.getMember().put(player.getUniqueId().toString(), args[1]);
                            plugin.getCanClaim().put(player.getUniqueId().toString(), args[1]);
                            plugin.getCanUnclaim().put(player.getUniqueId().toString(), args[1]);
                            player.sendMessage(args[1] + ChatColor.GREEN + " created");

                        } else {
                            player.sendMessage(args[1] + ChatColor.RED + " already exists");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are already in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("disband")) {
                    if (!player.hasPermission("kingdoms.disband.admin")) {
                        if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                            if (plugin.getKingdoms().get(player.getUniqueId().toString()).equals(args[1])) {
                                if (plugin.getOwner().get(player.getUniqueId().toString()).equals(args[1])) {
                                    if (plugin.getKingdoms().containsValue(args[1])) {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            plugin.getKingdoms().remove(p.getUniqueId().toString(), args[1]);
                                            plugin.getOwner().remove(p.getUniqueId().toString(), args[1]);
                                            plugin.getAdmin().remove(p.getUniqueId().toString(), args[1]);
                                            plugin.getMember().remove(p.getUniqueId().toString(), args[1]);
                                            plugin.getCanClaim().remove(p.getUniqueId().toString(), args[1]);
                                            plugin.getCanUnclaim().remove(p.getUniqueId().toString(), args[1]);
                                            for (Map.Entry<String, String> claims : plugin.getClaimedChunks().entrySet()) {
                                                plugin.getClaimedChunks().remove(claims.getKey(), args[1]);
                                            }
                                            plugin.getKingdomSpawn().remove(args[1], spawn);
                                        }
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
                    } else {
                        if (plugin.getKingdoms().containsValue(args[1])) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                plugin.getKingdoms().remove(p.getUniqueId().toString(), args[1]);
                                plugin.getOwner().remove(p.getUniqueId().toString(), args[1]);
                                plugin.getAdmin().remove(p.getUniqueId().toString(), args[1]);
                                plugin.getMember().remove(p.getUniqueId().toString(), args[1]);
                                plugin.getCanClaim().remove(p.getUniqueId().toString(), args[1]);
                                plugin.getCanUnclaim().remove(p.getUniqueId().toString(), args[1]);
                                for (Map.Entry<String, String> claims : plugin.getClaimedChunks().entrySet()) {
                                    plugin.getClaimedChunks().remove(claims.getKey(), args[1]);
                                }
                                plugin.getKingdomSpawn().remove(args[1], spawn);
                                player.sendMessage(args[1] + ChatColor.GREEN + " disbanded");
                            }
                        } else {
                            player.sendMessage(args[1] + ChatColor.RED + " doesn't exist");
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("invite")) {
                    if (target == null) {
                        player.sendMessage(args[1] + ChatColor.RED + " is not online");
                    } else {
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
                                            return true;
                                        }
                                    }
                                } else {
                                    player.sendMessage("You are not an owner or admin of " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                                }
                            }
                        } else {
                            player.sendMessage(target.getName() + ChatColor.RED + " is already in a kingdom");
                            return true;
                        }
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
                                    return true;
                                } else {
                                    player.sendMessage(target.getName() + ChatColor.RED + " hasn't been invited to " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not an owner or admin of " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()));
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
                    if (target != null) {
                        if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                if (plugin.getMember().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                    plugin.getAdmin().put(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                    plugin.getMember().remove(target.getUniqueId().toString());
                                    player.sendMessage(ChatColor.GREEN + "You promoted " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " to " + ChatColor.WHITE + "admin");
                                    target.sendMessage(ChatColor.GREEN + "You were promoted to " + ChatColor.WHITE + "admin" + ChatColor.GREEN + " by " + ChatColor.WHITE + player.getName());
                                } else {
                                    player.sendMessage(target.getName() + ChatColor.RED + " is already a admin");
                                    return true;
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You must be the owner of " + ChatColor.WHITE + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.RED + " to demote members");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                            return true;
                        }
                    } else {
                        player.sendMessage(args[1] + ChatColor.RED + " is not online");
                    }
                }

                if (args[0].equalsIgnoreCase("demote")) {
                    if (target != null) {
                        kingdom = plugin.getKingdoms().get(player.getUniqueId().toString());
                        if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                            if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                if (plugin.getAdmin().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(player.getUniqueId().toString()))) {
                                    plugin.getAdmin().remove(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                    plugin.getMember().put(target.getUniqueId().toString(), plugin.getKingdoms().get(player.getUniqueId().toString()));
                                    player.sendMessage(ChatColor.GREEN + "You demoted " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " to " + ChatColor.WHITE + "member");
                                    target.sendMessage(ChatColor.GREEN + "You were demoted to " + ChatColor.WHITE + "member" + ChatColor.GREEN + " by " + ChatColor.WHITE + player.getName());
                                } else {
                                    player.sendMessage(target.getName() + ChatColor.RED + " is already a " + ChatColor.WHITE + "member");
                                    return true;
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You must be the owner of " + ChatColor.WHITE + kingdom + ChatColor.RED + " to demote members");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                            return true;
                        }
                    } else {
                        player.sendMessage(args[1] + ChatColor.RED + " is not online");
                    }
                }

                if (args[0].equalsIgnoreCase("kick")) {
                    String commandSender = player.getUniqueId().toString();
                    String commandTarget = target.getUniqueId().toString();
                    if (plugin.getKingdoms().get(commandTarget).equals(plugin.getKingdoms().get(commandTarget))) {
                        if (plugin.getOwner().get(commandSender).equals(plugin.getKingdoms().get(commandSender)) || plugin.getAdmin().get(commandSender).equals(plugin.getKingdoms().get(commandSender))) {
                            plugin.getKingdoms().remove(commandTarget, plugin.getKingdoms().get(commandSender));
                            plugin.getMember().remove(commandTarget, plugin.getKingdoms().get(commandSender));
                            plugin.getAdmin().remove(commandTarget, plugin.getKingdoms().get(commandSender));
                            player.sendMessage(ChatColor.GREEN + "You kicked " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " from " + ChatColor.WHITE + plugin.getKingdoms().get(commandSender));
                            target.sendMessage(ChatColor.RED + "You were kicked from " + ChatColor.WHITE + plugin.getKingdoms().get(commandSender));
                        } else {
                            player.sendMessage(ChatColor.GREEN + "You do not have permission to kick players from " + ChatColor.WHITE + plugin.getKingdoms().get(commandSender));
                        }
                    } else {
                        player.sendMessage("You are not in a kingdom");
                    }
                }

                if (args[0].equalsIgnoreCase("rename")) {
                    if (player.hasPermission("kingdoms.rename")) {
                        String commandSender = player.getUniqueId().toString();
                        if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                            if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(commandSender))) {
                                if (!plugin.getKingdoms().containsValue(args[1])) {
                                    player.sendMessage(ChatColor.GREEN + "You renamed " + ChatColor.WHITE + plugin.getKingdoms().get(commandSender) + ChatColor.GREEN + " to " + ChatColor.WHITE + args[1]);
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        for (Map.Entry<String, String> playerName : plugin.getOwner().entrySet()) {
                                            if (playerName.getKey().equals(p.getUniqueId().toString())) {
                                                if (playerName.getValue().equalsIgnoreCase(plugin.getKingdoms().get(commandSender))) {
                                                    plugin.getOwner().put(commandSender, args[1]);
                                                }
                                            }
                                        }
                                        for (Map.Entry<String, String> playerName : plugin.getAdmin().entrySet()) {
                                            if (playerName.getKey().equals(p.getUniqueId().toString())) {
                                                if (playerName.getValue().equalsIgnoreCase(plugin.getKingdoms().get(commandSender))) {
                                                    plugin.getAdmin().put(playerName.getKey(), args[1]);
                                                }
                                            }
                                        }
                                        for (Map.Entry<String, String> playerName : plugin.getMember().entrySet()) {
                                            if (playerName.getKey().equals(p.getUniqueId().toString())) {
                                                if (playerName.getValue().equalsIgnoreCase(plugin.getKingdoms().get(commandSender))) {
                                                    plugin.getMember().put(playerName.getKey(), args[1]);
                                                }
                                            }
                                        }
                                        for (Map.Entry<String, String> playerName : plugin.getCanClaim().entrySet()) {
                                            if (playerName.getKey().equals(p.getUniqueId().toString())) {
                                                if (playerName.getValue().equalsIgnoreCase(plugin.getKingdoms().get(commandSender))) {
                                                    plugin.getCanClaim().put(playerName.getKey(), args[1]);
                                                }
                                            }
                                        }
                                        for (Map.Entry<String, String> playerName : plugin.getCanUnclaim().entrySet()) {
                                            if (playerName.getKey().equals(p.getUniqueId().toString())) {
                                                if (playerName.getValue().equalsIgnoreCase(plugin.getKingdoms().get(commandSender))) {
                                                    plugin.getCanUnclaim().put(playerName.getKey(), args[1]);
                                                }
                                            }
                                        }
                                        for (Map.Entry<String, String> playerName : plugin.getKingdoms().entrySet()) {
                                            if (playerName.getKey().equals(p.getUniqueId().toString())) {
                                                if (playerName.getValue().equalsIgnoreCase(plugin.getKingdoms().get(commandSender))) {
                                                    plugin.getKingdoms().put(playerName.getKey(), args[1]);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(args[1] + ChatColor.RED + " already exists");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not the owner of " + ChatColor.WHITE + plugin.getKingdoms().get(commandSender));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a kingdom");
                    }
                    return true;
                }
                break;

            case 3:
                target = Bukkit.getPlayer(args[2]);
                String commandSender = player.getUniqueId().toString();
                String commandTarget = target.getUniqueId().toString();
                if (args[0].equalsIgnoreCase("transfer")) {
                    if (plugin.getKingdoms().get(target.getUniqueId().toString()).equals(plugin.getKingdoms().get(commandSender))) {
                        if (plugin.getOwner().get(player.getUniqueId().toString()).equals(plugin.getKingdoms().get(commandSender))) {
                            plugin.getOwner().remove(commandSender);
                            plugin.getOwner().put(commandTarget, args[1]);
                            player.sendMessage(ChatColor.GREEN + "You transferred " + ChatColor.WHITE + args[1] + ChatColor.GREEN + " to " + ChatColor.WHITE + target.getName());
                            target.sendMessage(player.getName() + ChatColor.GREEN + " transferred " + ChatColor.WHITE + args[1] + ChatColor.GREEN + " to you");
                        }
                    }
                }
                break;

        }
        return true;
    }
}