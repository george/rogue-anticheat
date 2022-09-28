#pragma once

#include <string>

#include "../data/player_template.h"
#include "../violation/violation.h"

class Check {

    PlayerTemplate *data;

    int violations{};

public:

    virtual auto getType() -> std::string = 0;

    virtual auto getName() -> std::string = 0;

    explicit Check(PlayerTemplate *playerTemplate) {
        data = playerTemplate;
    }

    auto fail(std::vector<std::string> args) -> void {
        data->addViolation(Violation(getType(), getName(), &args, ++violations));
    }
};