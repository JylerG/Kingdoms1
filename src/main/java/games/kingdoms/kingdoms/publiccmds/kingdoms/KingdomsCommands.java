package games.kingdoms.kingdoms.publiccmds.kingdoms;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KingdomsCommands implements CommandExecutor {

    public KingdomsCommands(Kingdoms plugin) {
        this.plugin = plugin;
    }

    private final Kingdoms plugin;

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
                break;

            case 2:
                String kingdom = args[1];
                if (args[0].equalsIgnoreCase("disband") && ((plugin.getOwner().containsKey(player.getUniqueId().toString()) && plugin.getOwner().containsValue(kingdom)) || player.hasPermission("kingdoms.disband.admin"))) {
                    //player is the kingdom owner or an admin with permission to disband the kingdom
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (plugin.getKingdoms().containsKey(p.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom)) {
                            plugin.getKingdoms().remove(p.getUniqueId().toString(), kingdom);
                            plugin.getOwner().remove(p.getUniqueId().toString(), kingdom);
                            plugin.getAdmin().remove(p.getUniqueId().toString(),kingdom);
                            plugin.getMember().remove(p.getUniqueId().toString(), kingdom);
                            player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + kingdom + ChatColor.RED + ChatColor.BOLD + " was disbanded");
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("disband") && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom) && !plugin.getOwner().containsKey(player.getUniqueId().toString())) {
                    //player is in kingdom, but can't disband it
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You don't have permission to disband " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("disband") && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && !plugin.getKingdoms().containsValue(kingdom)) {
                    //Player is not in the specified kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are not a member of " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("disband") && !plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are not in a kingdom");
                    return true;
                }
                if (args[0].equalsIgnoreCase("create") && plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                    //player is already in a kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are already in a kingdom");
                    return true;
                }
                if (args[0].equalsIgnoreCase("create") && !plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && !plugin.getKingdoms().containsValue(kingdom)) {
                    //player is not in a kingdom and kingdom doesn't exist
                    plugin.getKingdoms().put(player.getUniqueId().toString(), kingdom);
                    plugin.getOwner().put(player.getUniqueId().toString(), kingdom);
                    plugin.getAdmin().put(player.getUniqueId().toString(), kingdom);
                    plugin.getMember().put(player.getUniqueId().toString(), kingdom);
                    player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + kingdom + ChatColor.GREEN + ChatColor.BOLD + " created");
                    return true;
                }

                if (args[0].equalsIgnoreCase("create") && !plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom)) {
                    //player is not in a kingdom, but kingdom already exists
                    player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + kingdom + ChatColor.RED + ChatColor.BOLD + " already exists");
                    return true;
                }

                if (args[0].equalsIgnoreCase("join") && !plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getJoinList().containsKey(player.getUniqueId().toString()) && plugin.getJoinList().containsValue(kingdom)) {
                    //player is not in a kingdom, and joins the specified kingdom
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You joined " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("join") && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getJoinList().containsKey(player.getUniqueId().toString())) {
                    //player wants to join a kingdom, but they are already a member of a kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You must leave " + ChatColor.WHITE + ChatColor.BOLD + plugin.getKingdoms().get(player.getUniqueId().toString()) + ChatColor.RED + ChatColor.BOLD + " before you can join " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("join") && !plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && !plugin.getJoinList().containsKey(player.getUniqueId().toString())) {
                    //player has not been invited to the specified kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You have not been invited to " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                break;

            case 3:
                kingdom = args[2];
                Player target = Bukkit.getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("transfer") && player.hasPermission(kingdom + ".transfer") && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom) && plugin.getOwner().containsKey(player.getUniqueId().toString()) && plugin.getOwner().containsValue(kingdom)) {
                    //player is the owner of the kingdom and is transferring it to a specified player
                    plugin.getOwner().remove(player.getUniqueId().toString(), kingdom);
                    plugin.getOwner().put(target.getUniqueId().toString(), kingdom);
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You transferred " + ChatColor.WHITE + ChatColor.BOLD + kingdom + ChatColor.GREEN + ChatColor.BOLD + " to " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName());
                    target.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + player.getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " transferred " + ChatColor.WHITE + ChatColor.BOLD + kingdom + ChatColor.GREEN + ChatColor.BOLD + " to you");
                    return true;
                }
                if (args[0].equalsIgnoreCase("transfer") && !player.hasPermission(kingdom + ".transfer") && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom)) {
                    //player is a member of the kingdom, but doesn't have permission to transfer the kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You do not have permission to transfer " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("transfer") && !plugin.getKingdoms().containsValue(kingdom)) {
                    //kingdom doesn't exist
                    player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + kingdom + ChatColor.RED + ChatColor.BOLD + " doesn't exist");
                    return true;
                }
                if (args[0].equalsIgnoreCase("transfer") && !plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && !plugin.getKingdoms().containsValue(kingdom)) {
                    //player is not a member of the specified kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are not a member of " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("invite") && (plugin.getOwner().containsKey(player.getUniqueId().toString()) && plugin.getOwner().containsValue(kingdom) || plugin.getAdmin().containsKey(player.getUniqueId().toString()) && plugin.getAdmin().containsValue(kingdom)) && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom)) {
                    //player has been invited to a specified kingdom
                    plugin.getInviteList().put(player.getUniqueId().toString(), kingdom);
                    player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You invited " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.GREEN + ChatColor.BOLD + " to " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    target.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You were invited to " + ChatColor.WHITE + ChatColor.BOLD + kingdom + ChatColor.GREEN + ChatColor.BOLD + " by " + ChatColor.WHITE + ChatColor.BOLD + player.getDisplayName());
                    return true;
                }
                if (args[0].equalsIgnoreCase("invite") && plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && plugin.getKingdoms().containsValue(kingdom) && (!plugin.getOwner().containsKey(player.getUniqueId().toString()) || !plugin.getAdmin().containsKey(player.getUniqueId().toString()))) {
                    //player is a member of the specified kingdom, but doesn't have permission to invite players
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You do not have permission to invite people to " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                if (args[0].equalsIgnoreCase("invite") && (plugin.getKingdoms().containsKey(player.getUniqueId().toString()) && !plugin.getKingdoms().containsValue(kingdom)) || (!plugin.getKingdoms().containsKey(player.getUniqueId().toString()))) {
                    //player is not a member of the specified kingdom
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You are not a member of " + ChatColor.WHITE + ChatColor.BOLD + kingdom);
                    return true;
                }
                break;
        }
        return true;
    }
}