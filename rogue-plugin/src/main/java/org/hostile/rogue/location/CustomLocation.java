package org.hostile.rogue.location;

import lombok.Getter;
import org.hostile.rogue.util.minecraft.AxisAlignedBB;

@Getter
public class CustomLocation {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final boolean onGround;

    public CustomLocation(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public AxisAlignedBB toBoundingBox(boolean sneaking) {
        return new AxisAlignedBB(
                x - 0.3, y, z - 0.3,
                x + 0.3, y + (sneaking ? 1.62F : 1.8F), z + 0.3
        );
    }
}
