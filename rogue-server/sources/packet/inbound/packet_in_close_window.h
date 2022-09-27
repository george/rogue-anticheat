#pragma once

#include "../packet.h"

class WrappedPacketPlayInCloseWindow : public Packet {

    const int windowId;

public:

    WrappedPacketPlayInCloseWindow(json json) :
        windowId(json["windowId"])
    {}

    auto getWindowId() -> int {
        return windowId;
    }
};