package me.nik.anticheatbase.playerdata.data;

import me.nik.anticheatbase.processors.Packet;

public interface Data {
    void process(Packet packet);
}