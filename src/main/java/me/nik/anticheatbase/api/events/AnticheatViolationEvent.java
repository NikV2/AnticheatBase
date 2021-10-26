package me.nik.anticheatbase.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AnticheatViolationEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String checkName;
    private final String description;
    private final String type;
    private final String information;
    private final int vl;
    private final int maxVl;
    private final boolean experimental;
    private boolean cancel = false;

    /**
     * This event will always be called async, Beware.
     */
    public AnticheatViolationEvent(Player player, String checkName, String description, String type, String information,
                                   int vl, int maxVl, boolean experimental) {
        super(true);
        this.player = player;
        this.checkName = checkName;
        this.description = description;
        this.type = type;
        this.information = information;
        this.vl = vl;
        this.maxVl = maxVl;
        this.experimental = experimental;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * @return The check included in this event
     */
    public String getCheck() {
        return checkName;
    }

    /**
     * @return The check's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The type of the check included in this event
     */
    public String getType() {
        return type;
    }

    /**
     * @return The total violation amount
     */
    public int getVl() {
        return vl;
    }

    /**
     * @return The maximum violation amount
     */
    public int getMaxVl() {
        return maxVl;
    }

    /**
     * @return The information of why the player failed this check
     */
    public String getInformation() {
        return information;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return The player involved in this event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Whether the check is in an experimental state
     */
    public boolean isExperimental() {
        return experimental;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}