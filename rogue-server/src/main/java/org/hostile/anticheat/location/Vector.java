package org.hostile.anticheat.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hostile.anticheat.util.minecraft.Vec3;

@Getter @Setter
@AllArgsConstructor
public class Vector {

    private double posX;
    private double posY;
    private double posZ;

    public Vector clone() {
        return new Vector(this.posX, this.posY, this.posZ);
    }

    public Vector add(Vector vector) {
        this.posX += vector.getPosX();
        this.posY += vector.getPosY();
        this.posZ += vector.getPosZ();

        return this;
    }

    public Vec3 toVec3() {
        return new Vec3(posX, posY, posZ);
    }
}
