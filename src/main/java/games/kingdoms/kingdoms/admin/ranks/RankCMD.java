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
                        return true;
                    }
                    break;
                    case 3:
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.WHITE + args[1] + ChatColor.RED + " is not online");
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("default") && player.hasPermission("kingdoms.setrank.default")) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT);
                            plugin.getStaff().remove(target.getUniqueId().toString());
                            plugin.saveStaff();
                            plugin.savePlayerRank();
                            target.setOp(false);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.DARK_GRAY + Rank.DEFAULT);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.DARK_GRAY + Rank.DEFAULT);
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("vip") && player.hasPermission("kingdoms.setrank.vip")) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.GREEN.toString() + ChatColor.BOLD + Rank.VIP);
                            plugin.getStaff().remove(target.getUniqueId().toString());
                            plugin.saveStaff();
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.GREEN + Rank.VIP);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.GREEN + Rank.VIP);
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("hero") && player.hasPermission("kingdoms.setrank.hero")) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.AQUA.toString() + ChatColor.BOLD + Rank.HERO);
                            plugin.getStaff().remove(target.getUniqueId().toString());
                            plugin.saveStaff();
                            plugin.savePlayerRank();
                            target.setOp(false);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.AQUA + Rank.HERO);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.AQUA + Rank.HERO);
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("youtube") && player.hasPermission("kingdoms.setrank.youtube")) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), Rank.YOUTUBE.getColor().toString() + ChatColor.BOLD + Rank.YOUTUBE.getName() + Rank.YOUTUBE.getSecondaryColor() + ChatColor.BOLD + Rank.YOUTUBE.getSecondaryName());
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + Rank.YOUTUBE.getColor() + Rank.YOUTUBE.getName() + Rank.YOUTUBE.getSecondaryColor() + Rank.YOUTUBE.getSecondaryName());
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + Rank.YOUTUBE.getColor() + Rank.YOUTUBE.getName() + Rank.YOUTUBE.getSecondaryColor() + Rank.YOUTUBE.getSecondaryName());
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("jrmod") && player.hasPermission("kingdoms.setrank.jrmod")) {
                        plugin.getStaff().put(target.getUniqueId().toString(), "jrmod");
                        plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + Rank.JRMOD);
                        plugin.savePlayerRank();
                        target.setOp(false);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.DARK_AQUA + Rank.JRMOD);
                        target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.DARK_AQUA + Rank.JRMOD);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("mod") && player.hasPermission("kingdoms.setrank.mod")) {
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.YELLOW.toString() + ChatColor.BOLD + Rank.MOD);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.YELLOW + Rank.MOD);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.YELLOW + Rank.MOD);
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("srmod") && player.hasPermission("kingdom.setrank.srmod")) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "srmod");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.GOLD.toString() + ChatColor.BOLD + Rank.SRMOD);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.GOLD + Rank.SRMOD);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.GOLD + Rank.SRMOD);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("jradmin") && player.hasPermission("kingdoms.setrank.jradmin")) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "jradmin");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.JRADMIN);
                            plugin.savePlayerRank();
                            target.setOp(false);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.DARK_RED + Rank.JRADMIN);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.DARK_RED + Rank.JRADMIN);
                        }
                        if (args[0].equalsIgnoreCase("set") && target != null && args[2].equalsIgnoreCase("admin") && player.hasPermission("kingdoms.setrank.admin")) {
                            plugin.getStaff().put(target.getUniqueId().toString(), "admin");
                            plugin.getPlayerRank().put(target.getUniqueId().toString(), ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.ADMIN);
                            plugin.savePlayerRank();
                            target.setOp(true);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "You set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s rank to " + ChatColor.DARK_RED + Rank.ADMIN);
                            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your rank has been set to " + ChatColor.DARK_RED + Rank.ADMIN);
                        }
                    break;
            }
        }

        return true;
    }
}
