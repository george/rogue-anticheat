#include "rogue_app.h"

RogueConfig rogue_app::config = RogueConfig();

auto rogue_app::initialize() -> std::unique_ptr<crow::SimpleApp> {
    crow::SimpleApp app = crow::SimpleApp();

    CROW_ROUTE(app, "/")([](){
        return crow::response("Hello, world!");
    });
    
    return std::make_unique<crow::SimpleApp>(app);
}

auto rogue_app::getCheckData(const std::string &checkIdentifier) -> CheckData {
    return config.getCheckData(checkIdentifier);
}

template<typename Base, typename U>
auto checkInstance(const U *ptr) -> bool {
    return dynamic_cast<const Base*>(ptr) != nullptr;
}