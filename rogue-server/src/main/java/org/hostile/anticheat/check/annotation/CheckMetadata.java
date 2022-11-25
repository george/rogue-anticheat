package org.hostile.anticheat.check.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckMetadata {

    String type() default "";

    String name() default "";

}
