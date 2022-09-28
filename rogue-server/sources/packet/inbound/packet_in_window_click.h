#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInWindowClick : public Packet {

    const int windowId;
    const int slotId;
    const int usedButton;
    const short actionNumber;
    const std::string clickedItem;
    const int mode;

public:

    PacketPlayInWindowClick(json json) :
        windowId(json["windowId"]),
        slotId(json["slotId"]),
        usedButton(json["usedButton"]),
        actionNumber(json["actionNumber"]),
        mode(json["mode"])
    {}

    auto getWindowId() -> int {
        return windowId;
    }

    auto getSlotId() -> int {
        return slotId;
    }

    auto getUsedButton() -> int {
        return usedButton;
    }

    auto getActionNumber() -> short {
        return actionNumber;
    }

    auto getClickedItem() -> std::string {
        return clickedItem;
    }

    auto getMode() -> int {
        return mode;
    }
};