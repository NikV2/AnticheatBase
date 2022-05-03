package me.nik.anticheatbase.checks.types;

import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.processors.Packet;

/*
 * Abstract class for Checks
 */
public abstract class Check extends AbstractCheck {

    public Check(Profile profile, CheckType check, String type, String description) {
        super(profile, check, type, description);
    }

    public Check(Profile profile, CheckType check, String description) {
        super(profile, check, "", description);
    }

    public abstract void handle(Packet packet);
}