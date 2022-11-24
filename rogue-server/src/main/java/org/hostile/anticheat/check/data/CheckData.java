package org.hostile.anticheat.check.data;

import com.google.gson.JsonObject;
import lombok.Getter;

@Getter
public class CheckData {

    private final String checkType;
    private final String checkName;

    private final String action;
    private final int maxViolations;

    public CheckData(JsonObject object) {
        this.checkType = object.get("check_type").getAsString();
        this.checkName = object.get("check_name").getAsString();

        this.action = object.get("action").getAsString();
        this.maxViolations = object.get("max_violations").getAsInt();
    }
}
