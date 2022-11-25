package org.hostile.anticheat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.util.MathUtil;

@Getter @AllArgsConstructor
public class PositionUpdateEvent {

    private final CustomLocation previousLocation;
    private final CustomLocation currentLocation;

    private final double offsetXZ;
    private final double offsetY;

    public PositionUpdateEvent(CustomLocation previousLocation, CustomLocation currentLocation) {
        this.previousLocation = previousLocation;
        this.currentLocation = currentLocation;

        this.offsetXZ = MathUtil.distance(previousLocation, currentLocation);
        this.offsetY = Math.abs(previousLocation.getY() - currentLocation.getY());
    }
}
