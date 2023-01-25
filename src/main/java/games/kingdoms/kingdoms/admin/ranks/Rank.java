package games.kingdoms.kingdoms.admin.ranks;

import org.bukkit.ChatColor;

public enum Rank {

    ADMIN("Admin", ChatColor.DARK_RED, false, false, 8),
    JRADMIN("JrAdmin", ChatColor.DARK_RED, false, false, 7),
    SRMOD("SrMod", ChatColor.GOLD, false, false, 6),
    MOD("Mod", ChatColor.YELLOW, false, false, 5),
    JRMOD("JrMod", ChatColor.DARK_AQUA, false, false, 4),
    PLEB("Pleb", ChatColor.DARK_PURPLE, false, false, 3),
    HERO("Hero", ChatColor.AQUA, false, false, 2),
    VIP("VIP", ChatColor.GREEN, false, false, 1),
    DEFAULT("Default", ChatColor.DARK_GRAY, false, false, 0);

    private final String name;
    private final ChatColor color;
    private final boolean bold;
    private final boolean italicized;
    private final int level;

    Rank(String name, ChatColor color, boolean bold, boolean italicized, int level) {
        this.name = name;
        this.color = color;
        this.bold = bold;
        this.italicized = italicized;
        this.level = level;
    }

    public String getRank() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalicized() {
        return italicized;
    }

    public int getLevel() {
        return level;
    }
}
