package me.nik.anticheatbase.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import me.nik.anticheatbase.utils.ServerVersion;

public class WrapperPlayClientWindowClick extends PacketWrapper {

    public static final PacketType TYPE = PacketType.Play.Client.WINDOW_CLICK;

    private final int slot;

    public WrapperPlayClientWindowClick(PacketContainer packet) {
        super(packet, TYPE);

        this.slot = handle.getIntegers().read(ServerVersion.getVersion().isLowerThan(ServerVersion.v1_17_R1) ? 2 : 1);
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