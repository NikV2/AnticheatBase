package me.nik.anticheatbase.playerdata.data.impl;

import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.playerdata.data.Data;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.utils.custom.desync.Desync;

public class ActionData implements Data {

    private final Profile profile;

    private final Desync desync;

    public ActionData(Profile profile) {
        this.profile = profile;

        this.desync = new Desync(profile);
    }

    @Override
    public void process(Packet packet) {
    }

    public Desync getDesync() {
        return desync;
    }
}