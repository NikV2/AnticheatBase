package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerAction;

public class WrapperPlayClientEntityAction extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.ENTITY_ACTION;

    private final int entityId, jumpBoost;
    private final PlayerAction action;

    public WrapperPlayClientEntityAction(PacketContainer packet) {
        super(packet, TYPE);

        StructureModifier<Integer> integers = handle.getIntegers();

        this.entityId = integers.read(0);
        this.jumpBoost = integers.read(1);

        this.action = handle.getPlayerActions().readSafely(0);
    }

    /**
     * Retrieve Entity ID.
     * <p>
     * Notes: entity's ID
     *
     * @return The current Entity ID
     */
    public int getEntityID() {
        return entityId;
    }

    /**
     * Retrieve Action ID.
     * <p>
     * Notes: the ID of the action, see below.
     *
     * @return The current Action ID
     */
    public PlayerAction getAction() {
        return action;
    }

    /**
     * Retrieve Jump Boost.
     * <p>
     * Notes: horse jump boost. Ranged from 0 -> 100.
     *
     * @return The current Jump Boost
     */
    public int getJumpBoost() {
        return jumpBoost;
    }
}