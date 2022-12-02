package org.hostile.anticheat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RotationEvent {

    private final float yaw;
    private final float pitch;

    private final float deltaYaw;
    private final float deltaPitch;

    private final float accelerationYaw;
    private final float accelerationPitch;

    private final double gcd;

    private final double sensitivityX;
    private final double sensitivityY;

}
