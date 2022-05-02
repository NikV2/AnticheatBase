package me.nik.anticheatbase.utils;

import me.nik.anticheatbase.managers.profile.Profile;

/**
 * A simple movement utility class
 * NOTE: This is not perfect, It's made in case you want to make simple
 * Limit checks, There's lots of things that are wrong in here.
 */
public final class MoveUtils {

    //---------------------------------------------------------------------------------------
    public static final float MAXIMUM_PITCH = 90.0F;
    //---------------------------------------------------------------------------------------
    public static final float FRICTION = .91F;
    public static final float FRICTION_FACTOR = .6F;
    public static final double WATER_FRICTION = .800000011920929D;
    public static final double MOTION_Y_FRICTION = .9800000190734863D;
    public static final double JUMP_MOTION = .41999998688697815D;
    public static final double LAND_GROUND_MOTION = -.07840000152587834D;
    public static final float JUMP_MOVEMENT_FACTOR = 0.026F;
    //---------------------------------------------------------------------------------------
    /**
     * Assuming they're moving forward and no acceleration is applied.
     */
    public static final float BASE_AIR_SPEED = .3565F;
    /**
     * Assuming they're moving sideways
     */
    public static final float BASE_GROUND_SPEED = .2867F;
    //---------------------------------------------------------------------------------------
    /**
     * 1.9+ Clients last tick motion before it resets to 0 due to .003D
     */
    public static final double RESET_MOTION = .003016261509046103D;
    //---------------------------------------------------------------------------------------

    private MoveUtils() {
    }

    public static float getBaseAirSpeed(final Profile profile) {

        //Your own method here
        return 0F;
    }

    public static float getBaseGroundSpeed(final Profile profile) {

        //Your own method here
        return 0F;
    }

    public static float getCustomSpeed(final Profile profile) {

        //Your own method here
        return 0F;
    }
}