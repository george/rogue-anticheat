package org.hostile.anticheat.check.annotation;

public @interface CheckMetadata {

    String type() default "";

    String name() default "";

}
