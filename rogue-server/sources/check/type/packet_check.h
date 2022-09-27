#pragma once

#include "../check.h"
#include "../../event/type/packet_event.h"

class PacketCheck : public Check {

public:

    virtual auto handle(PacketEvent *event) -> void = 0;

};