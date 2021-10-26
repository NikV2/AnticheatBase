package me.nik.anticheatbase.manager.impl.logs;

import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.manager.impl.logs.impl.FileExporter;
import me.nik.anticheatbase.utils.Initializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogManager implements Initializer {

    private final Queue<PlayerLog> logsQueue = new ConcurrentLinkedQueue<>();

    private final LogExporter logExporter;

    private boolean logging;

    public LogManager(JavaPlugin plugin) {

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

        this.logExporter.init();
    }

    @Override
    public void init() {

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