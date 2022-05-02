package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import me.nik.anticheatbase.utils.ServerUtils;

public class WrapperPlayClientWindowClick extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.WINDOW_CLICK;

    private final int slot;

    public WrapperPlayClientWindowClick(PacketContainer packet) {
        super(packet, TYPE);

        this.slot = handle.getIntegers().read(ServerUtils.isCavesUpdate() ? 2 : 1);
    }

    /**
     * Retrieve Slot.
     * <p>
     * Notes: the clicked slot. See below.
     *
     * @return The current Slot
     */
    public int getSlot() {
        return slot;
    }
}