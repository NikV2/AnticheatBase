package me.nik.anticheatbase.utils.custom;

import me.nik.anticheatbase.utils.MathUtils;
import me.nik.anticheatbase.utils.ServerUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

/**
 * A simple effects class holding every information about the profile's effects
 * <p>
 * This is efficient since we're running one loop once and grabbing every effect we need.
 * <p>
 * We can also use this in order to get if they have had an effect in the last X milliseconds,
 * Which can be insanely useful.
 */
public class Effects {

    /*
    Potion Effects

    The reason we're doing this, and we're making a collection variable is due to certain spigot forks
    Converting the player.getActivePotionEffects() method into a hashmap, Which can cause errors while looping
     */
    private Collection<PotionEffect> potionEffects;

    //Speed
    private long lastSpeed;
    private int speedLevel;

    //Slowness
    private long lastSlowness;
    private int slownessLevel;

    //Jump
    private long lastJumpBoost;
    private int jumpBoostLevel;

    //SlowFalling
    private long lastSlowFalling;
    private int slowFallingLevel;

    //Levitation
    private long lastLevitation;
    private int levitationLevel;

    //Dolphin Grace
    private long lastDolphinGrace;
    private int dolphinGraceLevel;

    //Haste
    private long lastHaste;
    private int hasteLevel;

    public void handle(Player player) {

        final long currentTime = System.currentTimeMillis();

        for (PotionEffect effect : this.potionEffects = player.getActivePotionEffects()) {

            final PotionEffectType type = effect.getType();

            final int level = effect.getAmplifier() + 1;

            if (type.equals(PotionEffectType.SPEED)) {

                this.lastSpeed = currentTime;
                this.speedLevel = level;
            }

            if (type.equals(PotionEffectType.SLOW)) {

                this.lastSlowness = currentTime;
                this.slownessLevel = level;
            }

            if (type.equals(PotionEffectType.JUMP)) {

                this.lastJumpBoost = currentTime;
                this.jumpBoostLevel = level;
            }

            if (type.equals(PotionEffectType.FAST_DIGGING)) {

                this.lastHaste = currentTime;
                this.hasteLevel = level;
            }

            if (!ServerUtils.isLegacy() && type.equals(PotionEffectType.SLOW_FALLING)) {

                this.lastSlowFalling = currentTime;
                this.slowFallingLevel = level;
            }

            if (ServerUtils.isElytraUpdate() && type.equals(PotionEffectType.LEVITATION)) {

                this.lastLevitation = currentTime;
                this.levitationLevel = level;
            }

            if (!ServerUtils.isLegacy() && type.equals(PotionEffectType.DOLPHINS_GRACE)) {

                this.lastDolphinGrace = currentTime;
                this.dolphinGraceLevel = level;
            }
        }
    }

    public int getHasteLevel(long delta) {
        return MathUtils.elapsed(this.lastHaste) < delta ? this.hasteLevel : 0;
    }

    public int getSlownessLevel(long delta) {
        return MathUtils.elapsed(this.lastSlowness) < delta ? this.slownessLevel : 0;
    }

    public int getLevitationLevel(long delta) {
        return MathUtils.elapsed(this.lastLevitation) < delta ? this.levitationLevel : 0;
    }

    public int getSlowFallingLevel(long delta) {
        return MathUtils.elapsed(this.lastSlowFalling) < delta ? this.slowFallingLevel : 0;
    }

    public int getJumpBoostLevel(long delta) {
        return MathUtils.elapsed(this.lastJumpBoost) < delta ? this.jumpBoostLevel : 0;
    }

    public int getDolphinGraceLevel(long delta) {
        return MathUtils.elapsed(this.lastDolphinGrace) < delta ? this.dolphinGraceLevel : 0;
    }

    public int getSpeedLevel(long delta) {
        return MathUtils.elapsed(this.lastSpeed) < delta ? this.speedLevel : 0;
    }

    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }
}