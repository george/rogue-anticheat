#pragma once

#include "./packet_in_flying.h"

class PacketPlayInPosition : public PacketPlayInFlying {

public:

    PacketPlayInPosition(json json) :
            PacketPlayInFlying(json)
    {}

};