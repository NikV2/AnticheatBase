package me.nik.anticheatbase.utils.custom.desync;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.tasks.TickTask;
import me.nik.anticheatbase.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A simple class that is going to help us handle and fix any type of desync with the client
 */
public class Desync {

    private static final Inventory CACHED_INVENTORY = Bukkit.createInventory(null, InventoryType.PLAYER);
    private final Profile profile;
    private int lastFixedTicks;

    public Desync(Profile profile) {
        this.profile = profile;
    }

    public void fix(DesyncType desyncType) {

        //Make sure this method isn't being spammed by any check that hasn't updated its status yet
        if (MathUtils.elapsedTicks(this.lastFixedTicks) < 15) return;

        final Player player = profile.getPlayer();

        switch (desyncType) {

            case BLOCKING:

                final PlayerInventory inventory = player.getInventory();

                final int currentSlot = inventory.getHeldItemSlot();

                final int nextSlot = currentSlot == 0 ? currentSlot + 1 : currentSlot - 1;

                inventory.setHeldItemSlot(nextSlot);

                break;

            case SNEAKING:
            case SPRINTING:

                /*
                We need to do this on the main thread due to async catchers
                The reason we're doing it one by one after a tick
                Is due to certain clients not properly un-sneaking, un-sprinting
                If this gets executed instantly.
                 */
                new BukkitRunnable() {

                    int state = 0;

                    @Override
                    public void run() {

                        switch (state++) {

                            case 1:

                                /*
                                Close the inventory first to make sure they're not using any type
                                Of inventory move alongside noslow.
                                 */
                                player.closeInventory();

                                break;

                            case 2:

                                /*
                                Open an empty inventory in order to force them
                                To un-sprint and un-sneak.
                                 */
                                player.openInventory(CACHED_INVENTORY);

                                break;

                            case 3:

                                /*
                                Close the inventory and cancel the task
                                 */
                                player.closeInventory();

                                this.cancel();

                                break;
                        }
                    }
                }.runTaskTimer(Anticheat.getInstance(), 0, 0);

                break;
        }

        this.lastFixedTicks = TickTask.getCurrentTick();
    }
}