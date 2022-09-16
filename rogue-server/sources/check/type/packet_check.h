#pragma once

#include "../check.h"
#include "../../event/type/packet_event.h"

class PacketCheck : public Check {

    virtual auto handle(PacketEvent *event) -> void = 0;

};