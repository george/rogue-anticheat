package org.hostile.rogue.listener;

import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.hostile.rogue.RoguePlugin;
import org.hostile.rogue.data.PlayerData;
import org.hostile.rogue.manager.impl.PlayerDataManager;
import org.hostile.rogue.packet.PacketWrapper;
import org.hostile.rogue.packet.WrappedPacket;

public class PacketListener extends PacketAdapter {

    private final PlayerDataManager playerDataManager;

    public PacketListener(RoguePlugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketWrapper.getProcessedPackets());

        this.playerDataManager = plugin.getPlayerDataManager();
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        PlayerData data = playerDataManager.getPlayerData(event.getPlayer());
        WrappedPacket wrappedPacket = PacketWrapper.wrapPacket(event.getPacketType(), event.getPacket());

        handle(data, wrappedPacket);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PlayerData data = playerDataManager.getPlayerData(event.getPlayer());
        WrappedPacket wrappedPacket = PacketWrapper.wrapPacket(event.getPacketType(), event.getPacket());

        handle(data, wrappedPacket);
    }

    public void handle(PlayerData data, WrappedPacket wrappedPacket) {
        data.handlePacket(wrappedPacket);
    }
}
