package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerEntityVelocity extends PacketWrapper {
    public static final PacketType TYPE =
            PacketType.Play.Server.ENTITY_VELOCITY;

    public WrapperPlayServerEntityVelocity() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerEntityVelocity(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve Entity ID.
     * <p>
     * Notes: entity's ID
     *
     * @return The current Entity ID
     */
    public int getEntityID() {
        return handle.getIntegers().read(0);
    }

    /**
     * Set Entity ID.
     *
     * @param value - new value.
     */
    public void setEntityID(int value) {
        handle.getIntegers().write(0, value);
    }

    /**
     * Retrieve the entity of the painting that will be spawned.
     *
     * @param world - the current world of the entity.
     * @return The spawned entity.
     */
    public Entity getEntity(World world) {
        return handle.getEntityModifier(world).read(0);
    }

    /**
     * Retrieve the entity of the painting that will be spawned.
     *
     * @param event - the packet event.
     * @return The spawned entity.
     */
    public Entity getEntity(PacketEvent event) {
        return getEntity(event.getPlayer().getWorld());
    }

    /**
     * Retrieve the velocity in the x axis.
     *
     * @return The current velocity X
     */
    public double getVelocityX() {
        return handle.getIntegers().read(1) / 8000.0D;
    }

    /**
     * Set the velocity in the x axis.
     *
     * @param value - new value.
     */
    public void setVelocityX(double value) {
        handle.getIntegers().write(1, (int) (value * 8000.0D));
    }

    /**
     * Retrieve the velocity in the y axis.
     *
     * @return The current velocity y
     */
    public double getVelocityY() {
        return handle.getIntegers().read(2) / 8000.0D;
    }

    /**
     * Set the velocity in the y axis.
     *
     * @param value - new value.
     */
    public void setVelocityY(double value) {
        handle.getIntegers().write(2, (int) (value * 8000.0D));
    }

    /**
     * Retrieve the velocity in the z axis.
     *
     * @return The current velocity z
     */
    public double getVelocityZ() {
        return handle.getIntegers().read(3) / 8000.0D;
    }

    /**
     * Set the velocity in the z axis.
     *
     * @param value - new value.
     */
    public void setVelocityZ(double value) {
        handle.getIntegers().write(3, (int) (value * 8000.0D));
    }
}