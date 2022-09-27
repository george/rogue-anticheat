#include "./packet_out_entity.h"

class PacketPlayOutEntityLook : public PacketPlayOutEntity {

public:

    PacketPlayOutEntityLook(json json) :
        PacketPlayOutEntity(json)
    {}

};