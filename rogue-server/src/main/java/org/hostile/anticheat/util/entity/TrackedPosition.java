package org.hostile.anticheat.util.entity;

import lombok.Getter;
import org.hostile.anticheat.location.Vector;

@Getter
public class TrackedPosition {

    private final long timestamp = System.currentTimeMillis();

    private final Vector location;

    public TrackedPosition(Vector location) {
        this.location = location;
    }
}
