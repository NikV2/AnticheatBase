package me.nik.anticheatbase.tasks;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.checks.types.Check;
import me.nik.anticheatbase.playerdata.Profile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * A task that we'll be using in order to clear the profile violations
 */
public class ViolationTask extends BukkitRunnable {

    private final Anticheat plugin;

    public ViolationTask(Anticheat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for (final Profile profile : this.plugin.getProfileManager().getProfileMap().values()) {

            final List<Check> checks = profile.getAllChecks();

            //Can happen
            if (checks.isEmpty()) continue;

            profile.getAllChecks().forEach(Check::resetVl);
        }
    }
}