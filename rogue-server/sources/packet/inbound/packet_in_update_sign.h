#pragma once

#include "../packet.h"
#include "../../location/block_location.h"

class PacketPlayInUpdateSign : public Packet {

    const BlockLocation blockLocation;

public:

    PacketPlayInUpdateSign(json json) :
            blockLocation(BlockLocation(json["blockPosition"]))
    {}

    auto getBlockLocation() -> BlockLocation {
        return blockLocation;
    }
};