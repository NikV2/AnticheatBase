package me.nik.anticheatbase.playerdata.data.impl;

import me.nik.anticheatbase.playerdata.data.Data;
import me.nik.anticheatbase.processors.Packet;

public class TeleportData implements Data {

    private int teleportTicks;

    @Override
    public void process(Packet packet) {
        /*
        Handle the packet
         */
    }

    public int getTeleportTicks() {
        return teleportTicks;
    }
}