package me.nik.anticheatbase;

import com.comphenix.protocol.ProtocolLibrary;
import me.nik.anticheatbase.commands.CommandManager;
import me.nik.anticheatbase.files.Checks;
import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.files.Lang;
import me.nik.anticheatbase.files.commentedfiles.CommentedFileConfiguration;
import me.nik.anticheatbase.listeners.ClientListener;
import me.nik.anticheatbase.listeners.ProfileListener;
import me.nik.anticheatbase.listeners.ViolationListener;
import me.nik.anticheatbase.managers.AlertManager;
import me.nik.anticheatbase.managers.CheckManager;
import me.nik.anticheatbase.managers.logs.LogManager;
import me.nik.anticheatbase.managers.nms.NmsManager;
import me.nik.anticheatbase.managers.threads.ThreadManager;
import me.nik.anticheatbase.playerdata.ProfileManager;
import me.nik.anticheatbase.processors.listeners.BukkitListener;
import me.nik.anticheatbase.processors.listeners.NetworkListener;
import me.nik.anticheatbase.tasks.LogsTask;
import me.nik.anticheatbase.tasks.TickTask;
import me.nik.anticheatbase.tasks.ViolationTask;
import me.nik.anticheatbase.utils.ReflectionUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A simple and very efficient anticheat base
 *
 * @author Nik
 */
public class Anticheat extends JavaPlugin {

    private static Anticheat instance;

    private Config configuration;
    private Checks checks;
    private Lang lang;

    private ProfileManager profileManager;
    private LogManager logManager;
    private ThreadManager threadManager;
    private AlertManager alertManager;
    private CheckManager checkManager;
    private NmsManager nmsManager;

    @Override
    public void onEnable() {

        instance = this;

        //Files
        loadFiles();

        //General
        load();

        //Tasks
        loadTasks();

        //Listeners
        loadListeners();

        //Load Commands
        getCommand("anticheat").setExecutor(new CommandManager(this));
    }

    private void loadFiles() {

        //Config
        this.configuration = new Config(this);
        this.configuration.setup();

        //Checks
        this.checks = new Checks(this);
        this.checks.setup();

        //Language
        this.lang = new Lang(this);
        this.lang.setup();
    }

    private void load() {

        this.profileManager = new ProfileManager();
        this.checkManager = new CheckManager();
        this.logManager = new LogManager(this);
        this.threadManager = new ThreadManager(this);
        this.alertManager = new AlertManager(this);
        this.nmsManager = new NmsManager();
    }

    private void loadTasks() {

        new TickTask(this).runTaskTimerAsynchronously(this, 50L, 0L);

        if (Config.Setting.LOGS_ENABLED.getBoolean()) {

            new LogsTask(this).runTaskTimerAsynchronously(this, 1200L, 320L);
        }

        final long violationInterval = Config.Setting.CHECK_SETTINGS_VIOLATION_RESET_INTERVAL.getLong() * 1200;

        new ViolationTask(this).runTaskTimerAsynchronously(this, violationInterval, violationInterval);
    }

    private void loadListeners() {

        final PluginManager pm = getServer().getPluginManager();

        //Bukkit Events
        pm.registerEvents(new ProfileListener(this), this);
        pm.registerEvents(new ClientListener(this), this);
        pm.registerEvents(new ViolationListener(this), this);

        //Packet Listener
        ProtocolLibrary.getProtocolManager().addPacketListener(new NetworkListener(this));

        //Bukkit Listener
        pm.registerEvents(new BukkitListener(this), this);
    }

    @Override
    public void onDisable() {

        //Files
        this.configuration.reset();
        this.checks.reset();

        //Disinitialize
        this.profileManager.disInit();
        this.checkManager.disInit();
        this.alertManager.disInit();
        this.threadManager.disInit();

        //Clear reflection cache
        ReflectionUtils.clear();

        instance = null;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public AlertManager getAlertManager() {
        return alertManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public LogManager getLogManager() {
        return logManager;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public NmsManager getNmsManager() {
        return nmsManager;
    }

    public CommentedFileConfiguration getConfiguration() {
        return this.configuration.getConfig();
    }

    public CommentedFileConfiguration getChecks() {
        return this.checks.getConfig();
    }

    public FileConfiguration getLang() {
        return this.lang.get();
    }

    public static Anticheat getInstance() {
        return instance;
    }
}