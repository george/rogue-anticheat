#pragma once

#include "../tracker/impl/action_tracker.h"

class TrackerProvider {

public:

    virtual auto getActionTracker() -> ActionTracker* = 0;

};