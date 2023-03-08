package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedEnumEntityUseAction;
import me.nik.anticheatbase.utils.ServerVersion;
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

        if (ServerVersion.getVersion().isHigherThan(ServerVersion.v1_16_R3)) {

            WrappedEnumEntityUseAction enumEntityUseAction = handle.getEnumEntityUseActions().read(0);

            if ((action = enumEntityUseAction.getAction()) == EnumWrappers.EntityUseAction.INTERACT_AT) {

                this.targetVector = enumEntityUseAction.getPosition();
            }

        } else {

            if ((action = handle.getEntityUseActions().read(0)) == EnumWrappers.EntityUseAction.INTERACT_AT) {

                this.targetVector = handle.getVectors().read(0);
            }
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