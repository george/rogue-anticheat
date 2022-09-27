#pragma once

#include "../packet.h"

class PacketPlayInArmAnimation : public Packet {

    const long timestamp;

public:

    PacketPlayInArmAnimation(long timestamp) :
        timestamp(timestamp)
    {}

    auto getTimestamp() -> long {
        return timestamp;
    }
};