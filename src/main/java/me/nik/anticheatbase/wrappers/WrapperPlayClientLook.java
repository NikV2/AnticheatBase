package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;

public class WrapperPlayClientLook extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.LOOK;

    private final float yaw, pitch;
    private final boolean onGround;

    public WrapperPlayClientLook(PacketContainer packet) {
        super(packet, TYPE);

        StructureModifier<Float> floats = handle.getFloat();

        this.yaw = floats.read(0);
        this.pitch = floats.read(1);
        this.onGround = handle.getBooleans().read(0);
    }

    /**
     * Retrieve Yaw.
     * <p>
     * Notes: absolute rotation on the X Axis, in degrees
     *
     * @return The current Yaw
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Retrieve Pitch.
     * <p>
     * Notes: absolute rotation on the Y Axis, in degrees
     *
     * @return The current Pitch
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Retrieve On Ground.
     * <p>
     * Notes: true if the client is on the ground, False otherwise
     *
     * @return The current On Ground
     */
    public boolean getOnGround() {
        return onGround;
    }
}