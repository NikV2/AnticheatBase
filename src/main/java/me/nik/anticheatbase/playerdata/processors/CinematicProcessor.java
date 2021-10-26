package me.nik.anticheatbase.playerdata.processors;

import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.playerdata.data.RotationData;
import me.nik.anticheatbase.tasks.TickTask;
import me.nik.anticheatbase.utils.MathUtils;

/**
 * A simple cinematic processor to determine whether a player is using cinematic
 * <p>
 * NOTE: This is not a perfect way to handle cinematic, However i figured i should add this since
 * It's simple and effective.
 */
public class CinematicProcessor {

    //This is the usual minimum constant when using cinematic
    private static final double CINEMATIC_CONSTANT = 7E-3;

    private final Profile profile;
    private int lastCinematicTicks, cinematicTicks;
    private boolean cinematic;

    public CinematicProcessor(Profile profile) {
        this.profile = profile;
    }

    public boolean isCinematic() {
        return this.cinematic;
    }

    public void handle() {

        final int currentTick = TickTask.getCurrentTick();

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

        if (this.cinematic && this.cinematicTicks > 3) this.lastCinematicTicks = currentTick;
    }

    public int getLastCinematicTicks() {
        return MathUtils.elapsedTicks(this.lastCinematicTicks);
    }
}