package me.nik.anticheatbase.utils.custom;

/**
 * A effects enumeration class in order to grab the matching Effect Type
 * Based on the potion ID.
 */
public enum EffectType {
    UNKNOWN,
    SPEED,
    SLOWNESS,
    HASTE,
    MINING_FATIGUE,
    STRENGTH,
    INSTANT_HEALTH,
    INSTANT_DAMAGE,
    JUMP_BOOST,
    NAUSEA,
    REGENERATION,
    RESISTANCE,
    FIRE_RESISTANCE,
    WATER_BREATHING,
    INVISIBILITY,
    BLINDNESS,
    NIGHT_VISION,
    HUNGER,
    WEAKNESS,
    POISON,
    WITHER,
    HEALTH_BOOST,
    ABSORPTION,
    SATURATION,
    GLOWING,
    LEVITATION,
    LUCK,
    BAD_LUCK,
    SLOW_FALLING,
    CONDUIT_POWER,
    DOLPHINS_GRACE,
    BAD_OMEN,
    HERO_OF_THE_VILLAGE;

    /**
     * Get the effect type based on the id
     *
     * @param id The id
     * @return The matching effect type if present, Otherwise this method will return UNKNOWN.
     */
    public static EffectType fromID(int id) {
        return id >= EffectType.values().length ? UNKNOWN : EffectType.values()[id];
    }
}