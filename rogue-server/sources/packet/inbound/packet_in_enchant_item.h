#pragma once

#include "../packet.h"

class PacketPlayInEnchantItem : public Packet {

    const int windowId;
    const int button;

public:

    PacketPlayInEnchantItem(json json) :
        windowId(json["windowId"]),
        button(json["button"])
    {}

    auto getWindowId() -> int {
        return windowId;
    }

    auto getButton() -> int {
        return button;
    }
};