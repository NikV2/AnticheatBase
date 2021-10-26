package me.nik.anticheatbase.manager.impl.logs;

import me.nik.anticheatbase.files.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class LogExporter {

    protected final long DELETE_DAYS;
    protected final JavaPlugin plugin;

    public LogExporter(JavaPlugin plugin) {
        this.plugin = plugin;
        this.DELETE_DAYS = TimeUnit.DAYS.toMillis(Config.Setting.LOGS_CLEAR_DAYS.getInt());
    }

    public abstract void init();

    public abstract void disInit();

    public abstract void logMultiple(Collection<PlayerLog> logs);

    public abstract void log(PlayerLog log);

    public abstract List<PlayerLog> getLogs();

    public abstract List<PlayerLog> getLogsForPlayer(String player);
}