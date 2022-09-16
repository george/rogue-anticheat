#pragma once

#include "../event.h"
#include "../../packet/packet.h"

class PacketEvent : Event {

private:

    const Packet packet;

public:
    
    PacketEvent(Packet packet) :
        packet(packet) {}

    Packet getPacket() {
        return this->packet;
    }
};