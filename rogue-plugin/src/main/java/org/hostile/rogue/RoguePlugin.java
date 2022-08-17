package org.hostile.rogue;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.hostile.rogue.listener.PacketListener;
import org.hostile.rogue.manager.impl.PlayerDataManager;

@Getter
public class RoguePlugin extends JavaPlugin {

    @Getter private static RoguePlugin instance;

    private final PlayerDataManager playerDataManager = new PlayerDataManager();

    @Override
    public void onEnable() {
        instance = this;

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
    }

    @Override
    public void onDisable() {
    }
}