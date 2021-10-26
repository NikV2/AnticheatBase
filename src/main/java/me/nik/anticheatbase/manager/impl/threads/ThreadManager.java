package me.nik.anticheatbase.manager.impl.threads;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.manager.Manager;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.utils.MiscUtils;
import me.nik.anticheatbase.utils.custom.exception.AnticheatException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Comparator;

/**
 * A simple thread manager class that'll help us make sure a player profile is provided with the best
 * Available thread at any time, While also shutting down threads that are not used.
 */
public class ThreadManager extends Manager<ProfileThread> implements Listener {

    //Get a proper thread limit
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;

    private final Anticheat plugin;

    public ThreadManager(Anticheat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
    }

    public ProfileThread getAvailableProfileThread() {

        ProfileThread profileThread;

        //Checks whether we should create a new thread based on the thread limit
        if (size() < MAX_THREADS) {

            //Create a new profile thread and set it to our variable in order to use it
            profileThread = new ProfileThread();

            //Add our new profile thread to the list in order to use it for future profiles
            add(profileThread);

        } else {

            //Get an available thread based on the profiles using it, Otherwise grab a random element to avoid issues.
            profileThread = getList()
                    .stream()
                    .min(Comparator.comparing(ProfileThread::getProfileCount))
                    .orElse(MiscUtils.randomElement(getList()));
        }

        //Throw an exception if the profile thread is null, Which should be impossible.
        if (profileThread == null) {
            throw new AnticheatException("Encountered a null profile thread, Please restart the server to avoid any issues.");
        }

        //Return the available thread and increment the profile count
        return profileThread.incrementAndGet();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Profile profile = this.plugin.getProfileManager().getProfile(e.getPlayer());

        if (profile == null) return;

        ProfileThread profileThread = profile.getProfileThread();

        /*
        If this is the only profile using this thread, Shut it down and remove it from the list
        Otherwise decrease the counter and return.
        */
        if (profileThread.getProfileCount() > 1) {

            profileThread.decrement();

            return;
        }

        remove(profileThread.shutdownThread());
    }

    @Override
    public void shutdown() {

        getList().forEach(ProfileThread::shutdownThread);

        clear();
    }
}