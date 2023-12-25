package games.kingdoms.kingdoms;

import games.kingdoms.kingdoms.admin.balance.EconomyCommand;
import games.kingdoms.kingdoms.admin.balance.EconomyTabCompleter;
import games.kingdoms.kingdoms.admin.customitems.MerchantCommand;
import games.kingdoms.kingdoms.admin.customitems.MerchantListener;
import games.kingdoms.kingdoms.admin.customitems.customores.CustomOreCommand;
import games.kingdoms.kingdoms.admin.customitems.customores.CustomOreTabCompleter;
import games.kingdoms.kingdoms.admin.gamemodes.Adventure;
import games.kingdoms.kingdoms.admin.gamemodes.Creative;
import games.kingdoms.kingdoms.admin.gamemodes.Spectator;
import games.kingdoms.kingdoms.admin.gamemodes.Survival;
import games.kingdoms.kingdoms.admin.ranks.Rank;
import games.kingdoms.kingdoms.admin.ranks.RankCMD;
import games.kingdoms.kingdoms.admin.ranks.RankTabCompleter;
import games.kingdoms.kingdoms.admin.staffchat.StaffChat;
import games.kingdoms.kingdoms.admin.staffchat.StaffChatTabCompleter;
import games.kingdoms.kingdoms.admin.staffvault.StaffVault;
import games.kingdoms.kingdoms.admin.staffvault.StaffVaultListener;
import games.kingdoms.kingdoms.admin.vanish.commands.VanishCMD;
import games.kingdoms.kingdoms.admin.vanish.events.JoinEvent;
import games.kingdoms.kingdoms.publiccmds.balance.BalanceCommand;
import games.kingdoms.kingdoms.publiccmds.balance.PayCommand;
import games.kingdoms.kingdoms.publiccmds.kingdoms.KingdomsCommands;
import games.kingdoms.kingdoms.publiccmds.kingdoms.KingdomsListener;
import games.kingdoms.kingdoms.publiccmds.nightvision.Commands;
import games.kingdoms.kingdoms.publiccmds.randomtp.RandomTeleportListener;
import games.kingdoms.kingdoms.publiccmds.randomtp.rtp;
import games.kingdoms.kingdoms.publiccmds.teleports.WarzoneCMD;
import games.kingdoms.kingdoms.publiccmds.teleports.WarzoneCommandListener;
import games.kingdoms.kingdoms.rankedcmds.feed.Feed;
import games.kingdoms.kingdoms.rankedcmds.fly.Fly;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class Kingdoms extends JavaPlugin implements Listener {
    private static Kingdoms plugin;
    private HashMap<String, String> canUnclaim = new HashMap<>();
    private HashMap<String, String> canClaim = new HashMap<>();
    private Map<String, String> playerRank = new HashMap<>();
    private Map<String, String> owner = new HashMap<>();
    private final Map<String, String> kingdomOwner = new HashMap<>();
    private final Map<String, String> kingdomAdmin = new HashMap<>();
    private final Map<String, String> kingdomMember = new HashMap<>();
    private Map<String, String> admin = new HashMap<>();
    private Map<String, String> member = new HashMap<>();
    private Map<String, String> inviteList = new HashMap<>();
    private Map<String, String> claimedChunks = new HashMap<>();
    private Map<String, Location> kingdomSpawn = new HashMap<>();
    private Map<String, String> staff = new HashMap<>();
    private Map<String, String> kingdoms = new HashMap<>();
    private final ArrayList<Player> invisiblePlayers = new ArrayList<>();
    private HashMap<String, Long> money = new HashMap<>();

    @Override
    public void onEnable() {

        //Kingdoms
        plugin = this;
        this.money = new HashMap<>();
        this.playerRank = new HashMap<>();
        this.kingdomSpawn = new HashMap<>();
        this.kingdoms = new HashMap<>();
        this.member = new HashMap<>();
        this.owner = new HashMap<>();
        this.admin = new HashMap<>();
        this.staff = new HashMap<>();
        this.inviteList = new HashMap<>();
        this.claimedChunks = new HashMap<>();
        this.canUnclaim = new HashMap<>();
        this.canClaim = new HashMap<>();
        kingdomSpawn = this.restoreKingdomSpawns();
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
        if (this.getConfig().contains("money")) {
            restoreMoney();
        }
        if (this.getConfig().contains("owner")) {
            restoreOwner();
        }
        if (this.getConfig().contains("admin")) {
            restoreAdmin();
        }
        if (this.getConfig().contains("member")) {
            restoreMember();
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
        kingdom();
        gameModes();
        rank();
        ore();
        Pay();
        Eco();
        balanceCMDs();
        Merchant();
        staffChat();

        //Events
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new WarzoneCommandListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new CustomOreCommand(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new KingdomsListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new RandomTeleportListener(this), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Kingdoms successfully Enabled");
    }

    @Override
    public void onDisable() {
        if (!kingdomOwner.isEmpty()) {
            saveOwner();
        }
        if (!kingdomAdmin.isEmpty()) {
            saveAdmin();
        }
        if (!kingdomMember.isEmpty()) {
            saveMember();
        }
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
        if (!kingdomSpawn.isEmpty()) {
            saveKingdomSpawns(kingdomSpawn);
        }
        if (!money.isEmpty()) {
            saveMoney();
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Kingdoms Successfully Disabled");
    }

    public void saveMoney() {
        for (Map.Entry<String, Long> money : money.entrySet()) {
            this.getConfig().set("money." + money.getKey(), money.getValue());
        }
        this.saveConfig();
    }

    public void restoreMoney() {
        this.getConfig().getConfigurationSection("money").getKeys(false).forEach(key -> {
            String moneyStr = this.getConfig().getString("money." + key);
            long moneyInt = Long.parseLong(moneyStr);
            this.money.put(key, moneyInt);
        });
    }

    public void saveKingdomData() {
        for (Map.Entry<String, String> kingdoms : kingdoms.entrySet()) {
            this.getConfig().set("kingdoms." + kingdoms.getKey(), kingdoms.getValue());
        }
        this.saveConfig();
    }

    public void saveKingdomSpawns(Map<String, Location> kingdomSpawn) {
        ConfigurationSection section = this.getConfig().createSection("kingdomSpawn");
        for (Map.Entry<String, Location> entry : kingdomSpawn.entrySet()) {
            String kingdomName = entry.getKey();
            Location location = entry.getValue();
            section.set(kingdomName + ".world", location.getWorld().getName());
            section.set(kingdomName + ".x", location.getX());
            section.set(kingdomName + ".y", location.getY());
            section.set(kingdomName + ".z", location.getZ());
            section.set(kingdomName + ".yaw", location.getYaw());
            section.set(kingdomName + ".pitch", location.getPitch());
        }
        this.saveConfig();
    }

    public Map<String, Location> restoreKingdomSpawns() {
        Map<String, Location> kingdomSpawn = new HashMap<>();
        ConfigurationSection section = this.getConfig().getConfigurationSection("kingdomSpawn");
        if (section != null) {
            for (String kingdomName : section.getKeys(false)) {
                World world = Bukkit.getWorld("kingdoms");
                double x = section.getDouble(kingdomName + ".x");
                double y = section.getDouble(kingdomName + ".y");
                double z = section.getDouble(kingdomName + ".z");
                float yaw = (float) section.getDouble(kingdomName + ".yaw");
                float pitch = (float) section.getDouble(kingdomName + ".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);
                kingdomSpawn.put(kingdomName, location);
            }
        }
        return kingdomSpawn;
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

    public void saveOwner() {
        for (Map.Entry<String, String> canClaim : kingdomOwner.entrySet()) {
            this.getConfig().set("owner." + canClaim.getKey(), canClaim.getValue());
        }
        this.saveConfig();
    }

    public void restoreOwner() {
        this.getConfig().getConfigurationSection("owner").getKeys(false).forEach(key -> {
            String canClaim = (String) this.getConfig().get("owner." + key);
            this.canClaim.put(key, canClaim);
        });
    }

    public void saveAdmin() {
        for (Map.Entry<String, String> canClaim : kingdomAdmin.entrySet()) {
            this.getConfig().set("admin." + canClaim.getKey(), canClaim.getValue());
        }
        this.saveConfig();
    }

    public void restoreAdmin() {
        this.getConfig().getConfigurationSection("admin").getKeys(false).forEach(key -> {
            String canClaim = (String) this.getConfig().get("admin." + key);
            this.canClaim.put(key, canClaim);
        });
    }

    public void saveMember() {
        for (Map.Entry<String, String> canClaim : kingdomMember.entrySet()) {
            this.getConfig().set("member." + canClaim.getKey(), canClaim.getValue());
        }
        this.saveConfig();
    }

    public void restoreMember() {
        this.getConfig().getConfigurationSection("member").getKeys(false).forEach(key -> {
            String canClaim = (String) this.getConfig().get("member." + key);
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

    private void nightVision() {
        getCommand("nv").setExecutor(new Commands(this));
    }

    private void setRank() {
        getCommand("rank").setExecutor(new RankCMD(this));
    }

    private void staffVault() {
        getCommand("staffvault").setExecutor(new StaffVault());
        Bukkit.getServer().getPluginManager().registerEvents(new StaffVaultListener(), this);
    }

    private void staffChat() {
        getCommand("s").setExecutor(new StaffChat(this));
        getCommand("s").setTabCompleter(new StaffChatTabCompleter());
    }

    private void balanceCMDs() {
        getCommand("balance").setExecutor(new BalanceCommand(this));
    }

    private void Feed() {
        getCommand("feed").setExecutor(new Feed());
    }

    private void Eco() {
        getCommand("eco").setExecutor(new EconomyCommand(this));
        getCommand("eco").setTabCompleter(new EconomyTabCompleter());
    }

    private void Pay() {
        getCommand("pay").setExecutor(new PayCommand(this));
    }

    private void Fly() {
        getCommand("fly").setExecutor(new Fly(this));
    }

    private void warZone() {
        getCommand("warzone").setExecutor(new WarzoneCMD(this));
    }

    private void Vanish() {
        getCommand("vanish").setExecutor(new VanishCMD(this));
    }

    private void Rtp() {
        getCommand("rtp").setExecutor(new rtp());
    }

    private void kingdom() {
        getCommand("kingdom").setExecutor(new KingdomsCommands(this));
    }

    private void rank() {
        getCommand("rank").setExecutor(new RankCMD(this));
        getCommand("rank").setTabCompleter(new RankTabCompleter(this));
    }

    private void Merchant() {
        getCommand("merchant").setExecutor(new MerchantCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new MerchantListener(this), this);
    }

    private void gameModes() {
        getCommand("gmc").setExecutor(new Creative(this));
        getCommand("gms").setExecutor(new Survival(this));
        getCommand("gmsp").setExecutor(new Spectator(this));
        getCommand("gma").setExecutor(new Adventure(this));
    }

    private void ore() {
        getCommand("ore").setExecutor(new CustomOreCommand(this));
        getCommand("ore").setTabCompleter(new CustomOreTabCompleter(this));
    }

    public void inKingdomBoard(Player player) {
        long moneyValue = money.get(player.getUniqueId().toString());
        String formattedMoney;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
        //Scoreboard Name
        Objective obj = board.registerNewObjective("notInKingdom", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lKingdoms"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score divider = obj.getScore("-----------------------");
        divider.setScore(14);
        Score p = obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "PLAYER");
        p.setScore(13);
        //Player rank
        Score rank = obj.getScore("Rank " + playerRank.get(player.getUniqueId().toString()));
        rank.setScore(12);
        //Coins
        if (moneyValue == 0) {
            Score coins = obj.getScore("Coins " + ChatColor.GOLD + 0);
            coins.setScore(11);
        } else {
            if (moneyValue == 1) {
                formattedMoney = "1";
            } else if (moneyValue < 1_000.0) {
                formattedMoney = String.valueOf(moneyValue);
            } else if (moneyValue < 1_000_000.0) {
                formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
            } else if (moneyValue < 1_000_000_000.0) {
                formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
            } else if (moneyValue < 1_000_000_000_000.0) {
                formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
            } else if (moneyValue < 1_000_000_000_000_000.0) {
                formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
            } else if (moneyValue < 1_000_000_000_000_000_000.0) {
                formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
            } else if (moneyValue < 1_000_000_000_000_000_000_000.0) {
                formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
            } else {
                formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
            }
            Score coins = obj.getScore("Coins " + ChatColor.GOLD + formattedMoney);
            coins.setScore(11);
        }

        //Kill/Death Ratio
        Score kdr = obj.getScore("KDR " + ChatColor.YELLOW + player.getStatistic(Statistic.PLAYER_KILLS) + ChatColor.WHITE + "/" + ChatColor.YELLOW + player.getStatistic(Statistic.DEATHS));
        kdr.setScore(10);
        //edit this to show how many challenges have been completed
        Score challenges = obj.getScore(ChatColor.WHITE + "Challenges " + ChatColor.YELLOW + "WIP");
        challenges.setScore(9);
        Score blank = obj.getScore(" ");
        blank.setScore(8);
        Score kingdom = obj.getScore(ChatColor.YELLOW.toString() + ChatColor.BOLD + "SERVER");
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

    public void notInKingdomBoard(Player player) {
        long moneyValue = money.get(player.getUniqueId().toString());
        String formattedMoney;
        Chunk chunk = player.getLocation().getChunk();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
        //Scoreboard Name
        Objective obj = board.registerNewObjective("notInKingdom", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lKingdoms"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score divider = obj.getScore("-----------------------");
        divider.setScore(14);
        Score p = obj.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "PLAYER");
        p.setScore(13);
        //Rank
        Score rank = obj.getScore("Rank " + playerRank.get(player.getUniqueId().toString()));
        rank.setScore(12);
        //Coins
        if (moneyValue == 0) {
            Score coins = obj.getScore("Coins " + ChatColor.GOLD + 0);
            coins.setScore(11);
        } else {
            if (moneyValue == 1) {
                formattedMoney = "1";
            } else if (moneyValue < 1_000.0) {
                formattedMoney = String.valueOf(moneyValue);
            } else if (moneyValue < 1_000_000.0) {
                formattedMoney = String.format("%.2fK", moneyValue / 1_000.0);
            } else if (moneyValue < 1_000_000_000.0) {
                formattedMoney = String.format("%.2fM", moneyValue / 1_000_000.0);
            } else if (moneyValue < 1_000_000_000_000.0) {
                formattedMoney = String.format("%.2fB", moneyValue / 1_000_000_000.0);
            } else if (moneyValue < 1_000_000_000_000_000.0) {
                formattedMoney = String.format("%.2fT", moneyValue / 1_000_000_000_000.0);
            } else if (moneyValue < 1_000_000_000_000_000_000.0) {
                formattedMoney = String.format("%.2fQ", moneyValue / 1_000_000_000_000_000.0);
            } else if (moneyValue < 1_000_000_000_000_000_000_000.0) {
                formattedMoney = String.format("%.2fQU", moneyValue / 1_000_000_000_000_000_000.0);
            } else {
                formattedMoney = String.format("%.2fS", moneyValue / 1_000_000_000_000_000_000_000.0);
            }
            Score coins = obj.getScore("Coins " + ChatColor.GOLD + formattedMoney);
            coins.setScore(11);
        }
        //Kill/Death Ratio
        Score kdr = obj.getScore("KDR " + ChatColor.YELLOW + player.getStatistic(Statistic.PLAYER_KILLS) + ChatColor.WHITE + "/" + ChatColor.YELLOW + player.getStatistic(Statistic.DEATHS));
        kdr.setScore(10);
        //edit this to show how many challenges have been completed
        Score challenges = obj.getScore(ChatColor.WHITE + "Challenges " + ChatColor.YELLOW + "WIP");
        challenges.setScore(9);
        Score blank = obj.getScore(" ");
        blank.setScore(8);
        Score kingdom = obj.getScore(ChatColor.YELLOW.toString() + ChatColor.BOLD + "SERVER");
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

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (kingdoms.containsKey(player.getUniqueId().toString())) {
            inKingdomBoard(player);
        } else {
            notInKingdomBoard(player);
        }
        if (this.getConfig().contains("playerRank")) {
            restorePlayerRank();
        }
        if (this.getConfig().contains("staff")) {
            restoreStaff();
        }
        if (this.getConfig().contains("inviteList")) {
            restoreInviteList();
        }
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (kingdoms.containsKey(player.getUniqueId().toString())) {
                inKingdomBoard(player);
            } else {
                notInKingdomBoard(player);
            }
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
            if (this.getConfig().contains("money")) {
                restoreMoney();
            }
            if (this.getConfig().contains("owner")) {
                restoreOwner();
            }
            if (this.getConfig().contains("admin")) {
                restoreAdmin();
            }
            if (this.getConfig().contains("member")) {
                restoreMember();
            }
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            notInKingdomBoard(player);

            playerRank.put(player.getUniqueId().toString(), ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT);
            money.put(player.getUniqueId().toString(), 0L);

        } else {
            //money.put(player.getUniqueId().toString(), 0L);
            //playerRank.put(player.getUniqueId().toString(), ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT);

            money.put(player.getUniqueId().toString(), money.get(player.getUniqueId().toString()));

            if (kingdoms.containsKey(player.getUniqueId().toString())) {
                inKingdomBoard(player);
            } else {
                notInKingdomBoard(player);
            }

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
            if (this.getConfig().contains("money")) {
                restoreMoney();
            }
            if (this.getConfig().contains("owner")) {
                restoreOwner();
            }
            if (this.getConfig().contains("admin")) {
                restoreAdmin();
            }
            if (this.getConfig().contains("member")) {
                restoreMember();
            }
        }
    }

    //TODO: Create an EventHandler to deal with changing the scoreboard to reflect whether someone is in the wilderness or not

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        String eventMessage = event.getMessage();
        Player player = event.getPlayer();
        if (kingdoms.containsKey(player.getUniqueId().toString())) {
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.GRAY + player.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.DARK_GRAY + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + Rank.VIP.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.GREEN + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.AQUA.toString() + ChatColor.BOLD + Rank.HERO.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + ChatColor.AQUA + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(Rank.YOUTUBE.getColor().toString() + ChatColor.BOLD + Rank.YOUTUBE.getName().toUpperCase() + Rank.YOUTUBE.getSecondaryColor() + ChatColor.BOLD + Rank.YOUTUBE.getSecondaryName().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + Rank.YOUTUBE.getColor() + ChatColor.BOLD + Rank.YOUTUBE.getName().toUpperCase() + Rank.YOUTUBE.getSecondaryColor() + ChatColor.BOLD + Rank.YOUTUBE.getSecondaryName().toUpperCase() + ChatColor.WHITE + player.getDisplayName() + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + Rank.JRMOD.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + Rank.MOD.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.GOLD.toString() + ChatColor.BOLD + Rank.SRMOD.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.JRADMIN.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.ADMIN.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.WHITE + "[" + ChatColor.GOLD + kingdoms.get(player.getUniqueId().toString()) + ChatColor.WHITE + "] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
        } else {
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + Rank.DEFAULT.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.GRAY + player.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.DARK_GRAY + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + Rank.VIP.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.GREEN + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.AQUA.toString() + ChatColor.BOLD + Rank.HERO.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + ChatColor.AQUA + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(Rank.YOUTUBE.getColor().toString() + ChatColor.BOLD + Rank.YOUTUBE.getName().toUpperCase() + Rank.YOUTUBE.getSecondaryColor() + ChatColor.BOLD + Rank.YOUTUBE.getSecondaryName().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + Rank.YOUTUBE.getColor() + ChatColor.BOLD + Rank.YOUTUBE.getName().toUpperCase() + Rank.YOUTUBE.getSecondaryColor() + ChatColor.BOLD + Rank.YOUTUBE.getSecondaryName().toUpperCase() + ChatColor.WHITE + player.getDisplayName() + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + Rank.JRMOD.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + Rank.MOD.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.GOLD.toString() + ChatColor.BOLD + Rank.SRMOD.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.JRADMIN.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
            if (playerRank.get(player.getUniqueId().toString()).equals(ChatColor.DARK_RED.toString() + ChatColor.BOLD + Rank.ADMIN.toString().toUpperCase())) {
                String format = ChatColor.WHITE.toString() + ChatColor.BOLD + "[G] " + playerRank.get(player.getUniqueId().toString()) + " " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE + ": " + eventMessage;
                event.setFormat(format);
            }
        }
        event.setMessage(eventMessage);
    }

    // Method to check if a chunk is claimed
    private boolean isChunkClaimed(Chunk chunk) {
        String chunkID = chunk.getX() + "," + chunk.getZ();
        return plugin.getClaimedChunks().containsKey(chunkID);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk fromChunk = event.getFrom().getChunk();
        Chunk toChunk = event.getTo().getChunk();

        if (!fromChunk.equals(toChunk)) {
            // The player entered a new chunk

            String kingdom = null;

            if (plugin.getKingdoms().containsKey(player.getUniqueId().toString())) {
                kingdom = plugin.getKingdoms().get(player.getUniqueId().toString());
            }

            String title, subtitle;

            if (isChunkClaimed(toChunk)) {
                String ownerKingdom = plugin.getClaimedChunks().get(toChunk.getX() + "," + toChunk.getZ());

                title = " ";
                subtitle = ChatColor.GREEN + ownerKingdom;
            } else {
                title = " ";
                subtitle = ChatColor.RED + "Wilderness";
            }

            sendTitle(player, title, subtitle, 10, 40, 10);
        }
    }

    // Method to send a title to a player
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
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

    public Map<String, String> getMember() {
        return member;
    }

    public Map<String, String> getStaff() {
        return staff;
    }

    public Map<String, String> getKingdoms() {
        return kingdoms;
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

    public Map<String, Location> getKingdomSpawn() {
        return kingdomSpawn;
    }

    public Map<String, String> getAdmin() {
        return admin;
    }

    public Map<String, String> getInviteList() {
        return inviteList;
    }

    public HashMap<String, Long> getMoney() {
        return money;
    }

    public Map<String, String> getKingdomOwner() {
        return kingdomOwner;
    }

    public Map<String, String> getKingdomAdmin() {
        return kingdomAdmin;
    }

    public Map<String, String> getKingdomMember() {
        return kingdomMember;
    }
}