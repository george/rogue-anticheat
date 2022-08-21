package org.hostile.rogue.packet;

import org.hostile.rogue.util.json.JsonChain;

public abstract class WrappedPacket {

    public abstract JsonChain serialize();

    public abstract String getName();

}
