package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInClientCommand extends WrappedPacket {

    private final EnumWrappers.ClientCommand clientCommand;

    public WrappedPacketPlayInClientCommand(PacketContainer packetContainer) {
        this.clientCommand = packetContainer.getClientCommands().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("clientCommand", clientCommand.name());
    }

    @Override
    public String getName() {
        return "in_client_command";
    }
}
