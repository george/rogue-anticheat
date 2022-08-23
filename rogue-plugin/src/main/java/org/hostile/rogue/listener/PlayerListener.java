package org.hostile.rogue.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.hostile.rogue.RoguePlugin;

public class PlayerListener implements Listener {

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        RoguePlugin.getInstance().getPlayerDataManager().getPlayerData(event.getPlayer()).handleQuit();
    }
}
