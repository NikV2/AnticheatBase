package me.nik.anticheatbase.playerdata.processors.impl;

import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.playerdata.data.impl.RotationData;
import me.nik.anticheatbase.playerdata.processors.Processor;
import me.nik.anticheatbase.utils.MathUtils;

/**
 * A simple cinematic processor to determine whether a player is using cinematic
 * <p>
 * NOTE: This is not a perfect way to handle cinematic, However i figured i should add this since
 * It's simple and effective.
 */
public class CinematicProcessor implements Processor {

    //This is the minimum rotation constant
    private static final double CINEMATIC_CONSTANT = 7.8125E-3;

    private final Profile profile;
    private int lastCinematicTicks, cinematicTicks;
    private boolean cinematic;

    public CinematicProcessor(Profile profile) {
        this.profile = profile;
    }

    public boolean isCinematic() {
        return this.cinematic;
    }

    @Override
    public void process() {

        //Update
        this.lastCinematicTicks = this.cinematic && this.cinematicTicks > 3 ? 0 : this.lastCinematicTicks + 1;

        RotationData data = profile.getRotationData();

        //Fixes exploits
        if (data.getDeltaPitch() == 0F || data.getDeltaYaw() == 0F) return;

        final float yawAccel = data.getYawAccel();

        final float pitchAccel = data.getPitchAccel();

        final boolean invalid = MathUtils.isScientificNotation(yawAccel)
                || yawAccel == 0F
                || MathUtils.isScientificNotation(pitchAccel)
                || pitchAccel == 0F;

        SensitivityProcessor sensitivityProcessor = data.getSensitivityProcessor();

        final double constantYaw = sensitivityProcessor.getConstantYaw();

        final double constantPitch = sensitivityProcessor.getConstantPitch();

        final boolean cinematic = !invalid && yawAccel < 1F && pitchAccel < 1F;

        if (cinematic) {

            if (constantYaw < CINEMATIC_CONSTANT && constantPitch < CINEMATIC_CONSTANT) this.cinematicTicks++;

        } else this.cinematicTicks -= this.cinematicTicks > 0 ? 1 : 0;

        //Make sure we're not getting exploited
        this.cinematicTicks -= this.cinematicTicks > 5 ? 1 : 0;

        this.cinematic = this.cinematicTicks > 2 || getLastCinematicTicks() < 80;
    }

    public int getLastCinematicTicks() {
        return this.lastCinematicTicks;
    }
}