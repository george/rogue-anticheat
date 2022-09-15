//
// Created by PC on 9/14/2022.
//

#include "rogue_app.h"

RogueConfig rogue_app::config = RogueConfig();

auto rogue_app::initialize() -> std::unique_ptr<crow::SimpleApp> {
    crow::SimpleApp app = crow::SimpleApp();

    CROW_ROUTE(app, "/")([](){
        return "Test";
    });

    return std::make_unique<crow::SimpleApp>(app);
}

auto rogue_app::getCheckData(const std::string &checkIdentifier) -> CheckData {
    return config.getCheckData(checkIdentifier);
}