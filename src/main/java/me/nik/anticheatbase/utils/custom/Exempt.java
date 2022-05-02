package me.nik.anticheatbase.utils.custom;

import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.playerdata.data.impl.MovementData;

/**
 * A simple class that we'll be using for exempting some checks, We'll cache the booleans every tick to
 * Save up some perfomance except for the ones that get updated by the server.
 * <p>
 * This is similar to Elevated's Exempt method however instead of using Predicates
 * We're caching the booleans as soon as we receive a packet for maximum perfomance.
 * <p>
 * This is a LOT faster especially when having a lot of checks, Using cached booleans instead of
 * Checking for example (player.getAllowFlight()) every single tick on every check.
 */
public class Exempt {

    private final Profile profile;

    public Exempt(Profile profile) {
        this.profile = profile;
    }

    private boolean movement, velocity, jesus, elytra, vehicle, autoclicker, aim;

    public void handleExempts(long timeStamp) {

        MovementData movementData = profile.getMovementData();

        //Example
        this.movement = movementData.getDeltaXZ() == 0D && movementData.getDeltaY() == 0D;
    }

    public boolean movement() {
        return this.movement;
    }

    public boolean velocity() {
        return this.velocity;
    }

    public boolean jesus() {
        return this.jesus;
    }

    public boolean autoclicker() {
        return this.autoclicker;
    }

    public boolean aim() {
        return this.aim;
    }

    public boolean elytra() {
        return this.elytra;
    }

    public boolean vehicle() {
        return this.vehicle;
    }
}