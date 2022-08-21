package org.hostile.rogue.packet;

import com.google.gson.JsonObject;
import org.hostile.rogue.util.json.JsonChain;

public abstract class WrappedPacket {

    public abstract JsonChain serialize();

}
