package me.nik.anticheatbase.processors.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.ImmutableSet;
import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.utils.ServerUtils;
import me.nik.anticheatbase.wrappers.WrapperPlayServerEntityVelocity;
import org.bukkit.entity.Player;

/**
 * A network listener class that we'll use in order to listen for received and sent packets for our checks and data.
 */
public class NetworkListener extends PacketAdapter {

    private final Anticheat plugin;

    /*
    Filter only the clientbound and serverbound packets that we'll actually use.
    This ensures that we'll be maximizing our perfomance by not processing any
    Packets that we're never using.
     */
    private static final ImmutableSet<PacketType> WHITELISTED_PACKETS = new ImmutableSet.Builder<PacketType>()
            .add(
                    /*
                    Clientbound Packets
                     */
                    ServerUtils.isCavesUpdate() ? PacketType.Play.Client.GROUND : PacketType.Play.Client.FLYING,
                    PacketType.Play.Client.POSITION,
                    PacketType.Play.Client.POSITION_LOOK,
                    PacketType.Play.Client.LOOK,
                    PacketType.Play.Client.ABILITIES,
                    PacketType.Play.Client.ARM_ANIMATION,
                    PacketType.Play.Client.BLOCK_DIG,
                    PacketType.Play.Client.BLOCK_PLACE,
                    PacketType.Play.Client.CHAT,
                    PacketType.Play.Client.ENTITY_ACTION,
                    PacketType.Play.Client.KEEP_ALIVE,
                    ServerUtils.isCavesUpdate() ? PacketType.Play.Client.PONG : PacketType.Play.Client.TRANSACTION,
                    PacketType.Play.Client.RESOURCE_PACK_STATUS,
                    PacketType.Play.Client.SETTINGS,
                    PacketType.Play.Client.SPECTATE,
                    PacketType.Play.Client.STEER_VEHICLE,
                    PacketType.Play.Client.USE_ENTITY,
                    PacketType.Play.Client.USE_ITEM,
                    PacketType.Play.Client.VEHICLE_MOVE,
                    PacketType.Play.Client.WINDOW_CLICK,
                    PacketType.Play.Client.SET_CREATIVE_SLOT,
                    PacketType.Play.Client.HELD_ITEM_SLOT
            ).add(
                    /*
                    Serverbound Packets
                     */
                    PacketType.Play.Server.EXPLOSION,
                    PacketType.Play.Server.ENTITY_VELOCITY,
                    PacketType.Play.Server.KEEP_ALIVE,
                    PacketType.Play.Server.ABILITIES,
                    PacketType.Play.Server.POSITION,
                    ServerUtils.isCavesUpdate() ? PacketType.Play.Server.PING : PacketType.Play.Server.TRANSACTION
            ).build();

    public NetworkListener(Anticheat plugin) {
        super(plugin, ListenerPriority.MONITOR, WHITELISTED_PACKETS);

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