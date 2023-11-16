package me.nik.anticheatbase.processors.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.utils.ChatUtils;
import me.nik.anticheatbase.utils.MoveUtils;
import me.nik.anticheatbase.utils.TaskUtils;
import me.nik.anticheatbase.wrappers.WrapperPlayClientLook;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPosition;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPositionLook;
import me.nik.anticheatbase.wrappers.WrapperPlayServerEntityVelocity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static final List<PacketType> WHITELISTED_PACKETS = new CopyOnWriteArrayList<>(Stream.of(
            /*
            Clientbound Packets
             */
            PacketType.Play.Client.FLYING,
            PacketType.Play.Client.GROUND,
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
            PacketType.Play.Client.PONG,
            PacketType.Play.Client.TRANSACTION,
            PacketType.Play.Client.RESOURCE_PACK_STATUS,
            PacketType.Play.Client.SETTINGS,
            PacketType.Play.Client.SPECTATE,
            PacketType.Play.Client.STEER_VEHICLE,
            PacketType.Play.Client.USE_ENTITY,
            PacketType.Play.Client.USE_ITEM,
            PacketType.Play.Client.VEHICLE_MOVE,
            PacketType.Play.Client.WINDOW_CLICK,
            PacketType.Play.Client.SET_CREATIVE_SLOT,
            PacketType.Play.Client.HELD_ITEM_SLOT,
            /*
            Serverbound Packets
             */
            PacketType.Play.Server.EXPLOSION,
            PacketType.Play.Server.ENTITY_VELOCITY,
            PacketType.Play.Server.KEEP_ALIVE,
            PacketType.Play.Server.ABILITIES,
            PacketType.Play.Server.POSITION,
            PacketType.Play.Server.PING,
            PacketType.Play.Server.TRANSACTION,
            PacketType.Play.Server.REMOVE_ENTITY_EFFECT,
            PacketType.Play.Server.ENTITY_EFFECT,
            PacketType.Play.Server.UPDATE_ATTRIBUTES
            /*
            Remove any packets that are not supported
            In the current server version.

            ProtocolLib removes them automatically
            However with this we're ensuring
            There won't be any warnings.
             */
    ).filter(PacketType::isSupported).collect(Collectors.toList()));

    public NetworkListener(Anticheat plugin) {
        super(plugin, ListenerPriority.LOWEST, WHITELISTED_PACKETS);

        this.plugin = plugin;

        //Clear cache
        WHITELISTED_PACKETS.clear();
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        if (e.isPlayerTemporary() || e.getPlayer() == null) return;

        final Player player = e.getPlayer();

        final Packet packet = new Packet(e.getPacket(), System.currentTimeMillis());

        /*
         Check for position crashers which could destroy our multithreading
         We have to do this on the netty thread in order to cancel the packet
        */
        final String crashAttempt = checkCrasher(packet);

        if (crashAttempt != null) {

            e.setCancelled(true);

            ChatUtils.log("Kicking " + player.getName() + " for sending an invalid position packet, Information: " + crashAttempt);

            //Kick the player on the main thread
            TaskUtils.task(() -> player.kickPlayer("Invalid Packet"));

            return;
        }

        final Profile profile = this.plugin.getProfileManager().getProfile(player);

        if (profile == null) return;

        profile.getProfileThread().execute(() -> profile.handle(packet));
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.isPlayerTemporary() || e.getPlayer() == null) return;

        final Player player = e.getPlayer();

        final Profile profile = this.plugin.getProfileManager().getProfile(player);

        if (profile == null) return;

        final PacketContainer container = e.getPacket();

        final Packet packet = new Packet(container, System.currentTimeMillis());

        /*
        ---------------------------------------------------------------------------
        Validate serverbound packets to make sure they're being sent to the player
        ---------------------------------------------------------------------------
         */
        final int playerId = player.getEntityId();

        switch (packet.getType()) {

            case SERVER_ENTITY_VELOCITY:

                final WrapperPlayServerEntityVelocity velocity = new WrapperPlayServerEntityVelocity(container);

                if (velocity.getEntityID() != playerId) return;

                break;

                /*
                Validate more
                 */
        }
        /*
        ---------------------------------------------------------------------------
         */

        profile.getProfileThread().execute(() -> profile.handle(packet));
    }

    private String checkCrasher(Packet packet) {

        final Packet.Type type = packet.getType();

        double x = 0D, y = 0D, z = 0D;
        float yaw = 0F, pitch = 0F;

        switch (type) {

            case POSITION:

                WrapperPlayClientPosition pos = packet.getPositionWrapper();

                x = Math.abs(pos.getX());
                y = Math.abs(pos.getY());
                z = Math.abs(pos.getZ());

                break;

            case POSITION_LOOK:

                WrapperPlayClientPositionLook posLook = packet.getPositionLookWrapper();

                x = Math.abs(posLook.getX());
                y = Math.abs(posLook.getY());
                z = Math.abs(posLook.getZ());
                yaw = Math.abs(posLook.getYaw());
                pitch = Math.abs(posLook.getPitch());

                break;

            case LOOK:

                WrapperPlayClientLook look = packet.getLookWrapper();

                yaw = Math.abs(look.getYaw());
                pitch = Math.abs(look.getPitch());

                break;
        }

        final double invalidValue = 3.0E7D;

        //This messes our threading system and potentially causes damage to the server.
        final boolean invalid = x > invalidValue || y > invalidValue || z > invalidValue
                || yaw > 3.4028235e+35F
                || pitch > MoveUtils.MAXIMUM_PITCH;

        //It's impossible for these values to be NaN or Infinite.
        final boolean impossible = !Double.isFinite(x)
                || !Double.isFinite(y)
                || !Double.isFinite(z)
                || !Float.isFinite(yaw)
                || !Float.isFinite(pitch);

        if (invalid || impossible) {
            return "X: " + x + " Y: " + y + " Z: " + z + " Yaw: " + yaw + " Pitch: " + pitch;
        }

        return null;
    }
}