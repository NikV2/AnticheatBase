package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class WrapperPlayClientBlockDig extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.BLOCK_DIG;

    private final BlockPosition location;
    private final EnumWrappers.Direction direction;
    private final EnumWrappers.PlayerDigType status;

    public WrapperPlayClientBlockDig(PacketContainer packet) {
        super(packet, TYPE);

        this.location = handle.getBlockPositionModifier().read(0);

        this.direction = handle.getDirections().read(0);

        this.status = handle.getPlayerDigTypes().read(0);
    }

    /**
     * Retrieve Location.
     * <p>
     * Notes: block position
     *
     * @return The current Location
     */
    public BlockPosition getLocation() {
        return location;
    }

    public EnumWrappers.Direction getDirection() {
        return direction;
    }

    /**
     * Retrieve Status.
     * <p>
     * Notes: the action the player is taking against the block (see below)
     *
     * @return The current Status
     */
    public EnumWrappers.PlayerDigType getStatus() {
        return status;
    }
}