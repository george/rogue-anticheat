#pragma once

#include <string>

#include "../data/player_template.h"
#include "../data/tracker_provider.h"
#include "../violation/violation.h"

class Check {

protected:

    PlayerTemplate *data;

    int violations{};
    double buffer{};

public:

    virtual auto getType() -> std::string = 0;

    virtual auto getName() -> std::string = 0;

    explicit Check(PlayerTemplate *playerTemplate) {
        data = playerTemplate;
    }

    auto fail(std::vector<std::string> args) -> void {
        data->addViolation(Violation(getType(), getName(), &args, ++violations));
    }

    auto incrementBuffer(double amount) -> double {
        return buffer += amount;
    }

    auto decrementBuffer(double amount) -> double {
        return buffer = std::max(0.0, buffer - amount);
    }
};