package org.hostile.rogue;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hostile.rogue.data.PlayerData;
import org.hostile.rogue.listener.PacketListener;
import org.hostile.rogue.manager.impl.PlayerDataManager;
import org.hostile.rogue.violation.ViolationHandler;
import org.hostile.rogue.web.RogueWebClient;

@Getter
public class RoguePlugin extends JavaPlugin {

    @Getter private static RoguePlugin instance;

    private final PlayerDataManager playerDataManager = new PlayerDataManager();

    private RogueWebClient rogueWebClient;
    private ViolationHandler violationHandler;

    @Override
    public void onEnable() {
        instance = this;

        rogueWebClient = new RogueWebClient(getConfig());
        violationHandler = new ViolationHandler(getConfig());

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerData data = playerDataManager.getPlayerData(player);

            data.handleQuit();
        });
    }
}