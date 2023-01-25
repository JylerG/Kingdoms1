package games.kingdoms.kingdoms;

import com.github.sanctum.labyrinth.data.FileList;
import games.kingdoms.kingdoms.admin.ranks.Rank;
import games.kingdoms.kingdoms.admin.ranks.RankCMD;
import games.kingdoms.kingdoms.admin.staffvault.StaffVault;
import games.kingdoms.kingdoms.admin.vanish.commands.VanishCMD;
import games.kingdoms.kingdoms.admin.vanish.events.JoinEvent;
import games.kingdoms.kingdoms.publiccmds.kingdoms.KingdomsCommands;
import games.kingdoms.kingdoms.publiccmds.nightvision.Commands;
import games.kingdoms.kingdoms.publiccmds.randomtp.TeleportUtils;
import games.kingdoms.kingdoms.publiccmds.randomtp.rtp;
import games.kingdoms.kingdoms.publiccmds.teleports.WarzoneCMD;
import games.kingdoms.kingdoms.rankedcmds.feed.Feed;
import games.kingdoms.kingdoms.rankedcmds.fly.Fly;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Getter
public final class Kingdoms extends JavaPlugin implements Listener {

    private FileList files;
    private Rank rank;
    private Map<String, String> playerRank = new HashMap<>();
    private Map<String, String> owner = new HashMap<>();
    private Map<String, String> admin = new HashMap<>();
    private Map<String, String> member = new HashMap<>();
    private Map<String, String> joinList = new HashMap<>();
    private Map<String, String> inviteList = new HashMap<>();
    private Map<String, String> staff = new HashMap<>();
    private KingdomsCommands kingdomsCommands;
    private Map<String, String> kingdoms = new HashMap<>();
    public ArrayList<Player> invisiblePlayers = new ArrayList<>();

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {

        //Vault Economy
//        if (!setupEconomy() ) {
//            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }

        //Kingdoms
        this.playerRank = new HashMap<>();
        this.kingdoms = new HashMap<>();
        this.member = new HashMap<>();
        this.owner = new HashMap<>();
        this.admin = new HashMap<>();
        this.staff = new HashMap<>();
        this.inviteList = new HashMap<>();
        this.joinList = new HashMap<>();
        files = FileList.search(this);
        getCommand("kingdom").setExecutor(new KingdomsCommands(this));
        this.getServer().getPluginManager().registerEvents(this, this);
        if (this.getConfig().contains("data")) {
            restoreKingdomData();
        }
        if (this.getConfig().contains("rank")) {
            restorePlayerRank();
        }
        if (this.getConfig().contains("owner")) {
            restoreKingdomOwner();
        }
        if (this.getConfig().contains("admin")) {
            restoreKingdomAdmins();
        }
        if (this.getConfig().contains("member")) {
            restoreKingdomMembers();
        }
        if (this.getConfig().contains("staff")) {
            restoreStaff();
        }



        //Commands
        setRank();
        nightVision();
        staffVault();
        Feed();
        Fly();
        warZone();
        Rtp();
        Vanish();


        //Scoreboard
        nonPvpBoard();

        //Events
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        System.out.println("Kingdoms successfully enabled");

    }

    @Override
    public void onDisable() {
        if (!kingdoms.isEmpty()) {
            saveKingdomData();
        }
        if (!playerRank.isEmpty()) {
            savePlayerRank();
        }
        if (!owner.isEmpty()) {
            saveKingdomOwner();
        }
        if (!admin.isEmpty()) {
            saveKingdomAdmins();
        }
        if (!member.isEmpty()) {
            saveKingdomMembers();
        }
        if (!staff.isEmpty()) {
            saveStaff();
        }

//        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        System.out.println("Kingdoms successfully disabled");
    }

