package me.nik.anticheatbase.checks.types;

import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.playerdata.Profile;
import org.bukkit.event.Event;

/*
 * Abstract class for Bukkit Event Checks
 */
public abstract class BukkitCheck extends Check {

    public BukkitCheck(Profile profile, CheckType check, String type, String description) {
        super(profile, check, type, description);
    }

    public BukkitCheck(Profile profile, CheckType check, String description) {
        super(profile, check, "", description);
    }

    public abstract void handle(Event event);
}