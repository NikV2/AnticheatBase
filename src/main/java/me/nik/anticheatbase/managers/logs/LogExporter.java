package me.nik.anticheatbase.managers.logs;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.files.Config;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class LogExporter {

    protected static final long DELETE_DAYS = TimeUnit.DAYS.toMillis(Config.Setting.LOGS_CLEAR_DAYS.getInt());

    protected final Anticheat plugin;

    public LogExporter(Anticheat plugin) {
        this.plugin = plugin;
    }

    public abstract void initialize();

    public abstract void shutdown();

    public abstract void logMultiple(Collection<PlayerLog> logs);

    public abstract void log(PlayerLog log);

    public abstract List<PlayerLog> getLogs();

    public abstract List<PlayerLog> getLogsForPlayer(String player);
}