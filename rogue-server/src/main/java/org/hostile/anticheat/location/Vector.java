package org.hostile.anticheat.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hostile.anticheat.util.minecraft.Vec3;

@Getter @Setter
@AllArgsConstructor
public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector clone() {
        return new Vector(this.x, this.y, this.z);
    }

    public Vector add(Vector vector) {
        this.x += vector.getX();
        this.y += vector.getY();
        this.z += vector.getZ();

        return this;
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }
}
