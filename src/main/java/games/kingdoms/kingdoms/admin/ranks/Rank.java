package games.kingdoms.kingdoms.admin.ranks;

import org.bukkit.ChatColor;

public enum Rank {

    ADMIN("Admin", ChatColor.DARK_RED, null, null, false, false, 9),
    JRADMIN("JrAdmin", ChatColor.DARK_RED, null, null, false, false, 8),
    SRMOD("SrMod", ChatColor.GOLD, null, null,false, false, 7),
    MOD("Mod", ChatColor.YELLOW, null, null, false, false, 6),
    JRMOD("JrMod", ChatColor.DARK_AQUA, null, null, false, false, 5),
    YOUTUBE("YOU", ChatColor.RED, "TUBE", ChatColor.WHITE, false, false, 4),
    PLEB("Pleb", ChatColor.DARK_PURPLE, null, null, false, false, 3),
    HERO("Hero", ChatColor.AQUA, null, null, false, false, 2),
    VIP("VIP", ChatColor.GREEN, null, null, false, false, 1),
    DEFAULT("Default", ChatColor.DARK_GRAY, null, null, false, false, 0);

    private final String primaryName;
    private final ChatColor primaryColor;
    private final boolean bold;
    private final boolean italicized;
    private final int level;
    private final String secondaryName;
    private final ChatColor secondaryColor;

    Rank(String primaryName, ChatColor primaryColor, String secondaryName, ChatColor secondaryColor, boolean bold, boolean italicized, int level) {
        this.primaryName = primaryName;
        this.primaryColor = primaryColor;
        this.bold = bold;
        this.italicized = italicized;
        this.level = level;
        this.secondaryColor = secondaryColor;
        this.secondaryName = secondaryName;
    }

    public String getRank() {
        return this.primaryName;
    }

    public String getName() {
        return primaryName;
    }

    public ChatColor getColor() {
        return primaryColor;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalicized() {
        return italicized;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public ChatColor getSecondaryColor() {
        return secondaryColor;
    }

    public int getLevel() {
        return level;
    }
}
