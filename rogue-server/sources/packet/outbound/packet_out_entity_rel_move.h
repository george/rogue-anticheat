#include "./packet_out_entity.h"

class PacketPlayOutEntityRelMove : public PacketPlayOutEntity {

public:

    PacketPlayOutEntityRelMove(json json) :
        PacketPlayOutEntity(json)
    {}

};