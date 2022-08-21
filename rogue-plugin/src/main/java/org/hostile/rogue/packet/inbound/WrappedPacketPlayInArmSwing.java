package org.hostile.rogue.packet.inbound;

import org.hostile.rogue.packet.WrappedPacket;
import org.hostile.rogue.util.json.JsonChain;

public class WrappedPacketPlayInArmSwing extends WrappedPacket {

    @Override
    public JsonChain serialize() {
        return new JsonChain();
    }
}
