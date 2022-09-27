#pragma once

#include "./packet_in_flying.h"

class PacketPlayInLook : public PacketPlayInFlying {

public:

    PacketPlayInLook(json json) :
            PacketPlayInFlying(json)
    {}

};