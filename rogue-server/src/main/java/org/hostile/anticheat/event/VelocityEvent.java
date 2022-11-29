package org.hostile.anticheat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.location.Vector;

@Getter @AllArgsConstructor
public class VelocityEvent {

    private final CustomLocation previousLocation;
    private final CustomLocation currentLocation;

    private final Vector velocity;

}
