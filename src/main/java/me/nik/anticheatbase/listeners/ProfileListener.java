package me.nik.anticheatbase.listeners;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.enums.Permissions;
import me.nik.anticheatbase.files.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A profile listener that we'll use in order to initialize our player profile.
 */
public class ProfileListener implements Listener {

    private final Anticheat plugin;

    public ProfileListener(Anticheat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {

        final Player player = e.getPlayer();

        this.plugin.getProfileManager().createProfile(player);

        if (Config.Setting.TOGGLE_ALERTS_ON_JOIN.getBoolean() && player.hasPermission(Permissions.COMMAND_ALERTS.getPermission())) {

            this.plugin.getAlertManager().addPlayerToAlerts(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent e) {
        this.plugin.getProfileManager().removeProfile(e.getPlayer());
    }
}