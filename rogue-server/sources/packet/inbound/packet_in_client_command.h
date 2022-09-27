#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInClientCommand : public Packet {

    const std::string clientCommand;

public:

    PacketPlayInClientCommand(json json) :
        clientCommand(json["clientCommand"])
    {}

    auto getClientCommand() -> std::string {
        return clientCommand;
    }
};