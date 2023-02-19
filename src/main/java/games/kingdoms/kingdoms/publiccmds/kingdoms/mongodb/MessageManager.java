package games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void consoleGood(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + s);
    }
    public static void consoleBad(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + s);
    }
    public static void consoleInfo(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + s);
    }

    public static void playerGood(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }
    public static void playerBad(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
    }
    public static void playerInfo(Player player, String message) {
        player.sendMessage(ChatColor.YELLOW + message);
    }
}
