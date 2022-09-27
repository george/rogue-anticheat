#pragma once

#include "../packet.h"

class PacketPlayOutKeepAlive : public Packet {

    const int key;

public:

    PacketPlayOutKeepAlive(json json) :
        key(json["key"])
    {}

    auto getKey() -> int {
        return key;
    }
};