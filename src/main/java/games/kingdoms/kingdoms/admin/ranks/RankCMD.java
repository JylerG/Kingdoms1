package games.kingdoms.kingdoms.admin.ranks;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankCMD implements CommandExecutor {

    private final Kingdoms plugin;
    private Rank rank;

    public RankCMD(Kingdoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            switch (args.length) {
                case 0:
                case 1:
                case 2:
                    if (player.hasPermission("kingdoms.setrank.default") || player.hasPermission("kingdoms.setrank.vip") || player.hasPermission("kingdoms.setrank.hero") || player.hasPermission("kingdoms.setrank.jrmod") || player.hasPermission("kingdoms.setrank.mod") || player.hasPermission("kingdoms.setrank.srmod") || player.hasPermission("kingdoms.setrank.jradmin") || player.isOp()) {
                        player.sendMessage(ChatColor.GOLD + "Usage: /rank set <player> <rank>");
                    }
                    break;
                    case 3:
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("default") && player.hasPermission("kingdoms.setrank.default") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT) || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "JRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.YELLOW.toString() + ChatColor.BOLD + "MOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "SRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.DARK_GRAY + ChatColor.BOLD + Rank.DEFAULT);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.DARK_GRAY + ChatColor.BOLD + Rank.DEFAULT);
                        } if (target == null) {
                        player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + args[1] + ChatColor.RED + ChatColor.BOLD + " is not online");
                    }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("vip") && player.hasPermission("kingdoms.setrank.vip") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GREEN.toString() + ChatColor.BOLD + "VIP") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.YELLOW.toString() + ChatColor.BOLD + "MOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "SRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.GREEN.toString() + ChatColor.BOLD + Rank.VIP);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.GREEN + ChatColor.BOLD + Rank.VIP);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.GREEN + ChatColor.BOLD + Rank.VIP);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("hero") && player.hasPermission("kingdoms.setrank.hero") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.AQUA.toString() + ChatColor.BOLD + "HERO") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.YELLOW.toString() + ChatColor.BOLD + "MOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "SRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.AQUA.toString() + ChatColor.BOLD + Rank.HERO);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.AQUA + ChatColor.BOLD + Rank.HERO);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.AQUA + ChatColor.BOLD + Rank.HERO);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("jrmod") && player.hasPermission("kingdoms.setrank.jrmod") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "JRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.YELLOW.toString() + ChatColor.BOLD + "MOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "SRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "jrmod");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + Rank.JRMOD);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.DARK_AQUA + ChatColor.BOLD + Rank.JRMOD);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.DARK_AQUA + ChatColor.BOLD + Rank.JRMOD);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("mod") && player.hasPermission("kingdoms.setrank.mod") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.YELLOW.toString() + ChatColor.BOLD + "MOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "SRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "mod");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.YELLOW.toString() + ChatColor.BOLD + Rank.MOD);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.YELLOW + ChatColor.BOLD + Rank.MOD);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.YELLOW + ChatColor.BOLD + Rank.MOD);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("srmod") && player.hasPermission("kingdom.setrank.srmod") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "SRMOD") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "srmod");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.GOLD.toString() + ChatColor.BOLD + Rank.SRMOD);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.GOLD + ChatColor.BOLD + Rank.SRMOD);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.GOLD + ChatColor.BOLD + Rank.SRMOD);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("jradmin") && player.hasPermission("kingdoms.setrank.jradmin") && (!plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN") || !plugin.getPlayerRank().get(target.getUniqueId().toString()).equalsIgnoreCase(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "JRADMIN"))) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "jradmin");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.JRADMIN);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.DARK_RED + ChatColor.BOLD + Rank.JRADMIN);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.DARK_RED + ChatColor.BOLD + Rank.JRADMIN);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("admin") && player.hasPermission("kingdoms.setrank.admin")) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "admin");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.ADMIN);
                            plugin.savePlayerRank();
                            target.setOp(true);
                            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "You set " + ChatColor.WHITE + ChatColor.BOLD + target.getDisplayName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "'s rank to " + ChatColor.DARK_RED + ChatColor.BOLD + Rank.ADMIN);
                            target.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Your rank has been set to " + ChatColor.DARK_RED + ChatColor.BOLD + Rank.ADMIN);
                        }
                    break;
            }
        }

        return true;
    }
}
