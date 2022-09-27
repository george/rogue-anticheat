#pragma once

#include <vector>

#include "../packet.h"

class PacketPlayOutDestroyEntities : public Packet {

    std::vector<int> entities;

public:

    PacketPlayOutDestroyEntities(json json)
    {
        auto entityIds = json["entities"];

        for(int i = 0; i < entityIds.size(); i++) {
            int entityId = entityIds[i];

            entities.push_back(entityId);
        }
    }

    std::vector<int> getEntityIds() {
        return entities;
    }

};