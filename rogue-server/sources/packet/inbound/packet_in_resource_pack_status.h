#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInResourcePackStatus : public Packet {

    const std::string resourcePackStats;

public:

    PacketPlayInResourcePackStatus(json json) :
        resourcePackStats(json["resourcePackStatus"])
    {}

    auto getResourcePackStatus() -> std::string {
        return resourcePackStats;
    }
};