package me.nik.anticheatbase.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.nik.anticheatbase.wrappers.WrapperPlayClientUseEntity;

/**
 * A packet wrapper class that we'll use in order to make it easier for us to get the received or sent packet
 *
 * While also caching a timestamp
 *
 * NOTE: You may change this to fit with your own packet listener
 * For this example we'll be using ProtocolLib.
 */
public class Packet {

    private final PacketContainer packet;
    private final PacketType packetType;
    private final long timeStamp;

    public Packet(PacketContainer packet) {
        this.packet = packet;
        this.packetType = packet.getType();
        this.timeStamp = System.currentTimeMillis();
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public PacketContainer getPacket() {
        return packet;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public boolean isExplosion() {
        return this.packetType == PacketType.Play.Server.EXPLOSION;
    }

    public boolean isVehicleMove() {
        return this.packetType == PacketType.Play.Client.VEHICLE_MOVE;
    }

    public boolean isMovement() {
        return this.packetType == PacketType.Play.Client.POSITION
                || this.packetType == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isClientResourcePack() {
        return this.packetType == PacketType.Play.Client.RESOURCE_PACK_STATUS;
    }

    public boolean isPosition() {
        return this.packetType == PacketType.Play.Client.POSITION;
    }

    public boolean isRotation() {
        return this.packetType == PacketType.Play.Client.LOOK
                || this.packetType == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isLook() {
        return this.packetType == PacketType.Play.Client.LOOK;
    }

    public boolean isFlying() {
        return this.packetType == PacketType.Play.Client.FLYING
                || this.packetType == PacketType.Play.Client.POSITION
                || this.packetType == PacketType.Play.Client.POSITION_LOOK
                || this.packetType == PacketType.Play.Client.LOOK;
    }

    public boolean isClientTransaction() {
        return this.packetType == PacketType.Play.Client.TRANSACTION;
    }

    public boolean isServerTransaction() {
        return this.packetType == PacketType.Play.Server.TRANSACTION;
    }

    public boolean isClientCommand() {
        return this.packetType == PacketType.Play.Client.CLIENT_COMMAND;
    }

    public boolean isAction() {
        return this.packetType == PacketType.Play.Client.ENTITY_ACTION;
    }

    public boolean isSpectate() {
        return this.packetType == PacketType.Play.Client.SPECTATE;
    }

    public boolean isClientKeepAlive() {
        return this.packetType == PacketType.Play.Client.KEEP_ALIVE;
    }

    public boolean isServerKeepAlive() {
        return this.packetType == PacketType.Play.Server.KEEP_ALIVE;
    }

    public boolean isCreativeSlot() {
        return this.packetType == PacketType.Play.Client.SET_CREATIVE_SLOT;
    }

    public boolean isClientSettings() {
        return this.packetType == PacketType.Play.Client.SETTINGS;
    }

    public boolean isVelocity() {
        return this.packetType == PacketType.Play.Server.ENTITY_VELOCITY;
    }

    public boolean isBlockDig() {
        return this.packetType == PacketType.Play.Client.BLOCK_DIG;
    }

    public boolean isPositionLook() {
        return this.packetType == PacketType.Play.Client.POSITION_LOOK;
    }

    public boolean isClientHeldItemSlot() {
        return this.packetType == PacketType.Play.Client.HELD_ITEM_SLOT;
    }

    public boolean isWindowClick() {
        return this.packetType == PacketType.Play.Client.WINDOW_CLICK;
    }

    public boolean isSteerVehicle() {
        return this.packetType == PacketType.Play.Client.STEER_VEHICLE;
    }

    public boolean isBlockPlace() {
        return this.packetType == PacketType.Play.Client.BLOCK_PLACE;
    }

    public boolean isUseItem() {
        return this.packetType == PacketType.Play.Client.USE_ITEM;
    }

    public boolean isChat() {
        return this.packetType == PacketType.Play.Client.CHAT;
    }

    public boolean isArmAnimation() {
        return this.packetType == PacketType.Play.Client.ARM_ANIMATION;
    }

    public boolean isClientAbilities() {
        return this.packetType == PacketType.Play.Client.ABILITIES;
    }

    public boolean isServerAbilities() {
        return this.packetType == PacketType.Play.Server.ABILITIES;
    }

    public boolean isAttack() {
        return this.packetType == PacketType.Play.Client.USE_ENTITY
                && WrapperPlayClientUseEntity.getEntityUseAction(packet) == EnumWrappers.EntityUseAction.ATTACK;
    }

    public boolean isTeleport() {
        return this.packetType == PacketType.Play.Server.POSITION;
    }
}