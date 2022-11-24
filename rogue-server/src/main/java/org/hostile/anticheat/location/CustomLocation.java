package org.hostile.anticheat.location;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CustomLocation {

    private final double x;
    private final double y;
    private final double z;

    private final float yaw;
    private final float pitch;

    private final boolean onGround;

}
