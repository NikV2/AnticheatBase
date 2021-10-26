package me.nik.anticheatbase.processors.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.wrappers.WrapperPlayServerEntityVelocity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A network listener class that we'll use in order to listen for received and sent packets for our checks and data.
 */
public class NetworkListener extends PacketAdapter {

    private final Anticheat plugin;

    private static final List<PacketType> LISTENING_PACKETS = new ArrayList<>();

    static {

        //Listen for every single client packet
        PacketType.Play.Client.getInstance().values()
                .stream()
                .filter(PacketType::isSupported)
                .forEach(LISTENING_PACKETS::add);

        //Listen for every single server packet
        PacketType.Play.Server.getInstance().values()
                .stream()
                .filter(PacketType::isSupported)
                .forEach(LISTENING_PACKETS::add);

        /*
        Feel free to filter out any packet types that you don't want to listen to.
        I simply added every single thing since this is just a base.
         */
    }

    public NetworkListener(Anticheat plugin) {
        super(plugin, ListenerPriority.MONITOR, LISTENING_PACKETS);

        this.plugin = plugin;
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        if (e.isPlayerTemporary() || e.getPlayer() == null) return;

        final Profile profile = this.plugin.getProfileManager().getProfile(e.getPlayer());

        if (profile == null) return;

        profile.getProfileThread().execute(() -> profile.handlePacket(new Packet(e.getPacket())));
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.isPlayerTemporary() || e.getPlayer() == null) return;

        final Player p = e.getPlayer();

        final Profile profile = this.plugin.getProfileManager().getProfile(p);

        if (profile == null) return;

        final int id = p.getEntityId();

        final PacketContainer packet = e.getPacket();

        final PacketType type = e.getPacketType();

        if (type == PacketType.Play.Server.ENTITY_VELOCITY) {

            final WrapperPlayServerEntityVelocity velocity = new WrapperPlayServerEntityVelocity(packet);

            if (velocity.getEntityID() != id) return;
        }

        profile.getProfileThread().execute(() -> profile.handlePacket(new Packet(packet)));
    }
}