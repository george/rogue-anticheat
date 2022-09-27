#pragma once

#include "../packet.h"

class PacketPlayInKeepAlive : public Packet {

    const int key;

public:

    PacketPlayInKeepAlive(json json) :
        key(json["key"])
    {}

    auto getKey() -> int {
        return key;
    }
};