package me.nik.anticheatbase.playerdata.processors.impl;

import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.playerdata.data.impl.RotationData;
import me.nik.anticheatbase.playerdata.processors.Processor;

/**
 * A simple cinematic processor to determine whether a player is using cinematic
 * <p>
 * NOTE: This is not a perfect way to handle cinematic, However i figured i should add this since
 * It's simple and effective.
 */
public class CinematicProcessor implements Processor {

    //This is the minimum rotation constant
    private static final double CINEMATIC_CONSTANT = .0078125F;

    private final Profile profile;
    private int lastCinematicTicks = 100, cinematicTicks;
    private boolean cinematic;

    public CinematicProcessor(Profile profile) {
        this.profile = profile;
    }

    public boolean isCinematic() {
        return cinematic;
    }

    @Override
    public void process() {

        //Update
        this.lastCinematicTicks = this.cinematic && this.cinematicTicks > 3 ? 0 : this.lastCinematicTicks + 1;

        RotationData data = profile.getRotationData();

        final float deltaYaw = data.getDeltaYaw();
        final float deltaPitch = data.getDeltaPitch();

        if (deltaYaw == 0F || deltaPitch == 0F || data.getRotationsAfterTeleport() < 3) return;

        final float yawAccel = data.getYawAccel();
        final float pitchAccel = data.getPitchAccel();

        SensitivityProcessor sensitivityProcessor = data.getSensitivityProcessor();

        final double constantYaw = sensitivityProcessor.getConstantYaw();
        final double constantPitch = sensitivityProcessor.getConstantPitch();

        final float delta = Math.abs(deltaYaw - deltaPitch);

        final boolean cinematic = yawAccel > .001F
                && yawAccel < 1F
                && pitchAccel > .001F
                && pitchAccel < 1F
                && delta < 3F
                && constantYaw < CINEMATIC_CONSTANT
                && constantPitch < CINEMATIC_CONSTANT;

        this.cinematicTicks = cinematic ? Math.min(5, this.cinematicTicks + 1) : Math.max(0, this.cinematicTicks - 1);

        this.cinematic = this.cinematicTicks > 1 || this.lastCinematicTicks < 80;
    }

    public int getLastCinematicTicks() {
        return lastCinematicTicks;
    }
}