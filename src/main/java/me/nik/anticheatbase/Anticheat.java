package me.nik.anticheatbase;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import me.nik.anticheatbase.commands.CommandManager;
import me.nik.anticheatbase.files.Checks;
import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.files.Lang;
import me.nik.anticheatbase.files.commentedfiles.CommentedFileConfiguration;
import me.nik.anticheatbase.listeners.ClientListener;
import me.nik.anticheatbase.listeners.ProfileListener;
import me.nik.anticheatbase.listeners.ViolationListener;
import me.nik.anticheatbase.manager.impl.AlertManager;
import me.nik.anticheatbase.manager.impl.CheckManager;
import me.nik.anticheatbase.manager.impl.logs.LogManager;
import me.nik.anticheatbase.manager.impl.nms.NmsManager;
import me.nik.anticheatbase.manager.impl.threads.ThreadManager;
import me.nik.anticheatbase.playerdata.ProfileManager;
import me.nik.anticheatbase.processors.listeners.BukkitListener;
import me.nik.anticheatbase.processors.listeners.NetworkListener;
import me.nik.anticheatbase.tasks.LogsTask;
import me.nik.anticheatbase.tasks.TickTask;
import me.nik.anticheatbase.tasks.ViolationTask;
import me.nik.anticheatbase.utils.ReflectionUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * A simple and very efficient anticheat base
 *
 * @author Nik
 */
@Getter
public class Anticheat extends JavaPlugin {

    private static Anticheat instance;

    private final Config configuration = new Config(this);
    private final Checks checks = new Checks(this);
    private final Lang lang = new Lang(this);

    private final ProfileManager profileManager = new ProfileManager();

    private final LogManager logManager = new LogManager(this);
    private final ThreadManager threadManager = new ThreadManager(this);

    // Might error for registering before enabled, doubt it.
    private final AlertManager alertManager = new AlertManager();
    private final CheckManager checkManager = new CheckManager();
    private final NmsManager nmsManager = new NmsManager();

    @Override
    public void onEnable() {
        instance = this;

        // Configuration Files
        configuration.setup();
        checks.setup();
        lang.setup();

        //Tasks
        loadTasks();

        //Packet Listener
        ProtocolLibrary.getProtocolManager().addPacketListener(new NetworkListener(this));

        // Bukkit Listeners
        Arrays.asList(
                new ProfileListener(this),
                new ClientListener(this),
                new ViolationListener(this),
                new BukkitListener(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        //Load Commands
        getCommand("anticheat").setExecutor(new CommandManager(this));
    }

    private void loadTasks() {
        new TickTask(this).runTaskTimerAsynchronously(this, 50L, 0L);

        if (Config.Setting.LOGS_ENABLED.getBoolean()) {
            new LogsTask(this).runTaskTimerAsynchronously(this, 1200L, 320L);
        }

        final long violationInterval = Config.Setting.CHECK_SETTINGS_VIOLATION_RESET_INTERVAL.getLong() * 1200;

        new ViolationTask(this).runTaskTimerAsynchronously(this, violationInterval, violationInterval);
    }

    @Override
    public void onDisable() {

        //Files
        this.configuration.reset();
        this.checks.reset();

        // Shutdown all managers
        this.profileManager.shutdown();
        this.checkManager.shutdown();
        this.alertManager.shutdown();
        this.threadManager.shutdown();

        //Clear reflection cache
        ReflectionUtils.clear();

        instance = null;
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