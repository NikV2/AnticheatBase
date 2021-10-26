package me.nik.anticheatbase.utils;

import org.bukkit.Bukkit;

/**
 * Ugly and terrible, can be done better.
 *
 * You may replace it with your own if you're not lazy like me and code this for the sake of having it.
 */
public final class ServerUtils {

    private ServerUtils(){}

    private static final String VERSION = Bukkit.getVersion();

    private static final boolean LEGACY = areVersions(
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12"
    );

    private static final boolean NETHER_UPDATE = !areVersions(
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12",
            "1.13",
            "1.14",
            "1.15"
    );

    private static final boolean ELYTRA_UPDATE = !isVersion("1.8");

    private static final boolean CAVES_UPDATE = !areVersions(
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12",
            "1.13",
            "1.14",
            "1.15",
            "1.16"
    );

    public static boolean isVersion(final String version) {
        return VERSION.contains(version);
    }

    public static boolean areVersions(final String... versions) {

        for (String version : versions) {

            if (VERSION.contains(version)) return true;
        }

        return false;
    }

    public static boolean isLegacy() {
        return LEGACY;
    }

    public static boolean isNetherUpdate() {
        return NETHER_UPDATE;
    }

    public static boolean isElytraUpdate() {
        return ELYTRA_UPDATE;
    }

    public static boolean isCavesUpdate() {
        return CAVES_UPDATE;
    }
}