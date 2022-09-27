#pragma once

#include <string>

#include "../packet.h"
#include "../../location/vector.h"

class PacketPlayInUseEntity : public Packet {

    const int entityId;
    const std::string action;

    Vector hitVec{0, 0, 0};

public:

    PacketPlayInUseEntity(json json) :
        entityId(json["entityId"]),
        action(json["action"])
    {
        if (json.contains("vec")) {
            auto vec = json["vec"];

            hitVec.add(new Vector(
                    vec["x"],
                    vec["y"],
                    vec["z"]
            ));
        }
    }

    auto getEntityId() -> int {
        return entityId;
    }

    auto getAction() -> std::string {
        return action;
    }

    auto getHitVec() -> Vector {
        return hitVec;
    }
};
