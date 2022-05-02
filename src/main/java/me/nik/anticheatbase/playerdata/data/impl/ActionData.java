package me.nik.anticheatbase.playerdata.data.impl;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.playerdata.data.Data;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.utils.MiscUtils;
import me.nik.anticheatbase.utils.custom.PlacedBlock;
import me.nik.anticheatbase.utils.custom.desync.Desync;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionData implements Data {

    private GameMode gameMode;

    private boolean allowFlight, sneaking;

    private final Desync desync;

    private PlacedBlock placedBlock;

    private ItemStack itemInMainHand = MiscUtils.EMPTY_ITEM, itemInOffHand = MiscUtils.EMPTY_ITEM;

    private int lastAllowFlightTicks, lastSleepingTicks, lastRidingTicks;

    /*
     * 1.9+
     */
    private int lastDuplicateOnePointSeventeenPacketTicks = 100;

    public ActionData(Profile profile) {

        this.desync = new Desync(profile);

        //Initialize

        Player player = profile.getPlayer();

        this.gameMode = player.getGameMode();

        this.allowFlight = Anticheat.getInstance().getNmsManager().getNmsInstance().getAllowFlight(player);

        this.lastAllowFlightTicks = this.allowFlight ? 0 : 100;
    }

    @Override
    public void process(Packet packet) {
        /*
        Handle the packet
         */
    }

    public int getLastRidingTicks() {
        return lastRidingTicks;
    }

    public PlacedBlock getPlacedBlock() {
        return placedBlock;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public ItemStack getItemInMainHand() {
        return itemInMainHand;
    }

    public ItemStack getItemInOffHand() {
        return itemInOffHand;
    }

    public Desync getDesync() {
        return desync;
    }

    public int getLastSleepingTicks() {
        return lastSleepingTicks;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getLastDuplicateOnePointSeventeenPacketTicks() {
        return lastDuplicateOnePointSeventeenPacketTicks;
    }
}