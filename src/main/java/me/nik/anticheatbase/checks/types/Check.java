package me.nik.anticheatbase.checks.types;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.api.events.AnticheatViolationEvent;
import me.nik.anticheatbase.checks.annotations.Experimental;
import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.files.commentedfiles.CommentedFileConfiguration;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.utils.MiscUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Abstract class for Checks
 */
public abstract class Check {

    protected final Profile profile;

    private final boolean enabled;

    private final int maxVl;

    private final Set<String> commands = new LinkedHashSet<>();

    private final String checkName, checkType, description, fullCheckName;

    private int vl;

    private final boolean experimental;

    private float buffer;

    public String getFullCheckName() {
        return fullCheckName;
    }

    protected void debug(Object info) {
        Bukkit.broadcastMessage(String.valueOf(info));
    }

    public Check(Profile profile, CheckType check, String type, String description) {

        this.profile = profile;
        this.checkName = check.getCheckName();
        this.checkType = type;
        this.description = description;

        final CommentedFileConfiguration config = Anticheat.getInstance().getChecks();
        final String checkName = this.checkName.toLowerCase();
        final String checkType = type.toLowerCase().replace(" ", "_");

        boolean isEnabled;

        if (type.isEmpty()) {
            isEnabled = config.getBoolean(checkName + ".enabled");
        } else {
            isEnabled = config.getBoolean(checkName + "." + checkType + ".enabled",
                    config.getBoolean(checkName + "." + checkType));
        }

        this.enabled = isEnabled;

        this.maxVl = config.getInt(checkName + ".max_vl");

        this.commands.addAll(config.getStringList(checkName + ".commands"));

        this.experimental = this.getClass().isAnnotationPresent(Experimental.class);

        this.fullCheckName = this.checkName + (type.isEmpty() ? "" : (" (" + type + ")"));
    }

    public void fail(String verbose) {

        if (this.vl < 0) this.vl = 0;

        final Player p = profile.getPlayer();

        if (p == null) return;

        AnticheatViolationEvent violationEvent = new AnticheatViolationEvent(
                p,
                this.checkName,
                this.description,
                this.checkType,
                verbose,
                this.vl++,
                this.maxVl,
                this.experimental);

        Bukkit.getPluginManager().callEvent(violationEvent);

        if (violationEvent.isCancelled() || this.experimental) {

            this.vl--;

            return;
        }

        if (this.vl >= this.maxVl) {

            final String playerName = p.getName();

            for (String command : this.commands) {

                MiscUtils.consoleCommand(command.replace("%player%", playerName));
            }

            this.vl = 0;

            this.buffer = 0;
        }
    }

    public void setVl(int vl) {
        this.vl = vl;
    }

    public void resetVl() {
        this.vl = 0;
    }

    public int getVl() {
        return vl;
    }

    protected float increaseBuffer() {
        return buffer++;
    }

    protected float increaseBufferBy(double amount) {
        return buffer += amount;
    }

    protected float decreaseBuffer() {
        return buffer == 0 ? 0 : (buffer = Math.max(0, buffer - 1));
    }

    protected float decreaseBufferBy(double amount) {
        return buffer == 0 ? 0 : (buffer = (float) Math.max(0, buffer - amount));
    }

    protected void resetBuffer() {
        buffer = 0;
    }

    protected float getBuffer() {
        return buffer;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCheckName() {
        return this.checkName;
    }

    public String getCheckType() {
        return this.checkType;
    }

    protected long now() {
        return System.currentTimeMillis();
    }
}