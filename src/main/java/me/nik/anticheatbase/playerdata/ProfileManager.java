package me.nik.anticheatbase.playerdata;

import me.nik.anticheatbase.manager.Initializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A profile manager class that we'll use in order to create or get a player profile
 */
public class ProfileManager implements Initializer {

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    @Override
    public void init() {

        //Plugman
        Bukkit.getOnlinePlayers().forEach(this::createProfile);
    }

    public void createProfile(Player player) {
        this.profiles.put(player.getUniqueId(), new Profile(player));
    }

    public void removeProfile(Player player) {

        UUID uuid = player.getUniqueId();

        if (!this.profiles.containsKey(uuid)) return;

        this.profiles.remove(uuid);
    }

    public Profile getProfile(Player player) {
        return this.profiles.get(player.getUniqueId());
    }

    public Map<UUID, Profile> getProfileMap() {
        return this.profiles;
    }

    @Override
    public void shutdown() {
        this.profiles.clear();
    }
}