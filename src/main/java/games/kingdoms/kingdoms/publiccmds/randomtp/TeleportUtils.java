package games.kingdoms.kingdoms.publiccmds.randomtp;

import games.kingdoms.kingdoms.Kingdoms;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class TeleportUtils {

    public static Kingdoms plugin;

    public TeleportUtils(Kingdoms plugin) {
        TeleportUtils.plugin = plugin;
    }

    public static HashSet<Material> bad_blocks = new HashSet<>();

    static {
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.CACTUS);

    }

    public static Location generateLocation(Player player) {

        Random random = new Random();

        int x = random.nextInt(10000);
        int y = 1;
        int z = random.nextInt(10000);

        Location randomLocation = new Location(player.getWorld(), x, y, z);

        y = Objects.requireNonNull(randomLocation.getWorld()).getHighestBlockYAt(randomLocation);
        randomLocation.setY(y);

        player.teleport(randomLocation);

        while (!isLocationSafe(randomLocation)) {
            randomLocation = generateLocation(player);
        }
        return randomLocation;
    }

    public static boolean isLocationSafe(Location location) {

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return !(bad_blocks.contains(block.getType()) || (block.getType().isSolid()) || (above.getType().isSolid()));

    }
}
