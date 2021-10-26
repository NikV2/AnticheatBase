package me.nik.anticheatbase.processors.listeners;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.playerdata.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * A bukkit listener class that we'll use for our bukkit checks and data
 *
 * NOTE: You shouldn't be using bukkit events in the first place, I just added this for the sake of having it.
 */
public class BukkitListener implements Listener {

    private final Anticheat plugin;

    public BukkitListener(Anticheat plugin) {
        this.plugin = plugin;
    }

    private void onEvent(Event e, Player p) {

        //Yes, that can happen.
        if (!p.isOnline()) return;

        Profile profile = this.plugin.getProfileManager().getProfile(p);

        if (profile == null) return;

        /*
        Asynchronous bukkit events, Yes, Don't ask.
        You shouldn't be using events in the first place but i felt like i should add this option.

        Make sure to not cancel or interact with any event due to this being asynchronous
         */
        profile.getProfileThread().execute(() -> profile.handleBukkit(e));
    }

    /*
    Here you can add all of your different methods that you want to listen to.

    After that simply call our onEvent method so it can process it within the player profile.
     */
}