package io.github.derec4.elytraVaults.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
            Component keyText = getKeyDisplayText(keyItemMaterial);

            td.text(keyText);
            td.setBillboard(Display.Billboard.CENTER);
            td.setBackgroundColor(org.bukkit.Color.fromARGB(0, 0, 0, 0));
            td.setSeeThrough(false);
            td.addScoreboardTag("elytra_vault_text");
        });
    }

    public static Component getKeyDisplayText(Material keyMaterial) {
        return Component.text("Open With ")
                .append(formatMaterialName(keyMaterial).color(NamedTextColor.AQUA));
    }

    private static Component formatMaterialName(Material material) {
        NamespacedKey key = material.getKey();
        String langkey = String.format("item.%s.%s", key.getNamespace(), key.getKey());
        return Component.text("[").append(Component.translatable(langkey))
                .append(Component.text("]"));
    }
}
