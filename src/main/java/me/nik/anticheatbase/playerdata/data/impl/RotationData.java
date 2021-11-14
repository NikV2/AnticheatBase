package me.nik.anticheatbase.playerdata.data.impl;

import com.comphenix.protocol.events.PacketContainer;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.playerdata.data.Data;
import me.nik.anticheatbase.playerdata.processors.impl.CinematicProcessor;
import me.nik.anticheatbase.playerdata.processors.impl.SensitivityProcessor;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.wrappers.WrapperPlayClientLook;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPositionLook;

/**
 * A rotation data class holding every essential information we need when it comes to aim related data.
 */
public class RotationData implements Data {

    private final Profile profile;

    private final SensitivityProcessor sensitivityProcessor;
    private final CinematicProcessor cinematicProcessor;

    private float yaw, lastYaw, pitch, lastPitch, deltaYaw, lastDeltaYaw,
            deltaPitch, lastDeltaPitch, yawAccel, lastYawAccel, pitchAccel, lastPitchAccel;

    public RotationData(Profile profile) {
        this.profile = profile;
        this.sensitivityProcessor = new SensitivityProcessor(profile);
        this.cinematicProcessor = new CinematicProcessor(profile);
    }

    @Override
    public void process(Packet packet) {

        final PacketContainer container = packet.getPacket();

        if (packet.isPositionLook()) {

            final WrapperPlayClientPositionLook movePosLook = new WrapperPlayClientPositionLook(container);

            final float yawPosLook = movePosLook.getYaw();
            final float pitchPosLook = movePosLook.getPitch();

            processRotation(yawPosLook, pitchPosLook);

        } else if (packet.isLook()) {

            final WrapperPlayClientLook moveLook = new WrapperPlayClientLook(container);

            final float yawLook = moveLook.getYaw();
            final float pitchLook = moveLook.getPitch();

            processRotation(yawLook, pitchLook);

        }
    }

    private void processRotation(float yaw, float pitch) {

        final float lastYaw = this.yaw;

        this.lastYaw = lastYaw;
        this.yaw = yaw;

        final float lastPitch = this.pitch;

        this.lastPitch = lastPitch;
        this.pitch = pitch;

        final float lastDeltaYaw = this.deltaYaw;
        final float deltaYaw = Math.abs(yaw - lastYaw) % 360F;

        this.lastDeltaYaw = lastDeltaYaw;
        this.deltaYaw = deltaYaw;

        final float lastDeltaPitch = this.deltaPitch;
        final float deltaPitch = Math.abs(pitch - lastPitch);

        this.lastDeltaPitch = lastDeltaPitch;
        this.deltaPitch = deltaPitch;

        final float lastYawAccel = this.yawAccel;
        final float yawAccel = Math.abs(deltaYaw - lastDeltaYaw);

        this.lastYawAccel = lastYawAccel;
        this.yawAccel = yawAccel;

        final float lastPitchAccel = this.pitchAccel;
        final float pitchAccel = Math.abs(deltaPitch - lastDeltaPitch);

        this.lastPitchAccel = lastPitchAccel;
        this.pitchAccel = pitchAccel;

        this.sensitivityProcessor.process();

        this.cinematicProcessor.process();
    }

    public SensitivityProcessor getSensitivityProcessor() {
        return sensitivityProcessor;
    }

    public CinematicProcessor getCinematicProcessor() {
        return cinematicProcessor;
    }

    public float getYaw() {
        return yaw;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getLastPitch() {
        return lastPitch;
    }

    public float getDeltaYaw() {
        return deltaYaw;
    }

    public float getLastDeltaYaw() {
        return lastDeltaYaw;
    }

    public float getDeltaPitch() {
        return deltaPitch;
    }

    public float getLastDeltaPitch() {
        return lastDeltaPitch;
    }

    public float getYawAccel() {
        return yawAccel;
    }

    public float getLastYawAccel() {
        return lastYawAccel;
    }

    public float getPitchAccel() {
        return pitchAccel;
    }

    public float getLastPitchAccel() {
        return lastPitchAccel;
    }
}