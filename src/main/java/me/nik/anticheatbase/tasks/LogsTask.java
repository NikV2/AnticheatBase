package me.nik.anticheatbase.tasks;

import me.nik.anticheatbase.Anticheat;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A task that we'll be using in order to process our logs.
 */
public class LogsTask extends BukkitRunnable {

    private final Anticheat plugin;

    public LogsTask(Anticheat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        if (this.plugin.getLogManager().isLogging() || this.plugin.getLogManager().getLogsQueue().isEmpty()) return;

        this.plugin.getLogManager().getLogExporter().logMultiple(this.plugin.getLogManager().getLogsQueue());

        this.plugin.getLogManager().clearQueuedLogs();

        this.plugin.getLogManager().setLogging(false);
    }
}