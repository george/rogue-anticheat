package org.hostile.rogue.packet.inbound;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

@Getter
public class WrappedPacketPlayInResourcePackStatus extends WrappedPacket {

    private final EnumWrappers.ResourcePackStatus resourcePackStatus;

    public WrappedPacketPlayInResourcePackStatus(PacketContainer packetContainer) {
        this.resourcePackStatus = packetContainer.getResourcePackStatus().read(0);
    }

    @Override
    public JsonChain serialize() {
        return new JsonChain()
                .addProperty("resourcePackStatus", resourcePackStatus.name());
    }

    @Override
    public String getName() {
        return "in_resource_pack";
    }
}
