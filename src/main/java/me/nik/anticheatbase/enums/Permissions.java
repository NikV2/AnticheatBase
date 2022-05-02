package me.nik.anticheatbase.enums;

/**
 * A permissions enumerations class in order to cache our permissions and easily grab them
 */
public enum Permissions {
    ADMIN("anticheat.admin"),
    BYPASS("anticheat.bypass"),
    COMMAND_ALERTS("anticheat.commands.alerts");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}