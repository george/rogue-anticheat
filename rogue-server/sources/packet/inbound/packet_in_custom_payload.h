#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInCustomPayload : public Packet {

    const std::string payload;

public:

    PacketPlayInCustomPayload(json json) :
        payload(json["payload"])
    {}

    auto getPayload() -> std::string {
        return payload;
    }
};