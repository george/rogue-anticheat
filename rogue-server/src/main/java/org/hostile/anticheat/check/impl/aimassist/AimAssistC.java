package org.hostile.anticheat.check.impl.aimassist;

import org.hostile.anticheat.check.annotation.CheckMetadata;
import org.hostile.anticheat.check.type.impl.rotation.AttackCheck;
import org.hostile.anticheat.data.PlayerData;
import org.hostile.anticheat.event.RotationEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

/**
 * Checks for abnormal aim rounding
 */
@CheckMetadata(type = "AimAssist", name = "C")
public class AimAssistC extends AttackCheck {

    private final Predicate<Float> roundingCheckFunction = (a) -> a % 1.0 == 0;

    private final Queue<Float> pitchDeltas = new ConcurrentLinkedQueue<>();
    private final Queue<Float> yawDeltas = new ConcurrentLinkedQueue<>();

    public AimAssistC(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handleRotation(RotationEvent event) {
        debug("size", this.pitchDeltas.size());

        float deltaPitch = event.getDeltaPitch();
        float deltaYaw = event.getDeltaYaw();

        pitchDeltas.add(deltaPitch);
        yawDeltas.add(deltaYaw);

        if (pitchDeltas.size() >= 50) {
            long roundedPitchDeltas = pitchDeltas.stream().filter(roundingCheckFunction).count();
            long roundedYawDeltas = yawDeltas.stream().filter(roundingCheckFunction).count();

            fail("pitchRatio", 50 / roundedPitchDeltas, "yawRatio", 50 / roundedYawDeltas);

            pitchDeltas.clear();
            yawDeltas.clear();
        }
    }
}
