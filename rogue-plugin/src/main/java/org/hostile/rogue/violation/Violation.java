package org.hostile.rogue.violation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.hostile.rogue.data.PlayerData;

@Getter
@AllArgsConstructor
public class Violation {

    private final PlayerData playerData;
    private final int violations;
    private final int maxViolations;
    private final String checkName;
    private final String checkCategory;
    private final String[] information;

    public Document toDocument() {
        return new Document()
                .append("uuid", playerData.getPlayer().getUniqueId())
                .append("violations", violations)
                .append("maxViolations", maxViolations)
                .append("checkName", checkName)
                .append("checkCategory", checkCategory)
                .append("information", information);
    }

    public void handle() {
        TextComponent component = new TextComponent();
        StringBuilder textBuilder = new StringBuilder("&7[&b❑&7] &b");

        textBuilder.append(playerData.getPlayer().getName()).append(" &7failed &b")
                .append(checkCategory).append(" Check ").append(checkName);
        textBuilder.append(" ").append("&7&bx").append(violations).append("&7/&b").append(maxViolations);

        component.setText(ChatColor.translateAlternateColorCodes('&', textBuilder.toString()));

        StringBuilder hoverComponentBuilder = new StringBuilder();
        hoverComponentBuilder.append("&7&l&m---------------------------------------&r\n");

        if (information.length > 0) {
            for(int i = 0; i < information.length; i++) {
                hoverComponentBuilder.append("&b").append(information[i])
                        .append(" &7")
                        .append(information[++i])
                        .append("\n");
            }

            double keepAlivePing = playerData.getKeepAlivePing();
            double transactionPing = playerData.getTransactionPing();
            double avg = (keepAlivePing + transactionPing) / 2;

            hoverComponentBuilder.append("\n&bPing: &7K &b").append(keepAlivePing)
                    .append(" &7T &b").append(transactionPing)
                    .append(" &7AVG &b")
                    .append(avg);

            hoverComponentBuilder.append("\n&7&l&m---------------------------------------&r");

            BaseComponent[] hoverComponents = new ComponentBuilder(
                    ChatColor.translateAlternateColorCodes('&', hoverComponentBuilder.toString()))
                    .create();
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponents));
        }

        Bukkit.getOnlinePlayers().stream()
                .map(Player::spigot)
                .forEach(player -> player.sendMessage(component));
    }
}
