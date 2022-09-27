#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInChat : public Packet {

    const std::string message;

public:

    PacketPlayInChat(json json) :
        message(json["message"])
    {}

    auto getMessage() -> std::string {
        return message;
    }
};