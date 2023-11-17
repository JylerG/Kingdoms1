package games.kingdoms.kingdoms.publiccmds.kingdoms;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpigotConfig {
    private File configFile;
    private FileConfiguration config;

    public SpigotConfig(File configFile) {
        this.configFile = configFile;
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void addSection(String sectionName) {
        this.config.createSection(sectionName);
    }

    public void setProperty(String sectionName, String propertyName, Object value) {
        this.config.set(sectionName + "." + propertyName, value);
    }

    public Object get(String sectionName, String propertyName) {
        ConfigurationSection section = config.getConfigurationSection(sectionName);
        if (section == null) {
            return null;
        }
        return section.get(propertyName);
    }

    public void remove(String sectionName, String propertyName) {
        if (propertyName == null) {
            // Remove the entire section if propertyName is null
            config.set(sectionName, null);
        } else {
            // Remove the property from the specified section
            ConfigurationSection section = config.getConfigurationSection(sectionName);
            if (section != null) {
                section.set(propertyName, null);
            }
        }
        saveConfig();
    }

    public void saveConfig() {
        try {
            this.config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
