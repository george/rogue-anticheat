package org.hostile.anticheat.packet.inbound;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.hostile.anticheat.packet.Packet;

@Getter
public class WrappedPacketPlayInWindowClick extends Packet {

    private final int windowId;
    private final int slotId;
    private final int usedButton;
    private final short actionNumber;
    private final String clickedItem;
    private final int mode;

    public WrappedPacketPlayInWindowClick(JsonObject object) {
        this.windowId = object.get("windowId").getAsInt();
        this.slotId = object.get("slotId").getAsInt();
        this.usedButton = object.get("usedButton").getAsInt();
        this.actionNumber = object.get("actionNumber").getAsShort();
        this.clickedItem = object.get("clickedItem").getAsString();
        this.mode = object.get("mode").getAsInt();
    }
}