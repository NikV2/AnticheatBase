package me.nik.anticheatbase.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.nik.anticheatbase.wrappers.WrapperPlayClientBlockDig;
import me.nik.anticheatbase.wrappers.WrapperPlayClientChat;
import me.nik.anticheatbase.wrappers.WrapperPlayClientEntityAction;
import me.nik.anticheatbase.wrappers.WrapperPlayClientLook;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPosition;
import me.nik.anticheatbase.wrappers.WrapperPlayClientPositionLook;
import me.nik.anticheatbase.wrappers.WrapperPlayClientUseEntity;
import me.nik.anticheatbase.wrappers.WrapperPlayClientWindowClick;

/**
 * A packet wrapper class that we'll use in order to make it easier for us to get the received or sent packet
 * <p>
 * While also caching the possible wrappers that we need, along with a timestamp.
 * <p>
 * This packet class is made in order to cache every single thing (or possible things)
 * That we're going to be using for our checks and processors
 * Instead of checking (packetType == POSITION_LOOK) over and over again.
 * Our wrappers also cache the values it receives, so it won't have to use reflection
 * Over and over again (Although ProtocolLib handles it efficiently by itself after a while)
 */
public class Packet {

    private final PacketContainer container;
    private final Type type;
    private final long timeStamp;

    /*
    Use entity cache
     */
    private WrapperPlayClientUseEntity useEntityWrapper;
    private boolean attack;

    /*
    Block Dig cache
     */
    private WrapperPlayClientBlockDig blockDigWrapper;

    /*
    Window Click cache
     */
    private WrapperPlayClientWindowClick windowClickWrapper;

    /*
    Entity Action cache
     */
    private WrapperPlayClientEntityAction entityActionWrapper;

    /*
    Chat cache
     */
    private WrapperPlayClientChat chatWrapper;

    /*
    Movement - Flying cache
     */
    private WrapperPlayClientPosition positionWrapper;
    private WrapperPlayClientPositionLook positionLookWrapper;
    private WrapperPlayClientLook lookWrapper;
    private boolean movement, rotation, flying;

    public Packet(PacketContainer container, long timeStamp) {
        this.container = container;
        this.timeStamp = timeStamp;

        switch (this.type = Type.fromContainer(container)) {

            case USE_ENTITY:

                this.useEntityWrapper = new WrapperPlayClientUseEntity(container);

                this.attack = this.useEntityWrapper.getType() == EnumWrappers.EntityUseAction.ATTACK;

                break;

            case BLOCK_DIG:

                this.blockDigWrapper = new WrapperPlayClientBlockDig(container);

                break;

            case WINDOW_CLICK:

                this.windowClickWrapper = new WrapperPlayClientWindowClick(container);

                break;

            case ENTITY_ACTION:

                this.entityActionWrapper = new WrapperPlayClientEntityAction(container);

                break;

            case CHAT:

                this.chatWrapper = new WrapperPlayClientChat(container);

                break;

            case FLYING:
            case GROUND:

                this.flying = true;

                break;

            case POSITION:

                this.positionWrapper = new WrapperPlayClientPosition(container);

                this.flying = this.movement = true;

                break;

            case POSITION_LOOK:

                this.positionLookWrapper = new WrapperPlayClientPositionLook(container);

                this.flying = this.movement = this.rotation = true;

                break;

            case LOOK:

                this.lookWrapper = new WrapperPlayClientLook(container);

                this.flying = this.rotation = true;

                break;
        }
    }

    public boolean isAttack() {
        return attack;
    }

    public boolean isMovement() {
        return movement;
    }

    public boolean isRotation() {
        return rotation;
    }

    public boolean isFlying() {
        return flying;
    }

    public WrapperPlayClientUseEntity getUseEntityWrapper() {
        return useEntityWrapper;
    }

    public WrapperPlayClientBlockDig getBlockDigWrapper() {
        return blockDigWrapper;
    }

    public WrapperPlayClientWindowClick getWindowClickWrapper() {
        return windowClickWrapper;
    }

    public WrapperPlayClientEntityAction getEntityActionWrapper() {
        return entityActionWrapper;
    }

    public WrapperPlayClientChat getChatWrapper() {
        return chatWrapper;
    }

    public WrapperPlayClientPosition getPositionWrapper() {
        return positionWrapper;
    }

    public WrapperPlayClientPositionLook getPositionLookWrapper() {
        return positionLookWrapper;
    }

    public WrapperPlayClientLook getLookWrapper() {
        return lookWrapper;
    }

    public boolean is(Type type) {
        return this.type == type;
    }

    public PacketContainer getContainer() {
        return container;
    }

    public Type getType() {
        return type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public enum Type {
        /*
        ClientBound
         */
        FLYING,
        GROUND,
        POSITION,
        POSITION_LOOK,
        LOOK,
        USE_ENTITY,
        ABILITIES,
        ARM_ANIMATION,
        BLOCK_DIG,
        BLOCK_PLACE,
        CHAT,
        ENTITY_ACTION,
        KEEP_ALIVE,
        PONG,
        TRANSACTION,
        RESOURCE_PACK_STATUS,
        SETTINGS,
        SPECTATE,
        STEER_VEHICLE,
        USE_ITEM,
        VEHICLE_MOVE,
        WINDOW_CLICK,
        SET_CREATIVE_SLOT,
        HELD_ITEM_SLOT,
        /*
        ServerBound
         */
        SERVER_EXPLOSION,
        SERVER_ENTITY_VELOCITY,
        SERVER_KEEP_ALIVE,
        SERVER_ABILITIES,
        SERVER_POSITION,
        SERVER_PING,
        SERVER_TRANSACTION,
        SERVER_REMOVE_ENTITY_EFFECT,
        SERVER_ENTITY_EFFECT,
        SERVER_UPDATE_ATTRIBUTES;

        private static Type fromContainer(PacketContainer container) {

            PacketType type = container.getType();

            return Type.valueOf(type.isClient() ? type.name() : ("SERVER_" + type.name()));
        }
    }
}