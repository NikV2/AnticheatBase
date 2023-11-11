package me.nik.anticheatbase;

import com.comphenix.protocol.ProtocolLibrary;
import me.nik.anticheatbase.commands.CommandManager;
import me.nik.anticheatbase.files.Checks;
import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.files.commentedfiles.CommentedFileConfiguration;
import me.nik.anticheatbase.listeners.ClientBrandListener;
import me.nik.anticheatbase.listeners.ProfileListener;
import me.nik.anticheatbase.listeners.ViolationListener;
import me.nik.anticheatbase.managers.AlertManager;
import me.nik.anticheatbase.managers.logs.LogManager;
import me.nik.anticheatbase.managers.profile.ProfileManager;
import me.nik.anticheatbase.managers.themes.ThemeManager;
import me.nik.anticheatbase.managers.threads.ThreadManager;
import me.nik.anticheatbase.nms.NmsManager;
import me.nik.anticheatbase.processors.listeners.BukkitListener;
import me.nik.anticheatbase.processors.listeners.NetworkListener;
import me.nik.anticheatbase.tasks.LogsTask;
import me.nik.anticheatbase.tasks.TickTask;
import me.nik.anticheatbase.tasks.ViolationTask;
import me.nik.anticheatbase.utils.ChatUtils;
import me.nik.anticheatbase.utils.MiscUtils;
import me.nik.anticheatbase.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * A simple and efficient anticheat base
 *
 * @author Nik
 */
public class Anticheat extends JavaPlugin {

    private static Anticheat instance;

    private Config configuration;
    private Checks checks;

    private ProfileManager profileManager;
    private final NmsManager nmsManager = new NmsManager();
    private LogManager logManager;
    private ThreadManager threadManager;

    private AlertManager alertManager;
    private ThemeManager themeManager;

    @Override
    public void onEnable() {

        instance = this;

        //Initialize
        (this.configuration = new Config(this)).initialize();
        (this.checks = new Checks(this)).initialize();
        (this.profileManager = new ProfileManager()).initialize();
        (this.themeManager = new ThemeManager(this)).initialize();
        (this.logManager = new LogManager(this)).initialize();
        (this.threadManager = new ThreadManager(this)).initialize();
        (this.alertManager = new AlertManager()).initialize();

        //Tasks
        new TickTask(this).runTaskTimerAsynchronously(this, 50L, 0L);

        if (Config.Setting.LOGS_ENABLED.getBoolean()) {
            new LogsTask(this).runTaskTimerAsynchronously(this, 6000L, 6000L);
        }

        new ViolationTask(this).runTaskTimerAsynchronously(this,
                Config.Setting.CHECK_SETTINGS_VIOLATION_RESET_INTERVAL.getLong() * 1200L,
                Config.Setting.CHECK_SETTINGS_VIOLATION_RESET_INTERVAL.getLong() * 1200L);

        //Packet Listeners
        Arrays.asList(
                new NetworkListener(this),
                new ClientBrandListener(this)
        ).forEach(packetListener -> ProtocolLibrary.getProtocolManager().addPacketListener(packetListener));

        //Bukkit Listeners
        Arrays.asList(
                new ProfileListener(this),
                new ViolationListener(this),
                new BukkitListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        //Load Commands
        getCommand("anticheat").setExecutor(new CommandManager(this));

        //We're most likely going to be using transactions - ping pongs, So we need to do this for ViaVersion
        System.setProperty("com.viaversion.handlePingsAsInvAcknowledgements", "true");

        //Initialize static variables to make sure our threads won't get affected when they run for the first time.
        try {

            MiscUtils.initializeClasses(
                    "me.nik.anticheatbase.utils.fastmath.FastMath",
                    "me.nik.anticheatbase.utils.fastmath.NumbersUtils",
                    "me.nik.anticheatbase.utils.fastmath.FastMathLiteralArrays",
                    "me.nik.anticheatbase.utils.minecraft.MathHelper",
                    "me.nik.anticheatbase.utils.CollisionUtils",
                    "me.nik.anticheatbase.utils.MoveUtils"
            );

        } catch (ClassNotFoundException e) {

            //Impossible unless we made a mistake
            ChatUtils.log("An error was thrown during initialization, The anticheat may not work properly.");

            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

        //Shutdown all managers
        this.configuration.shutdown();
        this.checks.shutdown();
        this.profileManager.shutdown();
        this.alertManager.shutdown();
        this.threadManager.shutdown();
        this.themeManager.shutdown();

        //Clear reflection cache
        ReflectionUtils.clear();

        //Unregister any listeners
        HandlerList.unregisterAll(this);
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);

        //Cancel all tasks
        Bukkit.getScheduler().cancelTasks(this);

        instance = null;
    }

    public CommentedFileConfiguration getConfiguration() {
        return this.configuration.getConfig();
    }

    public CommentedFileConfiguration getChecks() {
        return this.checks.getConfig();
    }

    public ThemeManager getThemeManager() {
        return themeManager;
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

    public AlertManager getAlertManager() {
        return alertManager;
    }

    public NmsManager getNmsManager() {
        return nmsManager;
    }

    public static Anticheat getInstance() {
        return instance;
    }
}