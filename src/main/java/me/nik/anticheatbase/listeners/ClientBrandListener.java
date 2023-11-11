package me.nik.anticheatbase.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.utils.ChatUtils;
import me.nik.anticheatbase.utils.TaskUtils;
import me.nik.anticheatbase.utils.custom.ExpiringSet;
import me.nik.anticheatbase.wrappers.WrapperPlayClientCustomPayload;
import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A client listener that we'll use in order to get the profile's client brand.
 */
public class ClientBrandListener extends PacketAdapter {

    private final Anticheat plugin;

    /*
    We need to do this in order to fix edge cases that mostly occur in bungeecord servers
    Where the client brand payload would get sent more than once.
     */
    private final ExpiringSet<UUID> cache = new ExpiringSet<>(5000L);

    public ClientBrandListener(Anticheat plugin) {
        super(plugin, ListenerPriority.MONITOR, PacketType.Play.Client.CUSTOM_PAYLOAD);

        this.plugin = plugin;
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        if (e.isPlayerTemporary() || e.getPlayer() == null) return;

        Player player = e.getPlayer();

        UUID uuid = player.getUniqueId();

        WrapperPlayClientCustomPayload payload = new WrapperPlayClientCustomPayload(e.getPacket());

        String channel = payload.getChannel();

        /*
        Check if we received a payload from the brand channel
        Or if the player has set his brand recently.
         */
        if (channel == null || !channel.toLowerCase().endsWith("brand") || this.cache.contains(uuid)) return;

        String brand;

        try {

            /*
            Clear any color codes to make sure they're not exploiting this
            And translate the bytes.
             */
            brand = ChatUtils.stripColorCodes(new String(payload.getContents(), StandardCharsets.UTF_8).substring(1));

        } catch (Exception ex) {

            /*
            Cant parse, should never happen unless a client is doing it intentionally.
             */
            return;
        }

        /*
        Add the player's uuid to the cache
         */
        this.cache.add(uuid);

        /*
        Schedule it to run two seconds later to make sure the player profile has been initialized
         */
        TaskUtils.taskLaterAsync(() -> {

            Profile profile = this.plugin.getProfileManager().getProfile(player);

            /*
            Just to make sure.
             */
            if (profile == null || !profile.getClient().equals("Unknown")) return;

            profile.setClient(brand);

        }, 2000L);
    }
}