package me.nik.anticheatbase.utils.versionutils.impl;

import me.nik.anticheatbase.utils.versionutils.ClientVersion;
import me.nik.anticheatbase.utils.versionutils.VersionInstance;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

public class ViaVersion implements VersionInstance {
    @Override
    public ClientVersion getClientVersion(Player player) {
        return ClientVersion.getClientVersion(Via.getAPI().getPlayerVersion(player));
    }
}