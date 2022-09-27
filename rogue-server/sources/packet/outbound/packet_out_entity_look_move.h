#include "./packet_out_entity.h"

class PacketPlayOutEntityLookMove : public PacketPlayOutEntity {

public:

    PacketPlayOutEntityLookMove(json json) :
        PacketPlayOutEntity(json)
    {}

};