package me.nik.anticheatbase.utils.versionutils;

import me.nik.anticheatbase.utils.versionutils.impl.ProtocolLib;
import me.nik.anticheatbase.utils.versionutils.impl.ProtocolSupport;
import me.nik.anticheatbase.utils.versionutils.impl.ViaVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public final class VersionUtils {

    private static final VersionInstance VERSION_INSTANCE;

    static {

        PluginManager pm = Bukkit.getPluginManager();

        if (pm.isPluginEnabled("ViaVersion")) {

            VERSION_INSTANCE = new ViaVersion();

        } else if (pm.isPluginEnabled("ProtocolSupport")) {

            VERSION_INSTANCE = new ProtocolSupport();

        } else VERSION_INSTANCE = new ProtocolLib();
    }

    private VersionUtils() {
    }

    public static ClientVersion getClientVersion(final Player player) {
        return VERSION_INSTANCE.getClientVersion(player);
    }
}