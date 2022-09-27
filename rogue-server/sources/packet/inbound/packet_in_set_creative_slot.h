#pragma once

#include <string>

#include "../packet.h"

class PacketPlayInSetCreativeSlot : public Packet {

    const int slot;
    const std::string itemStack;

    PacketPlayInSetCreativeSlot(json json) :
        slot(json["slot"]),
        itemStack(json["material"])
    {}

    auto getSlot() -> int {
        return slot;
    }

    auto getItemStack() -> std::string {
        return itemStack;
    }
};