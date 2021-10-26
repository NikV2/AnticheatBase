package me.nik.anticheatbase.tasks;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.utils.MathUtils;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A task that we'll be using in order to grab the server's state and whatnot
 */
public class TickTask extends BukkitRunnable {

    private final Anticheat plugin;

    public TickTask(Anticheat plugin) {
        this.plugin = plugin;
    }

    private static int ticks;
    private static double tps = 20.0D;
    private static long tickTime, lastLagSpike;
    private int tpsTicks = 20;
    private long lastTime = System.currentTimeMillis();
    private long currentSec;

    @Override
    public void run() {

        //Increment current tick
        ticks++;

        //Handle server TPS and tick time
        server:
        {

            //The server's probably laggy at this early stage
            if (ticks < 100) break server;

            final long currentTime = System.currentTimeMillis();

            tickTime = currentTime - this.lastTime;

            this.lastTime = currentTime;

            final long sec = (currentTime / 1000L);

            if (this.currentSec == sec) {

                this.tpsTicks++;

            } else {

                this.currentSec = sec;

                tps = Math.min(MathUtils.decimalRound((tps + this.tpsTicks) / 2.0D, 2), 20.0D);

                this.tpsTicks = 1;
            }

            //Handle lag spikes
            if (tickTime >= 1000L) lastLagSpike = currentTime;
        }

        //Reset Data
        this.plugin.getProfileManager().getProfileMap().values().forEach(Profile::handleTick);
    }

    public static double getTPS() {
        return tps;
    }

    public static int getCurrentTick() {
        return Math.abs(ticks);
    }

    public static long getTickTime() {
        return tickTime;
    }

    public static long getLastLagSpike() {
        return MathUtils.elapsed(lastLagSpike);
    }
}