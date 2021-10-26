package me.nik.anticheatbase.checks.types;

import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.processors.Packet;

/*
 * Abstract class for Packet Checks
 */
public abstract class PacketCheck extends Check {

    public PacketCheck(Profile profile, CheckType check, String type, String description) {
        super(profile, check, type, description);
    }

    public PacketCheck(Profile profile, CheckType check, String description) {
        super(profile, check, "", description);
    }

    public abstract void handle(Packet packet);
}