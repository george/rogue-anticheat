package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInSettings extends WrappedPacket {

    private final String language;
    private final int view;
    private final EnumWrappers.ChatVisibility chatVisibility;
    private final boolean enableColors;
    private final int modelPartFlags;

    public WrappedPacketPlayInSettings(PacketContainer packetContainer) {
        this.language = packetContainer.getStrings().read(0);
        this.view = packetContainer.getIntegers().read(0);
        this.chatVisibility = packetContainer.getChatVisibilities().read(0);
        this.enableColors = packetContainer.getBooleans().read(0);
        this.modelPartFlags = packetContainer.getIntegers().read(1);
    }
}
