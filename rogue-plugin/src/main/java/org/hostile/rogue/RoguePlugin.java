package org.hostile.rogue;

import com.comphenix.protocol.ProtocolLibrary;
import dev.thomazz.pledge.api.Pledge;
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

    private Pledge pledge;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        rogueWebClient = new RogueWebClient(getConfig());
        violationHandler = new ViolationHandler(getConfig());

        pledge = Pledge.build().range(Short.MIN_VALUE, (short) -500);
        pledge.start(this);

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