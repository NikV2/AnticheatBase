package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedEnumEntityUseAction;
import org.bukkit.util.Vector;

public class WrapperPlayClientUseEntity extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.USE_ENTITY;

    private final int targetId;
    private final EnumWrappers.EntityUseAction type;
    private Vector targetVector;

    public WrapperPlayClientUseEntity(PacketContainer packet) {
        super(packet, TYPE);

        this.targetId = handle.getIntegers().read(0);

        EnumWrappers.EntityUseAction action;

        WrappedEnumEntityUseAction enumEntityUseAction = handle.getEnumEntityUseActions().read(0);

        if ((action = enumEntityUseAction.getAction()) == EnumWrappers.EntityUseAction.INTERACT_AT) {

            this.targetVector = enumEntityUseAction.getPosition();
        }

        this.type = action;
    }

    /**
     * Retrieve entity ID of the target.
     *
     * @return The current entity ID
     */
    public int getTargetID() {
        return targetId;
    }

    /**
     * Retrieve Type.
     *
     * @return The current Type
     */
    public EnumWrappers.EntityUseAction getType() {
        return type;
    }

    /**
     * Retrieve the target vector.
     *
     * @return The target vector or null
     */
    public Vector getTargetVector() {
        return targetVector;
    }
}