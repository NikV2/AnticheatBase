package me.nik.anticheatbase.managers.logs;

import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.managers.logs.impl.FileExporter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogManager {

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

    public void disInit() {
        this.logsQueue.clear();
        this.logExporter.disInit();
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
}