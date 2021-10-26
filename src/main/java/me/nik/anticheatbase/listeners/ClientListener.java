package me.nik.anticheatbase.listeners;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.utils.ChatUtils;
import me.nik.anticheatbase.utils.ServerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

/**
 * A client listener that we'll use in order to get the profile's client brand
 *
 * NOTE: You can do this more efficiently by listening for the custompayload packet
 * However this is the way i know how to do it.
 */
public class ClientListener implements Listener, PluginMessageListener {

    private final Anticheat plugin;

    //The channel is slightly different in legacy versions
    private static final String CHANNEL = ServerUtils.isLegacy() ? "MC|Brand" : "minecraft:brand";

    public ClientListener(Anticheat plugin) {

        this.plugin = plugin;

        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, CHANNEL, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {

        final Player p = e.getPlayer();

        try {

            p.getClass().getMethod("addChannel", String.class).invoke(p, CHANNEL);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {

            //?????
            ex.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        Profile profile = this.plugin.getProfileManager().getProfile(player);

        if (profile == null) return;

        /*
        The reason we're stripping color codes is to avoid exploits where the client can modify the client brand
        And put a lot of color codes to it effectively making debugging the player's brand a pain
        Due to all the chat colors it can put.
         */
        profile.setClient(ChatUtils.stripColorCodes(new String(message, StandardCharsets.UTF_8).substring(1)));
    }
}