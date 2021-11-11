package me.nik.anticheatbase.playerdata.data;

import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.wrappers.WrapperPlayClientEntityAction;

public class ActionData {

    private final Profile profile;
    private boolean sprinting, sneaking;
    private long lastActionPacket;

    public ActionData(final Profile profile) {
        this.profile = profile;
    }

    /**
     * NB: if i remember the player can choose or not to send this packet.
     */

    public void processPacket(final Packet packet) {
        if (packet.isAction()) {
            final WrapperPlayClientEntityAction wrapper = new WrapperPlayClientEntityAction(packet.getPacket());
            switch (wrapper.getAction()) {
                case START_SPRINTING:
                    this.sprinting = true;
                    break;
                case STOP_SPRINTING:
                    this.sprinting = false;
                    break;
                case START_SNEAKING:
                    this.sneaking = true;
                    break;
                case STOP_SNEAKING:
                    this.sneaking = false;
                    break;
            }

            this.lastActionPacket = System.currentTimeMillis();
        }
    }
}
