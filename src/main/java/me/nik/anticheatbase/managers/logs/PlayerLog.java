package me.nik.anticheatbase.managers.logs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerLog {

    private static final String CACHED_DATE = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());

    private final String server;
    private final String player;
    private final String uuid;
    private final String check;
    private final String information;
    private final String timeStamp;

    public PlayerLog(String server, String player, String uuid, String check, String information) {
        this.server = server;
        this.player = player;
        this.uuid = uuid;
        this.check = check;
        this.information = information.length() > 50 ? information.substring(0, 50) : information; //Fixes issues with databases
        this.timeStamp = CACHED_DATE;
    }

    public PlayerLog(String server, String player, String uuid, String check, String information, String timeStamp) {
        this.server = server;
        this.player = player;
        this.uuid = uuid;
        this.check = check;
        this.information = information;
        this.timeStamp = timeStamp;
    }

    public String getServer() {
        return server;
    }

    public String getPlayer() {
        return player;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCheck() {
        return check;
    }

    public String getInformation() {
        return information;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return this.server + ","
                + this.player + ","
                + this.uuid + ","
                + this.check + ","
                + this.information.replace(",", "") + ","
                + this.timeStamp;
    }
}