package me.nik.anticheatbase.manager.impl.logs;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.manager.Initializer;
import me.nik.anticheatbase.manager.impl.logs.impl.FileExporter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogManager implements Initializer {

    private final Queue<PlayerLog> logsQueue = new ConcurrentLinkedQueue<>();

    private LogExporter logExporter;

    private boolean logging;

    @Override
    public void init() {

        switch (Config.Setting.LOGS_TYPE.getString().toLowerCase()) {

            /*case "mysql":

                this.logExporter = new MySQLExporter(plugin);

                break;

            case "sqlite":

                this.logExporter = new SQLiteExporter(plugin);

                break;*/

            default:

                this.logExporter = new FileExporter(Anticheat.getInstance());

                break;
        }

        this.logExporter.init();
    }

    public void addLogToQueue(PlayerLog playerLog) {
        if (Config.Setting.LOGS_ENABLED.getBoolean()) {
            this.logsQueue.add(playerLog);
        }
    }

    public void clearQueuedLogs() {
        this.logsQueue.clear();
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public boolean isLogging() {
        return this.logging;
    }

    public LogExporter getLogExporter() {
        return this.logExporter;
    }

    public Queue<PlayerLog> getLogsQueue() {
        return this.logsQueue;
    }

    @Override
    public void shutdown() {
        this.logsQueue.clear();
        this.logExporter.disInit();
    }
}