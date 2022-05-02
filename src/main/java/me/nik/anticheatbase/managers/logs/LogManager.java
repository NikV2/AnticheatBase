package me.nik.anticheatbase.managers.logs;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.managers.Initializer;
import me.nik.anticheatbase.managers.logs.impl.FileExporter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogManager implements Initializer {

    private final Queue<PlayerLog> logsQueue = new ConcurrentLinkedQueue<>();

    private final LogExporter logExporter;

    private boolean logging;

    public LogManager(Anticheat plugin) {

        switch (Config.Setting.LOGS_TYPE.getString().toLowerCase()) {

            /*case "mysql":

                this.logExporter = new MySQLExporter(plugin);

                break;

            case "sqlite":

                this.logExporter = new SQLiteExporter(plugin);

                break;*/

            default:

                this.logExporter = new FileExporter(plugin);

                break;
        }
    }

    @Override
    public void initialize() {
        this.logExporter.initialize();
    }

    public Queue<PlayerLog> getLogsQueue() {
        return this.logsQueue;
    }

    public void addLogToQueue(PlayerLog playerLog) {

        if (!Config.Setting.LOGS_ENABLED.getBoolean()) return;

        this.logsQueue.add(playerLog);
    }

    public void clearQueuedLogs() {
        this.logsQueue.clear();
    }

    public LogExporter getLogExporter() {
        return this.logExporter;
    }

    public boolean isLogging() {
        return this.logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    @Override
    public void shutdown() {
        this.logsQueue.clear();
        this.logExporter.shutdown();
    }
}