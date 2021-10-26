package me.nik.anticheatbase.playerdata.data;

import com.comphenix.protocol.events.PacketContainer;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.utils.CollisionUtils;
import me.nik.anticheatbase.utils.MathUtils;
import me.nik.anticheatbase.utils.custom.Effects;
import me.nik.anticheatbase.utils.custom.Equipment;
import me.nik.anticheatbase.utils.fastmath.FastMath;
import me.nik.anticheatbase.wrappers.WrapperPlayClientLook;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPosition;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPositionLook;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * A movement data class holding every essential information we need when it comes to movement related data.
 */
public class MovementData {

    private final Profile profile;

    private final Effects effects;

    private final Equipment equipment;

    private double deltaX, lastDeltaX, deltaZ, lastDeltaZ, deltaY, lastDeltaY, deltaXZ, lastDeltaXZ,
            accelXZ, lastAccelXZ, accelY, lastAccelY;

    private Location location, lastLocation;

    private boolean onGround, lastOnGround, serverGround, lastServerGround;

    private int flyTicks, serverGroundTicks, lastServerGroundTicks, clientGroundTicks;

    public MovementData(Profile profile) {
        this.profile = profile;

        this.effects = new Effects();
        this.equipment = new Equipment();
    }

    public void processPacket(Packet packet) {

        final Player p = profile.getPlayer();

        if (p == null) return;

        final World world = p.getWorld();

        final PacketContainer container = packet.getPacket();

        if (packet.isPosition()) {

            final WrapperPlayClientPosition move = new WrapperPlayClientPosition(container);

            double x = move.getX();
            double y = move.getY();
            double z = move.getZ();
            float yaw = getLocation().getYaw();
            float pitch = getLocation().getPitch();
            boolean onGround = move.getOnGround();

            this.lastOnGround = this.onGround;
            this.onGround = onGround;

            this.flyTicks = onGround ? 0 : this.flyTicks++;
            this.clientGroundTicks = onGround ? this.clientGroundTicks++ : 0;

            Location location = new Location(world, x, y, z, yaw, pitch);
            Location lastLocation = getLocation() != null ? getLocation() : location;

            processLocation(lastLocation, location);

        } else if (packet.isPositionLook()) {

            final WrapperPlayClientPositionLook movePosLook = new WrapperPlayClientPositionLook(container);

            double xPosLook = movePosLook.getX();
            double yPosLook = movePosLook.getY();
            double zPosLook = movePosLook.getZ();
            float yawPosLook = movePosLook.getYaw();
            float pitchPosLook = movePosLook.getPitch();
            boolean onGroundPosLook = movePosLook.getOnGround();

            this.lastOnGround = this.onGround;
            this.onGround = onGroundPosLook;

            this.flyTicks = onGroundPosLook ? 0 : this.flyTicks++;
            this.clientGroundTicks = onGroundPosLook ? this.clientGroundTicks++ : 0;

            Location locationPosLook = new Location(world, xPosLook, yPosLook, zPosLook, yawPosLook, pitchPosLook);
            Location lastLocationPosLook = getLocation() != null ? getLocation() : locationPosLook;

            processLocation(lastLocationPosLook, locationPosLook);

        } else if (packet.isLook()) {

            final WrapperPlayClientLook moveLook = new WrapperPlayClientLook(container);

            double xLook = getLocation().getX();
            double yLook = getLocation().getY();
            double zLook = getLocation().getZ();
            float yawLook = moveLook.getYaw();
            float pitchLook = moveLook.getPitch();
            boolean onGroundLook = moveLook.getOnGround();

            this.lastOnGround = this.onGround;
            this.onGround = onGroundLook;

            this.flyTicks = onGroundLook ? 0 : this.flyTicks++;
            this.clientGroundTicks = onGroundLook ? this.clientGroundTicks++ : 0;

            Location locationLook = new Location(world, xLook, yLook, zLook, yawLook, pitchLook);
            Location lastLocationLook = getLocation() != null ? getLocation() : locationLook;

            processLocation(lastLocationLook, locationLook);
        }
    }

