package games.kingdoms.kingdoms;

import com.github.sanctum.labyrinth.data.FileList;
import games.kingdoms.kingdoms.admin.customitems.CustomItems;
import games.kingdoms.kingdoms.admin.gamemodes.Adventure;
import games.kingdoms.kingdoms.admin.gamemodes.Creative;
import games.kingdoms.kingdoms.admin.gamemodes.Spectator;
import games.kingdoms.kingdoms.admin.gamemodes.Survival;
import games.kingdoms.kingdoms.admin.ranks.Rank;
import games.kingdoms.kingdoms.admin.ranks.RankCMD;
import games.kingdoms.kingdoms.admin.staffvault.StaffVault;
import games.kingdoms.kingdoms.admin.vanish.commands.VanishCMD;
import games.kingdoms.kingdoms.admin.vanish.events.JoinEvent;
import games.kingdoms.kingdoms.publiccmds.kingdoms.main.KingdomsCommands;
import games.kingdoms.kingdoms.publiccmds.kingdoms.main.KingdomsListener;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.MessageManager;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.MongoConnect;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.economy.EconomyCore;
import games.kingdoms.kingdoms.publiccmds.kingdoms.mongodb.economy.PlayerManager;
import games.kingdoms.kingdoms.publiccmds.nightvision.Commands;
import games.kingdoms.kingdoms.publiccmds.randomtp.TeleportUtils;
import games.kingdoms.kingdoms.publiccmds.randomtp.rtp;
import games.kingdoms.kingdoms.publiccmds.teleports.WarzoneCMD;
import games.kingdoms.kingdoms.rankedcmds.feed.Feed;
import games.kingdoms.kingdoms.rankedcmds.fly.Fly;
import games.kingdoms.kingdoms.rankedcmds.nickname.Nickname;
import net.milkbowl.vault.economy.Economy;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
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
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public final class Kingdoms extends JavaPlugin implements Listener {

    private HashMap<String, String> canUnclaim = new HashMap<>();
    private HashMap<String, String> canClaim = new HashMap<>();
    private FileList files;
    private Rank rank;
    private Map<String, String> playerRank = new HashMap<>();
    private Map<String, String> owner = new HashMap<>();
    private Map<String, String> admin = new HashMap<>();
    private Map<String, String> member = new HashMap<>();
    private Map<String, String> inviteList = new HashMap<>();
    private Map<String, String> claimedChunks = new HashMap<>();
    private Map<String, String> staff = new HashMap<>();
    private KingdomsCommands kingdomsCommands;
    private Map<String, String> kingdoms = new HashMap<>();

    private final ArrayList<Player> invisiblePlayers = new ArrayList<>();

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final Economy econ = null;
    public MongoConnect mongoConnect;
    public EconomyCore economyCore;

    private HashMap<UUID, PlayerManager> playerManagerHashMap = new HashMap<>();

    @Override
    public void onEnable() {

        //Kingdoms
        this.playerRank = new HashMap<>();
        this.kingdoms = new HashMap<>();
        this.member = new HashMap<>();
        this.owner = new HashMap<>();
        this.admin = new HashMap<>();
        this.staff = new HashMap<>();
        this.inviteList = new HashMap<>();
        this.claimedChunks = new HashMap<>();
        this.canUnclaim = new HashMap<>();
        this.canClaim = new HashMap<>();
        this.playerManagerHashMap = new HashMap<>();
        files = FileList.search(this);
        mongoConnect = new MongoConnect();
        mongoConnect.playerDataConnect();
        getCommand("kingdom").setExecutor(new KingdomsCommands(this));
        getCommand("nick").setExecutor(new Nickname(this));
        getCommand("merchant").setExecutor(new CustomItems(this));
        getCommand("gmc").setExecutor(new Creative(this));
        getCommand("gms").setExecutor(new Survival(this));
        getCommand("gmsp").setExecutor(new Spectator(this));
        getCommand("gma").setExecutor(new Adventure(this));
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new KingdomsListener(this), this);
        if (this.getConfig().contains("kingdoms")) {
            restoreKingdomData();
        }
        if (this.getConfig().contains("playerRank")) {
            restorePlayerRank();
        }
        if (this.getConfig().contains("kingdomOwner")) {
            restoreKingdomOwner();
        }
        if (this.getConfig().contains("kingdomAdmins")) {
            restoreKingdomAdmins();
        }
        if (this.getConfig().contains("kingdomMembers")) {
            restoreKingdomMembers();
        }
        if (this.getConfig().contains("staff")) {
            restoreStaff();
        }
        if (this.getConfig().contains("canUnclaim")) {
            restoreCanUnclaim();
        }
        if (this.getConfig().contains("canClaim")) {
            restoreCanClaim();
        }
        if (this.getConfig().contains("claimedChunks")) {
            restoreClaims();
        }
        if (this.getConfig().contains("inviteList")) {
            restoreInviteList();
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

        //Economy
//        economyCore = new EconomyCore(this);
//        if (!setupEconomy()) {
//            MessageManager.consoleBad("Economy could not be registered...Vault is missing");
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }
//        MessageManager.consoleGood("Kingdoms has successfully launched");
//        getCommand("createaccount").setExecutor(new CreateAccount(this));

        //Events
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        MessageManager.consoleGood("Kingdoms successfully enabled");

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
        if (!canUnclaim.isEmpty()) {
            saveCanUnclaim();
        }
        if (!canClaim.isEmpty()) {
            saveCanClaim();
        }
        if (!claimedChunks.isEmpty()) {
            saveClaims();
        }
        if (!inviteList.isEmpty()) {
            saveInviteList();
        }

        MessageManager.consoleGood("Kingdoms successfully disabled");
    }

    public void saveKingdomData() {
        for (Map.Entry<String, String> kingdoms : kingdoms.entrySet()) {
            this.getConfig().set("kingdoms." + kingdoms.getKey(), kingdoms.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomData() {
        this.getConfig().getConfigurationSection("kingdoms").getKeys(false).forEach(key -> {
            String kingdom = (String) this.getConfig().get("kingdoms." + key);
            kingdoms.put(key, kingdom);
        });
    }

    public void saveKingdomOwner() {
        for (Map.Entry<String, String> owner : owner.entrySet()) {
            this.getConfig().set("kingdomOwner." + owner.getKey(), owner.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomOwner() {
        this.getConfig().getConfigurationSection("kingdomOwner").getKeys(false).forEach(key -> {
            String owner = (String) this.getConfig().get("kingdomOwner." + key);
           this.owner.put(key, owner);
        });
    }

    public void saveKingdomAdmins() {
        for (Map.Entry<String, String> admins : admin.entrySet()) {
            this.getConfig().set("kingdomAdmins." + admins.getKey(), admins.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomAdmins() {
        this.getConfig().getConfigurationSection("kingdomAdmins").getKeys(false).forEach(key -> {
            String admin = (String) this.getConfig().get("kingdomAdmins." + key);
            this.admin.put(key, admin);
        });
    }

    public void saveKingdomMembers() {
        for (Map.Entry<String, String> members : member.entrySet()) {
            this.getConfig().set("kingdomMembers." + members.getKey(), members.getValue());
        }
        this.saveConfig();
    }

    public void restoreKingdomMembers() {
        this.getConfig().getConfigurationSection("kingdomMembers").getKeys(false).forEach(key -> {
            String members = (String) this.getConfig().get("kingdomMembers." + key);
            member.put(key, members);
        });
    }

    public void savePlayerRank() {
        for (Map.Entry<String, String> playerRank : playerRank.entrySet()) {
            this.getConfig().set("playerRank." + playerRank.getKey(), playerRank.getValue());
        }
        this.saveConfig();
    }

    public void restorePlayerRank() {
        this.getConfig().getConfigurationSection("playerRank").getKeys(false).forEach(key -> {
            String playerRank = (String) this.getConfig().get("playerRank." + key);
            this.playerRank.put(key, playerRank);
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

    public void saveCanUnclaim() {
        for (Map.Entry<String, String> canUnclaim : canUnclaim.entrySet()) {
            this.getConfig().set("canUnclaim." + canUnclaim.getKey(), canUnclaim.getValue());
        }
        this.saveConfig();
    }

    public void restoreCanUnclaim() {
        this.getConfig().getConfigurationSection("canUnclaim").getKeys(false).forEach(key -> {
            String canUnclaim = (String) this.getConfig().get("canUnclaim." + key);
            this.canUnclaim.put(key, canUnclaim);
        });
    }

    public void saveCanClaim() {
        for (Map.Entry<String, String> canClaim : canClaim.entrySet()) {
            this.getConfig().set("canClaim." + canClaim.getKey(), canClaim.getValue());
        }
        this.saveConfig();
    }

    public void restoreCanClaim() {
        this.getConfig().getConfigurationSection("canClaim").getKeys(false).forEach(key -> {
            String canClaim = (String) this.getConfig().get("canClaim." + key);
            this.canClaim.put(key, canClaim);
        });
    }

    public void saveClaims() {
        for (Map.Entry<String, String> claimedChunks : claimedChunks.entrySet()) {
            this.getConfig().set("claimedChunks." + claimedChunks.getKey(), claimedChunks.getValue());
        }
        this.saveConfig();
    }

    public void restoreClaims() {
        this.getConfig().getConfigurationSection("claimedChunks").getKeys(false).forEach(key -> {
            String claimedChunks = (String) this.getConfig().get("claimedChunks." + key);
            this.claimedChunks.put(key, claimedChunks);
        });
    }

    public void saveInviteList() {
        for (Map.Entry<String, String> inviteList : inviteList.entrySet()) {
            this.getConfig().set("inviteList." + inviteList.getKey(), inviteList.getValue());
        }
        this.saveConfig();
    }

    public void restoreInviteList() {
        this.getConfig().getConfigurationSection("inviteList").getKeys(false).forEach(key -> {
            String inviteList = (String) this.getConfig().get("inviteList." + key);
            this.inviteList.put(key, inviteList);
        });
    }

    //Scoreboard
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPlayedBefore()) {
            playerRank.put(p.getUniqueId().toString(), Rank.DEFAULT.getColor().toString() + Rank.DEFAULT.getName());
            if (mongoConnect.getPlayerDataCollection().find(new Document("uuid", p.getUniqueId().toString())).first() == null) {
                playerManagerHashMap.put(p.getUniqueId(), new PlayerManager(p.getUniqueId().toString(), 0 , 0));
            }
        } else {
            restoreStaff();
            restoreClaims();
            restoreCanClaim();
            restoreCanUnclaim();
            restoreInviteList();
            restorePlayerRank();
            restoreKingdomData();
            restoreKingdomOwner();
            restoreKingdomAdmins();
            restoreKingdomMembers();
        }
        nonPvPBoard(p);
    }

    @EventHandler
    public void onJoin1(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Welcome " + ChatColor.WHITE + ChatColor.BOLD + p.getName() + ChatColor.GREEN + ChatColor.BOLD + " to " + ChatColor.YELLOW + ChatColor.BOLD + "Kingdoms");
            return;
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandSendEvent event) {
        nonPvPBoard(event.getPlayer());
        saveStaff();
        saveClaims();
        saveCanClaim();
        saveCanUnclaim();
        saveKingdomData();
        saveKingdomOwner();
        saveKingdomAdmins();
        saveKingdomMembers();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        nonPvPBoard(event.getEntity());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        nonPvPBoard(event.getPlayer());
        saveStaff();
        saveClaims();
        saveCanClaim();
        saveCanUnclaim();
        saveKingdomData();
        saveKingdomOwner();
        saveKingdomAdmins();
        saveKingdomMembers();
    }

    private void nonPvPBoard(Player player) {

        Chunk chunk = player.getLocation().getChunk();
        String k = kingdoms.get(player.getUniqueId().toString());
        String chunkID = chunk.getX() + "," + chunk.getZ();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd");
        Date date = new Date();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective obj = board.registerNewObjective("NonPvP", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lKingdoms"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score divider = obj.getScore("-----------------------");
        divider.setScore(14);
        Score p = obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "PLAYER");
        p.setScore(13);
        Score rank = obj.getScore("Rank " + playerRank.get(player.getUniqueId().toString()));
        rank.setScore(12);
        Score coins = obj.getScore("Coins " + ChatColor.GOLD + playerManagerHashMap.get(player.getUniqueId()));
        coins.setScore(11);
        Score kdr = obj.getScore("KDR " + ChatColor.YELLOW + player.getStatistic(Statistic.PLAYER_KILLS) + ChatColor.WHITE + "/" + ChatColor.YELLOW + player.getStatistic(Statistic.DEATHS));
        kdr.setScore(10);
        //edit this to show how many challenges have been completed
        Score challenges = obj.getScore(ChatColor.WHITE + "Challenges " + ChatColor.YELLOW + "WIP");
        challenges.setScore(9);
        Score blank = obj.getScore(" ");
        blank.setScore(8);
        Score kingdom = obj.getScore(ChatColor.GOLD.toString() + ChatColor.BOLD + "Kingdom " + ChatColor.WHITE + ChatColor.BOLD + kingdoms.get(player.getUniqueId().toString()));
        kingdom.setScore(7);
        Score online = obj.getScore("Online " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size());
        online.setScore(6);
        Score online_staff = obj.getScore("Staff " + ChatColor.YELLOW + staff.size());
        online_staff.setScore(5);
        Score PvP_setting = obj.getScore(ChatColor.DARK_RED + "PvP " + ChatColor.GRAY + "[" + ChatColor.RED + "OFF" + ChatColor.GRAY + "]");
        PvP_setting.setScore(4);
        Score separator = obj.getScore(ChatColor.WHITE + "-----------------------");
        separator.setScore(3);
        Score user = obj.getScore(ChatColor.RED + player.getName() + " " + ChatColor.GRAY + dateFormat.format(date));
        user.setScore(2);
        Score server_ip = obj.getScore(ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "play.kingdoms.games");
        server_ip.setScore(1);
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
        Player player = event.getPlayer();
        if (playerRank.containsValue(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.DARK_GRAY + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.GREEN.toString() + ChatColor.BOLD + Rank.VIP)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.GREEN + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.AQUA.toString() + ChatColor.BOLD + Rank.HERO)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.AQUA + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(Rank.YOUTUBE.getColor().toString() + ChatColor.BOLD + Rank.YOUTUBE.getName() + Rank.YOUTUBE.getSecondaryColor() + ChatColor.BOLD + Rank.YOUTUBE.getSecondaryName())) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.DARK_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + Rank.JRMOD)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + playerRank.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.YELLOW.toString() + ChatColor.BOLD + Rank.MOD)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + playerRank.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.GOLD.toString() + ChatColor.BOLD + Rank.SRMOD)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + playerRank.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.JRADMIN)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + playerRank.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        if (playerRank.containsKey(player.getUniqueId().toString()) && playerRank.containsValue(ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.ADMIN)) {
            String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + playerRank.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
            event.setFormat(format);
        }
        event.setMessage(eventMessage);
    }

    public FileList getFileList() {
        return files;
    }

    public Map<String, String> getPlayerRank() {
        return playerRank;
    }

    public Map<String, String> getClaimedChunks() {
        return claimedChunks;
    }

    public Map<String, String> getOwner() {
        return owner;
    }

    public Map<String, String> getAdmin() {
        return admin;
    }

    public Map<String, String> getMember() {
        return member;
    }

    public void addChunk(String chunk, String kingdom) {
        claimedChunks.put(chunk, kingdom);
    }

    public boolean isClaimedChunk(String chunk) {
        return claimedChunks.containsKey(chunk);
    }

    public String getOwner(String chunk) {
        return claimedChunks.get(chunk);
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            MessageManager.consoleBad("Economy could not be registered...Vault is missing");
            return false;
        }
        getServer().getServicesManager().register(Economy.class, economyCore, this, ServicePriority.Highest);
        MessageManager.consoleGood("Economy has been registered");
        return true;
    }

    public ArrayList<Player> getInvisiblePlayers() {
        return invisiblePlayers;
    }

    public HashMap<String, String> getCanClaim() {
        return canClaim;
    }

    public HashMap<String, String> getCanUnclaim() {
        return canUnclaim;
    }

    public HashMap<UUID, PlayerManager> getPlayerManagerHashMap() {
        return playerManagerHashMap;
    }
}