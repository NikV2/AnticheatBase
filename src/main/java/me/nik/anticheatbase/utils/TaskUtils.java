package me.nik.anticheatbase.utils;

import me.nik.anticheatbase.Anticheat;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * A small utility class that we can use in order to create and run tasks quickly
 */
public final class TaskUtils {

    private TaskUtils() {
    }

    public static BukkitTask taskTimer(Runnable runnable, long delay, long interval) {
        return Bukkit.getScheduler().runTaskTimer(Anticheat.getInstance(), runnable, delay, interval);
    }

    public static BukkitTask taskTimerAsync(Runnable runnable, long delay, long interval) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(Anticheat.getInstance(), runnable, delay, interval);
    }

    public static BukkitTask task(Runnable runnable) {
        return Bukkit.getScheduler().runTask(Anticheat.getInstance(), runnable);
    }

    public static BukkitTask taskAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(Anticheat.getInstance(), runnable);
    }

    public static BukkitTask taskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(Anticheat.getInstance(), runnable, delay);
    }

    public static BukkitTask taskLaterAsync(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(Anticheat.getInstance(), runnable, delay);
    }
}