package org.hostile.anticheat.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter @AllArgsConstructor
public enum PotionEffectType {

    SPEED(1),
    SLOWNESS(2),
    JUMP_BOOST(8);

    private final int potionId;

    public static PotionEffectType of(int potionId) {
        return Arrays.stream(values())
                .filter(potionEffectType -> potionEffectType.getPotionId() == potionId)
                .findFirst()
                .orElse(null);
    }
}
