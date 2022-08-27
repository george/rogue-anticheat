package org.hostile.rogue.violation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.hostile.rogue.RoguePlugin;
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

                    if (configuration.get("punishments." + section + ".command") != null) {
                        String command = configuration.getString("punishments." + section + ".command");

                        actions.add((checkData, data) -> {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%",
                                    data.getPlayer().getName()));
                        });
                    } else if (configuration.get("punishments." + section + ".message") != null) {
                        String message = configuration.getString("punishments." + section + ".message.contents");
                        String header = configuration.getString("punishments." + section + ".message.component-header");
                        String footer = configuration.getString("punishments." + section + ".message.component-footer");
                        String dataMessage = configuration.getString("punishments." + section + ".message.component-data");

                        actions.add((checkData, data) -> {
                            TextComponent textComponent = new TextComponent();
                            textComponent.setText(ChatColor.translateAlternateColorCodes('&',
                                    message.replaceAll("%player%", data.getPlayer().getName())
                                            .replaceAll("%check%", checkData.get("checkName").getAsString())
                                            .replaceAll("%type%", checkData.get("checkType").getAsString())
                                            .replaceAll("%violations%", checkData.get("violations").getAsString())
                                            .replaceAll("%max_violations%", checkData.get("maxViolations").getAsString())
                            ));

                            StringBuilder hoverComponentBuilder = new StringBuilder();
                            hoverComponentBuilder.append(header).append("\n");

                            JsonArray violationData = checkData.get("data").getAsJsonArray();

                            if (violationData.size() > 0) {
                                for (int i = 0; i < violationData.size(); i++) {
                                    JsonObject object = violationData.get(i).getAsJsonObject();

                                    String name = object.get("name").getAsString();
                                    String flagData = object.get("data").getAsString();

                                    hoverComponentBuilder.append(dataMessage.replace("%name%", name)
                                            .replace("%data%", flagData)
                                    ).append("\n");
                                }

                                hoverComponentBuilder.append(footer);
                                BaseComponent[] hoverComponents = new ComponentBuilder(
                                        ChatColor.translateAlternateColorCodes('&', hoverComponentBuilder.toString())
                                ).create();

                                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponents));
                            }

                            Bukkit.getOnlinePlayers().stream()
                                    .filter(player -> player.hasPermission(permission))
                                    .forEach(player -> {
                                        player.spigot().sendMessage(textComponent);
                                    });
                        });
                    }

                    this.actions.put(section, actions);
                });
    }

    public void handle(PlayerData playerData, JsonObject flagData) {
        String action = flagData.get("action").getAsString();

        Bukkit.getScheduler().runTask(RoguePlugin.getInstance(), () -> {
            actions.get(action).forEach(serverAction -> serverAction.accept(flagData, playerData));
        });
    }
}
