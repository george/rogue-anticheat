cmake_minimum_required(VERSION 3.17)
project(rogue-server)
set(CMAKE_CXX_STANDARD 14)

include(FetchContent)

FetchContent_Declare(asio GIT_REPOSITORY https://github.com/chriskohlhoff/asio)
FetchContent_Declare(crow GIT_REPOSITORY https://github.com/CrowCpp/Crow)
FetchContent_Declare(json URL https://github.com/nlohmann/json/releases/download/v3.10.5/json.tar.xz)
FetchContent_MakeAvailable(json)

if (NOT asio_POPULATED)
    FetchContent_Populate(asio)
    include_directories(${PROJECT_NAME} ${asio_SOURCE_DIR} ${asio_BINARY_DIR})
endif()

SET(ASIO_INCLUDE_DIR ${asio_SOURCE_DIR}/asio/include)

if (NOT crow_POPULATED)
    FetchContent_Populate(crow)
    add_subdirectory(${crow_SOURCE_DIR})
endif()

file(GLOB_RECURSE SRCS sources/*.cpp sources/*.cc)
file(GLOB_RECURSE HDRS sources/*.h sources/*.hpp)

add_executable(${PROJECT_NAME} ${SRCS} ${HDRS})

target_link_libraries(${PROJECT_NAME} PUBLIC Crow::Crow ${ASIO_LIBRARIES} nlohmann_json::nlohmann_json)

if (NOT WIN32)
    set(CMAKE_CXX_STANDARD_LIBRARIES " ${CMAKE_CXX_STANDARD_LIBRARIES}")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl, -Wl")
else()
    set(CMAKE_CXX_STANDARD_LIBRARIES "-static-libgcc -static-libstdc++ -lwsock32 -lws2_32 -lcrypt32 -ldnsapi ${CMAKE_CXX_STANDARD_LIBRARIES}")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl,-Bstatic,--whole-archive -lwinpthread -Wl,--no-whole-archive")
endif()