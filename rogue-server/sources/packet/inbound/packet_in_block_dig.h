#pragma once

#include <string>

#include "../packet.h"
#include "../../location/block_location.h"

class PacketPlayInBlockDig : public Packet {

    const BlockLocation blockPosition;
    const std::string digType;

public:

    PacketPlayInBlockDig(json json) :
            blockPosition(BlockLocation(json["blockPosition"])),
            digType(json["digType"])
    {}

    auto getBlockPosition() -> BlockLocation {
        return blockPosition;
    }

    auto getDigType() -> std::string {
        return digType;
    }
};