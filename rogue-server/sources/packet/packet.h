#pragma once

#include <nlohmann/json.hpp>

using nlohmann::json;

class Packet {

public:

    virtual ~Packet() = default;

};