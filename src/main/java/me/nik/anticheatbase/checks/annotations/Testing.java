package me.nik.anticheatbase.checks.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation can be used when making a new check, To only make this check get registered.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Testing {
}