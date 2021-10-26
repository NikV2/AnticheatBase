package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import java.util.Arrays;

public class WrapperPlayServerChat extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.CHAT;

    public WrapperPlayServerChat() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerChat(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve the chat message.
     * <p>
     * Limited to 32767 bytes
     *
     * @return The current message
     */
    public WrappedChatComponent getMessage() {
        return handle.getChatComponents().read(0);
    }

    /**
     * Set the message.
     *
     * @param value - new value.
     */
    public void setMessage(WrappedChatComponent value) {
        handle.getChatComponents().write(0, value);
    }

    public EnumWrappers.ChatType getChatType() {
        return handle.getChatTypes().read(0);
    }

    public void setChatType(EnumWrappers.ChatType type) {
        handle.getChatTypes().write(0, type);
    }

    /**
     * Retrieve Position.
     * <p>
     * Notes: 0 - Chat (chat box) ,1 - System Message (chat box), 2 - Above
     * action bar
     *
     * @return The current Position
     * @deprecated Magic values replaced by enum
     */
    @Deprecated
    public byte getPosition() {
        Byte position = handle.getBytes().readSafely(0);
        if (position != null) {
            return position;
        } else {
            return getChatType().getId();
        }
    }

    /**
     * Set Position.
     *
     * @param value - new value.
     * @deprecated Magic values replaced by enum
     */
    @Deprecated
    public void setPosition(byte value) {
        handle.getBytes().writeSafely(0, value);

        if (EnumWrappers.getChatTypeClass() != null) {
            Arrays.stream(EnumWrappers.ChatType.values()).filter(t -> t.getId() == value).findAny()
                    .ifPresent(t -> handle.getChatTypes().writeSafely(0, t));
        }
    }
}