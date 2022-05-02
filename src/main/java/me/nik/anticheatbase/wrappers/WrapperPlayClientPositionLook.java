package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;

public class WrapperPlayClientPositionLook extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.POSITION_LOOK;

    private final double x, y, z;
    private final float yaw, pitch;
    private final boolean onGround;

    public WrapperPlayClientPositionLook(PacketContainer packet) {
        super(packet, TYPE);

        StructureModifier<Double> doubles = handle.getDoubles();
        StructureModifier<Float> floats = handle.getFloat();

        this.x = doubles.read(0);
        this.y = doubles.read(1);
        this.z = doubles.read(2);

        this.yaw = floats.read(0);
        this.pitch = floats.read(1);

        this.onGround = handle.getBooleans().read(0);
    }

    /**
     * Retrieve X.
     * <p>
     * Notes: absolute position
     *
     * @return The current X
     */
    public double getX() {
        return x;
    }

    /**
     * Retrieve Feet Y.
     * <p>
     * Notes: absolute feet position. Is normally HeadY - 1.62. Used to modify
     * the players bounding box when going up stairs, crouching, etc…
     *
     * @return The current FeetY
     */
    public double getY() {
        return y;
    }

    /**
     * Retrieve Z.
     * <p>
     * Notes: absolute position
     *
     * @return The current Z
     */
    public double getZ() {
        return z;
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