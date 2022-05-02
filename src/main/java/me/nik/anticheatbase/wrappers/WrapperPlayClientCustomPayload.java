package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import io.netty.buffer.ByteBuf;
import me.nik.anticheatbase.utils.ServerUtils;

public class WrapperPlayClientCustomPayload extends PacketWrapper {
    public static final PacketType TYPE = PacketType.Play.Client.CUSTOM_PAYLOAD;

    public WrapperPlayClientCustomPayload() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayClientCustomPayload(PacketContainer packet) {
        super(packet, TYPE);
    }

    public String getChannel() {
        return ServerUtils.isLegacy()
                ? handle.getStrings().readSafely(0)
                : handle.getMinecraftKeys().readSafely(0).getFullKey();
    }

    /**
     * Retrieve payload contents as a raw Netty buffer
     *
     * @return Payload contents as a Netty buffer
     */
    public ByteBuf getContentsBuffer() {
        return (ByteBuf) handle.getModifier().withType(ByteBuf.class).read(0);
    }

    /**
     * Retrieve payload contents
     *
     * @return Payload contents as a byte array
     */
    public byte[] getContents() {
        ByteBuf buffer = getContentsBuffer().copy();
        byte[] array = new byte[buffer.readableBytes()];
        buffer.readBytes(array);
        return array;
    }
}