    private void processLocation(Location from, Location to) {

        this.lastLocation = from;
        this.location = to;

        final double lastDeltaX = this.deltaX;
        final double deltaX = to.getX() - from.getX();

        this.lastDeltaX = lastDeltaX;
        this.deltaX = deltaX;

        final double lastDeltaY = this.deltaY;
        final double deltaY = to.getY() - from.getY();

        this.lastDeltaY = lastDeltaY;
        this.deltaY = deltaY;

        final double lastAccelY = this.accelY;
        final double accelY = Math.abs(lastDeltaY - deltaY);

        this.lastAccelY = lastAccelY;
        this.accelY = accelY;

        final double lastDeltaZ = this.deltaZ;
        final double deltaZ = to.getZ() - from.getZ();

        this.lastDeltaZ = lastDeltaZ;
        this.deltaZ = deltaZ;

        final double lastDeltaXZ = this.deltaXZ;
        final double deltaXZ = FastMath.hypot(deltaX, deltaZ);

        this.lastDeltaXZ = lastDeltaXZ;
        this.deltaXZ = deltaXZ;

        final double lastAccelXZ = this.accelXZ;
        final double accelXZ = Math.abs(lastDeltaXZ - deltaXZ);

        this.lastAccelXZ = lastAccelXZ;
        this.accelXZ = accelXZ;

        processPlayerData(to);
    }

    private void handleNearbyBlocks(Location location) {

        /*
        Handle collisions here

        NOTE: It's recommended to run a single big loop instead of multiple ones in order to grab collisions + nearby blocks
        Doing it multiple times within a tick can be insanely heavy on big playercounts.

        I'd also recommend using NMS when getting the block + block type off the main thread due to it
        Being insanely heavy in 1.9+ servers, And is generally faster.
         */
    }

    private void processPlayerData(Location location) {

        //Save up some perfomance

        if (this.deltaXZ == 0D && this.deltaY == 0D) return;

        //Make sure the player is not null

        final Player p = profile.getPlayer();

        if (p == null) return;

        if (!CollisionUtils.isChunkUnloaded(location)) {

            //Nearby Blocks

            handleNearbyBlocks(location);
        }

        //Server Ground

        final boolean lastServerGround = this.serverGround;

        final boolean serverGround = location.getY() % MathUtils.SERVER_GROUND_DIVISOR < .0001D;

        this.lastServerGround = lastServerGround;

        this.serverGround = serverGround;

        this.serverGroundTicks = serverGround ? this.serverGroundTicks + 1 : 0;

        this.lastServerGroundTicks = serverGround ? 0 : this.lastServerGroundTicks + 1;

        //Effects

        this.effects.handle(p);

        //Equipment

        this.equipment.handle(p);
    }

    //Handle it like the client instead of exempting for teleports on movement checks
    public void handleTeleport() {
        this.deltaXZ = 0D;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getClientGroundTicks() {
        return clientGroundTicks;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getLastDeltaX() {
        return lastDeltaX;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public double getLastDeltaZ() {
        return lastDeltaZ;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public double getLastDeltaY() {
        return lastDeltaY;
    }

    public double getDeltaXZ() {
        return deltaXZ;
    }

    public double getLastDeltaXZ() {
        return lastDeltaXZ;
    }

    public double getAccelXZ() {
        return accelXZ;
    }

    public double getLastAccelXZ() {
        return lastAccelXZ;
    }

    public double getAccelY() {
        return accelY;
    }

    public double getLastAccelY() {
        return lastAccelY;
    }

    public Location getLocation() {
        if (this.location == null) this.location = profile.getPlayer().getLocation();

        return this.location;
    }

    public Effects getEffects() {
        return effects;
    }

    public Location getLastLocation() {
        if (this.lastLocation == null) this.lastLocation = profile.getPlayer().getLocation();

        return this.lastLocation;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isServerGround() {
        return serverGround;
    }

    public int getFlyTicks() {
        return flyTicks;
    }

    public int getLastServerGroundTicks() {
        return lastServerGroundTicks;
    }

    public int getServerGroundTicks() {
        return serverGroundTicks;
    }

    public boolean isLastOnGround() {
        return lastOnGround;
    }

    public boolean isLastServerGround() {
        return lastServerGround;
    }
}