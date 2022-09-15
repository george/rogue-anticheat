#pragma once

#include <string>

template<typename T>
class Check {

protected:

    template<typename Base, typename U>
    auto checkInstance(const U *ptr) -> bool {
        return dynamic_cast<const Base*>(ptr) != nullptr;
    }

public:

    virtual auto handle(T event) -> void = 0;

    virtual auto getType() -> std::string = 0;

    virtual auto getName() -> std::string = 0;

};