    public void saveKingdomData() {
        for (Map.Entry<String, String> kingdoms : kingdoms.entrySet()) {
            this.getConfig().set("data." + kingdoms.getKey(), kingdoms.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomData() {
        this.getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
            String kingdom = (String) this.getConfig().get("data." + key);
            kingdoms.put(key, kingdom);
        });
    }

    public void saveKingdomOwner() {
        for (Map.Entry<String, String> owner : owner.entrySet()) {
            this.getConfig().set("owner." + owner.getKey(), owner.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomOwner() {
        this.getConfig().getConfigurationSection("owner").getKeys(false).forEach(key -> {
            String kingdom = (String) this.getConfig().get("owner." + key);
            kingdoms.put(key, kingdom);
        });
    }
    public void saveKingdomAdmins() {
        for (Map.Entry<String, String> admins : admin.entrySet()) {
            this.getConfig().set("admin." + admins.getKey(), admins.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomAdmins() {
        this.getConfig().getConfigurationSection("admin").getKeys(false).forEach(key -> {
            String admin = (String) this.getConfig().get("admin." + key);
            kingdoms.put(key, admin);
        });
    }
    public void saveKingdomMembers() {
        for (Map.Entry<String, String> members : member.entrySet()) {
            this.getConfig().set("members." + members.getKey(), members.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomMembers() {
        this.getConfig().getConfigurationSection("members").getKeys(false).forEach(key -> {
            String members = (String) this.getConfig().get("members." + key);
            kingdoms.put(key, members);
        });
    }

    public void savePlayerRank() {
        for (Map.Entry<String, String> ranks : playerRank.entrySet()) {
            this.getConfig().set("rank." + ranks.getKey(), ranks.getValue());
        }
        this.saveConfig();
    }

    public void restorePlayerRank() {
        this.getConfig().getConfigurationSection("rank").getKeys(false).forEach(key -> {
            String rank = (String) this.getConfig().get("rank." + key);
            playerRank.put(key, rank);
        });
    }

    public void saveStaff() {
        for (Map.Entry<String, String> staff : staff.entrySet()) {
            this.getConfig().set("staff." + staff.getKey(), staff.getValue());
        }
        this.saveConfig();
    }

    public void restoreStaff() {
        this.getConfig().getConfigurationSection("staff").getKeys(false).forEach(key -> {
            String staff = (String) this.getConfig().get("staff." + key);
            this.staff.put(key, staff);
        });
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    //Scoreboard
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPlayedBefore()) {
            playerRank.put(p.getUniqueId().toString(), Rank.DEFAULT.getColor().toString() + Rank.DEFAULT.getName());
        } else {
            restorePlayerRank();
            restoreKingdomOwner();
            restoreKingdomAdmins();
            restoreKingdomMembers();
        }
        nonPvPBoard(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        savePlayerRank();
        saveKingdomOwner();
        saveKingdomAdmins();
        saveKingdomMembers();
    }

    @EventHandler
    public void onCommand(PlayerCommandSendEvent event) {
        nonPvPBoard(event.getPlayer());

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        nonPvPBoard(event.getEntity());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        nonPvPBoard(event.getPlayer());
    }

    private void nonPvPBoard(Player player) {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective obj = board.registerNewObjective("NonPvP", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lKingdoms"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score divider = obj.getScore("-----------------------");
        divider.setScore(14);
        Score p = obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "PLAYER");
        p.setScore(13);
        Score rank = obj.getScore("Rank [" + playerRank.get(player.getUniqueId().toString()) + ChatColor.WHITE + "]");
        rank.setScore(12);
        Score kingdom = obj.getScore("Kingdom [" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "]");
        kingdom.setScore(11);
        //edit this to show the user's coins
        Score coins = obj.getScore("Coins " +  ChatColor.GOLD + "WIP");
        coins.setScore(10);
        Score kills = obj.getScore("Kills " + ChatColor.YELLOW + player.getStatistic(Statistic.PLAYER_KILLS));
        kills.setScore(9);
        Score deaths = obj.getScore(ChatColor.WHITE + "Deaths " + ChatColor.YELLOW + player.getStatistic(Statistic.DEATHS));
        deaths.setScore(8);
        //edit this to show how many challenges have been completed
        Score challenges = obj.getScore(ChatColor.WHITE + "Challenges " + ChatColor.YELLOW + "WIP");
        challenges.setScore(7);
        Score blank = obj.getScore(" ");
        blank.setScore(6);
        Score server = obj.getScore(ChatColor.YELLOW.toString() + ChatColor.BOLD + "SERVER");
        server.setScore(5);
        Score online = obj.getScore("Online " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size());
        online.setScore(4);
        //edit this to show online Staff
        Score online_staff = obj.getScore("Staff " + ChatColor.YELLOW + staff.size());
        online_staff.setScore(3);
        Score PvP_setting = obj.getScore(ChatColor.DARK_RED + "PvP " + ChatColor.GRAY + "[" + ChatColor.RED + "OFF" + ChatColor.GRAY + "]");
        PvP_setting.setScore(2);
        Score Separator = obj.getScore(ChatColor.WHITE + "-----------------------");
        Separator.setScore(1);
        Score server_ip = obj.getScore(ChatColor.GREEN + "play.kingdoms.games");
        server_ip.setScore(0);
        player.setScoreboard(board);
    }

    private void nightVision() {
        getCommand("nv").setExecutor(new Commands(this));
    }

    private void setRank() {
        getCommand("rank").setExecutor(new RankCMD(this));
    }

    private void staffVault() {
        getCommand("staffvault").setExecutor(new StaffVault());
    }

    private void Feed() {
        getCommand("feed").setExecutor(new Feed());
    }

    private void Fly() {
        getCommand("fly").setExecutor(new Fly(this));
    }
    private void warZone() {
        getCommand("warzone").setExecutor(new WarzoneCMD());
    }

    private void Vanish() {
        getCommand("vanish").setExecutor(new VanishCMD(this));
    }

    private void Rtp() {
        getCommand("rtp").setExecutor(new rtp());
        TeleportUtils utils = new TeleportUtils(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public void nonPvpBoard() {
        this.getServer().getPluginManager().registerEvents(this, this);

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                nonPvPBoard(online);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        String eventMessage = event.getMessage();
        Player eventPlayer = event.getPlayer();

        String format = ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(eventPlayer.getUniqueId().toString()) + ChatColor.WHITE + "] " + playerRank.get(eventPlayer.getUniqueId().toString()) + " " + ChatColor.WHITE + eventPlayer.getDisplayName() + ": " + eventMessage;
        event.setFormat(format);
        event.setMessage(eventMessage);
    }

    public FileList getFileList() {
        return files;
    }

    public Map<String, String> getPlayerRank() {
        return playerRank;
    }

//    public static Economy getEconomy() {
//        return econ;
//    }
//
//    public static Permission getPermissions() {
//        return perms;
//    }
//
//    public static Chat getChat() {
//        return chat;
//    }
    public Map<String, String> getOwner() {
        return owner;
    }

    public Map<String, String> getAdmin() {
        return admin;
    }

    public Map<String, String> getMember() {
        return member;
    }

    public Map<String, String> getJoinList() {
        return joinList;
    }

    public Map<String, String> getStaff() {
        return staff;
    }

    public Map<String, String> getInviteList() {
        return inviteList;
    }

    public Map<String, String> getKingdoms() {
        return kingdoms;
    }
}