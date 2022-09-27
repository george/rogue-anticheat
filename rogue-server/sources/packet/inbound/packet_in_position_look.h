#pragma once

#include "./packet_in_flying.h"

class PacketPlayInPositionLook : public PacketPlayInFlying {

public:

    PacketPlayInPositionLook(json json) :
            PacketPlayInFlying(json)
    {}

};