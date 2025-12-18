package io.github.derec4.elytraVaults.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;

    private static final String DETECTION_RADIUS = "detection-radius";
    private static final String ENABLE_TEXT_DISPLAY = "enable-text-display";
    private static final String TEST_MODE = "test-mode";

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        config.addDefault(DETECTION_RADIUS, 10);
        config.addDefault(ENABLE_TEXT_DISPLAY, true);
        config.addDefault(TEST_MODE, true);
        config.options().copyDefaults(true);

        plugin.saveConfig();
    }

    public int getDetectionRadius() {
        return config.getInt(DETECTION_RADIUS, 10);
    }

    public boolean isTextDisplayEnabled() {
        return config.getBoolean(ENABLE_TEXT_DISPLAY, true);
    }

    public boolean isTestMode() {
        return config.getBoolean(TEST_MODE, true);
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
}