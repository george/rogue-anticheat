#pragma once

#include "../packet.h"

class PacketPlayInCloseWindow : public Packet {

    const int windowId;

public:

    PacketPlayInCloseWindow(json json) :
        windowId(json["windowId"])
    {}

    auto getWindowId() -> int {
        return windowId;
    }
};