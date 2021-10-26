package me.nik.anticheatbase.manager.impl;

import me.nik.anticheatbase.manager.Manager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An alert manager class holding information about players with alerts
 */
public class AlertManager extends Manager<UUID> implements Listener {

    private final ExecutorService alertExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void init() {
    }

    public void addPlayerToAlerts(UUID uuid) {
        add(uuid);
    }

    public void removePlayerFromAlerts(UUID uuid) {
        remove(uuid);
    }

    public boolean hasAlerts(UUID uuid) {
        return contains(uuid);
    }

    //Make sure we don't get a memory leak
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        removePlayerFromAlerts(event.getPlayer().getUniqueId());
    }

    @Override
    public void shutdown() {
        clear();
    }

    public ExecutorService getAlertExecutor() {
        return alertExecutor;
    }
}