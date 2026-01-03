package io.github.derec4.elytraVaults.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.block.Block;

public class TextDisplayUtils {
    public static void spawnVaultTextDisplays(Location vaultLocation, Material keyItemMaterial) {
        Block block = vaultLocation.getBlock();
        double cx = block.getX() + 0.5;
        double cz = block.getZ() + 0.5;
        double topY = block.getY() + 1.0;

        block.getWorld().spawn(new Location(block.getWorld(), cx, topY + 0.35, cz), TextDisplay.class, td -> {
            td.text(Component.text("Elytra").color(NamedTextColor.LIGHT_PURPLE));
            td.setBillboard(Display.Billboard.CENTER);
            td.setBackgroundColor(org.bukkit.Color.fromARGB(0, 0, 0, 0));
            td.setSeeThrough(false);
            td.addScoreboardTag("elytra_vault_text");
        });

        block.getWorld().spawn(new Location(block.getWorld(), cx, topY + 0.05, cz), TextDisplay.class, td -> {
            String keyItemName = formatMaterialName(keyItemMaterial);
            Component keyText = Component.text("Open With ")
                    .append(Component.text("[" + keyItemName + "]").color(NamedTextColor.AQUA));

            td.text(keyText);
            td.setBillboard(Display.Billboard.CENTER);
            td.setBackgroundColor(org.bukkit.Color.fromARGB(0, 0, 0, 0));
            td.setSeeThrough(false);
            td.addScoreboardTag("elytra_vault_text");
        });
    }

    private static String formatMaterialName(Material material) {
        String[] words = material.name().toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                formatted.append(" ");
            }
            formatted.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1));
        }

        return formatted.toString();
    }
}
