package me.nik.anticheatbase.checks.enums;

/**
 * A checktype enumerations class that we'll use on our checks
 */
public enum CheckType {
    AIM("Aim"),
    AUTOCLICKER("AutoClicker"),
    BADPACKETS("BadPackets"),
    FLY("Fly"),
    KILLAURA("KillAura"),
    SCAFFOLD("Scaffold"),
    SPEED("Speed"),
    MOTION("Motion"),
    NOFALL("NoFall"),
    JESUS("Jesus"),
    VEHICLE("Vehicle"),
    ELYTRA("Elytra"),
    TIMER("Timer"),
    OMNISPRINT("OmniSprint"),
    NOSLOW("NoSlow"),
    REACH("Reach"),
    VELOCITY("Velocity"),
    INVENTORY("Inventory"),
    INTERACT("Interact"),
    HITBOX("Hitbox");

    private final String checkName;

    CheckType(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckName() {
        return checkName;
    }
}