package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;

public class WrapperPlayClientPosition extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.POSITION;

    private final double x, y, z;
    private final boolean onGround;

    public WrapperPlayClientPosition(PacketContainer packet) {
        super(packet, TYPE);

        StructureModifier<Double> doubles = handle.getDoubles();

        this.x = doubles.read(0);
        this.y = doubles.read(1);
        this.z = doubles.read(2);

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
     * Retrieve FeetY.
     * <p>
     * Notes: absolute feet position, normally HeadY - 1.62. Used to modify the
     * players bounding box when going up stairs, crouching, etc…
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