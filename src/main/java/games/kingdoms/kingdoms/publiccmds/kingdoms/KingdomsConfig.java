package games.kingdoms.kingdoms.publiccmds.kingdoms;

import com.github.sanctum.labyrinth.data.FileList;
import com.github.sanctum.labyrinth.data.YamlExtension;
import com.github.sanctum.panther.file.Configurable;
import games.kingdoms.kingdoms.Kingdoms;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class KingdomsConfig {

    static KingdomsConfig INSTANCE;
    final FileList list;
    private Kingdoms plugin;
    private String args;
    private int argLength;
    private Map<String, Configurable> configMap = new HashMap<>();

    KingdomsConfig(Kingdoms plugin) {
        this.plugin = plugin;
        this.list = FileList.search(plugin);
    }

    public Configurable getConfig(String key) {
        // Get or create a Configurable object based on the key
        Configurable config = configMap.get(key);
        this.args = key;
        if (config == null) {
            config = list.get(key, null, YamlExtension.INSTANCE).getRoot();
            configMap.put(key, config);
        }
        return config;
    }

    public void save(String key) {
        Configurable config = configMap.get(key);
        if (config != null) {
            config.save();
        }
    }

    public Configurable getConfig(@NotNull String fileName, @Nullable String fileDirectory) {
        return list.get(fileName, fileDirectory, YamlExtension.INSTANCE).getRoot();
    }

    public void setup() {
        Configurable mainConfig = getConfig(args);
        if (!mainConfig.exists()) {
            try {
                mainConfig.create();
            } catch (IOException e) {
                e.printStackTrace(); // handle what happens when for whatever reason it doesn't have access to write the file.
            }
        }
    }

//    public void reload() {
//        for (Map.Entry<String, String> kingdom : plugin.getKingdoms().entrySet()) {
//            Configurable mainConfig = getConfig(kingdom.getKey());
//            plugin.getKingdoms().put()
//            mainConfig.reload();
//        }
//    }

    public void delete(String args) {
        Configurable mainConfig = getConfig(args);
        mainConfig.delete();
    }

    public static @NotNull KingdomsConfig getInstance() {
        return INSTANCE != null ? INSTANCE : (INSTANCE = new KingdomsConfig(Kingdoms.getPlugin(Kingdoms.class)));
    }

}