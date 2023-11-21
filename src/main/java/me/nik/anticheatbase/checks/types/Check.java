package me.nik.anticheatbase.checks.types;

import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.processors.Packet;

/*
 * Abstract class for Checks
 */
public abstract class Check extends AbstractCheck {

    public Check(Profile profile, CheckName name, CheckType type, String description) {
        super(profile, name, type, description);
    }

    public Check(Profile profile, CheckName name, CheckType type, String letter, String description) {
        super(profile, name, type, "", description);
    }

    public abstract void handle(Packet packet);
}
