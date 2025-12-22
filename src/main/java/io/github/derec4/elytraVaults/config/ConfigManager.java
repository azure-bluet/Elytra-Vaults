package io.github.derec4.elytraVaults.config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;

    private static final String ENABLE_TEXT_DISPLAY = "enable-text-display";
    private static final String TEST_MODE = "test-mode";
    private static final String KEY_ITEM = "key-item";

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        
        // If config doesn't exist, generate it from scratch
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            generateFullConfig(configFile);
        } else {
            // Config exists, check for missing fields
            config = YamlConfiguration.loadConfiguration(configFile);
            updateMissingFields(configFile);
        }
        
        // Load the config into memory
        config = plugin.getConfig();
    }

    private void generateFullConfig(File configFile) {
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("# --------------------------------------------------\n");
            writer.write("# Vanilla friendly plugin by DerexXD\n");
            writer.write("# Report all ur troubles at the link below!\n");
            writer.write("# https://github.com/Derec-Mods/Elytra-Vaults/issues\n");
            writer.write("# --------------------------------------------------\n\n");
            
            writer.write("# Enable text display above vaults\n");
            writer.write(ENABLE_TEXT_DISPLAY + ": true\n\n");
            
            writer.write("# Test mode (for development)\n");
            writer.write(TEST_MODE + ": true\n\n");
            
            writer.write("# The material used as the key item to open the vault\n");
            writer.write("# Valid values are any Minecraft material name (e.g., SHULKER_SHELL, DIAMOND, GOLD_INGOT, etc.)\n");
            writer.write(KEY_ITEM + ": SHULKER_SHELL\n");
            
            plugin.getLogger().info("Generated config.yml");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to generate config.yml: " + e.getMessage());
        }
    }

    private void updateMissingFields(File configFile) {
        boolean needsUpdate = false;
        
        if (!config.contains(ENABLE_TEXT_DISPLAY)) {
            config.set(ENABLE_TEXT_DISPLAY, true);
            needsUpdate = true;
        }
        
        if (!config.contains(TEST_MODE)) {
            config.set(TEST_MODE, true);
            needsUpdate = true;
        }
        
        if (!config.contains(KEY_ITEM)) {
            config.set(KEY_ITEM, "SHULKER_SHELL");
            needsUpdate = true;
        }
        
        if (needsUpdate) {
            try {
                config.save(configFile);
                plugin.getLogger().info("Updated config.yml");
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to update config.yml: " + e.getMessage());
            }
        }
    }

    public boolean isTextDisplayEnabled() {
        return config.getBoolean(ENABLE_TEXT_DISPLAY, true);
    }

    public boolean isTestMode() {
        return config.getBoolean(TEST_MODE, true);
    }

    public Material getKeyItem() {
        String materialName = config.getString(KEY_ITEM, "SHULKER_SHELL");
        try {
            return Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("Invalid key-item material: " + materialName + ". Using default: SHULKER_SHELL");
            return Material.SHULKER_SHELL;
        }
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
}