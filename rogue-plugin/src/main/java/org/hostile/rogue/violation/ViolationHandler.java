package org.hostile.rogue.violation;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.hostile.rogue.data.PlayerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ViolationHandler {

    private final Map<String, List<BiConsumer<JsonObject, PlayerData>>> actions = new HashMap<>();

    public ViolationHandler(FileConfiguration configuration) {
        String permission = configuration.getString("flag-permission");

        configuration.getConfigurationSection("punishments").getKeys(false)
                .forEach(section -> {
                    List<BiConsumer<JsonObject, PlayerData>> actions = new ArrayList<>();

                    if (configuration.get(section + ".command") != null) {
                        String command = configuration.getString(section + ".command");

                        actions.add((checkData, data) -> {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%",
                                    data.getPlayer().getName()));
                        });
                    } else if (configuration.get(section + ".message") != null) {
                        String message = configuration.getString(section + ".message");

                        actions.add((checkData, data) -> {
                            String flagMessage = ChatColor.translateAlternateColorCodes('&',
                                    message.replaceAll("%player%", data.getPlayer().getName())
                                            .replaceAll("%check%", checkData.get("checkName").getAsString())
                                            .replaceAll("%type%", checkData.get("checkType").getAsString())
                                            .replaceAll("%violations%", checkData.get("violations").getAsString())
                                            .replaceAll("%max_violations%", checkData.get("maxViolations").getAsString())
                            );

                            Bukkit.getOnlinePlayers().stream()
                                    .filter(player -> player.hasPermission(permission))
                                    .forEach(player -> {
                                        player.sendMessage(flagMessage);
                                    });
                        });
                    }

                    this.actions.put(section, actions);
                });
    }

    public void handle(PlayerData playerData, JsonObject flagData) {
        String action = flagData.get("action").getAsString();

        actions.get(action).forEach(serverAction -> serverAction.accept(flagData, playerData));
    }
}
