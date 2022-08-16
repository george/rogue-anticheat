package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;

@Getter
public class WrappedPacketPlayInClientCommand extends WrappedPacket {

    private final EnumWrappers.ClientCommand clientCommand;

    public WrappedPacketPlayInClientCommand(PacketContainer packetContainer) {
        this.clientCommand = packetContainer.getClientCommands().read(0);
    }
}
