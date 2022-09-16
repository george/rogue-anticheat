#pragma once

#include <string>

class Check {
    
public:

    virtual auto getType() -> std::string = 0;

    virtual auto getName() -> std::string = 0;

};