package me.nik.anticheatbase.playerdata;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.checks.types.BukkitCheck;
import me.nik.anticheatbase.checks.types.Check;
import me.nik.anticheatbase.checks.types.PacketCheck;
import me.nik.anticheatbase.files.Config;
import me.nik.anticheatbase.managers.CheckManager;
import me.nik.anticheatbase.managers.Permissions;
import me.nik.anticheatbase.managers.custom.Exempt;
import me.nik.anticheatbase.managers.threads.ProfileThread;
import me.nik.anticheatbase.playerdata.data.MovementData;
import me.nik.anticheatbase.playerdata.data.RotationData;
import me.nik.anticheatbase.processors.Packet;
import me.nik.anticheatbase.utils.ChatUtils;
import me.nik.anticheatbase.utils.TaskUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * A profile class containing every single information we need
 */
public class Profile {

    //Data
    private final MovementData movementData = new MovementData(this);
    private final RotationData rotationData = new RotationData(this);

    //Checks
    private List<PacketCheck> packetChecks;
    private List<BukkitCheck> bukkitChecks;

    //Other Information
    private Player player;
    private UUID uuid;
    private boolean bypass;
    private String client = "Unknown";

    //Thread
    private ProfileThread profileThread;

    //Exempt
    private final Exempt exempt = new Exempt(this);

    public Profile(Player player) {

        //NPC's or any type of virtual players will be null
        if (player == null) return;

        this.player = player;

        this.uuid = player.getUniqueId();

        //Cache it for perfomance reasons
        this.bypass = !Config.Setting.DISABLE_BYPASS_PERMISSION.getBoolean()
                && player.hasPermission(Permissions.BYPASS.getPermission());

        //Find an available profile thread
        this.profileThread = Anticheat.getInstance().getThreadManager().getAvailableProfileThread();

        CheckManager checkManager = Anticheat.getInstance().getCheckManager();

        /*
        Initialize all of our checks
        This can be quite heavy especially if you have a lot of checks

        If you find this heavy feel free to change our current CheckManager
        And make the checks not automatically register.

        Alternatively you can register the checks by using an executor which can also work i guess?
         */
        this.packetChecks = checkManager.getPacketChecks(this);
        this.bukkitChecks = checkManager.getBukkitChecks(this);
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void handlePacket(Packet packet) {

        if (this.bypass || this.packetChecks.isEmpty()) return;

        //Process data
        this.movementData.processPacket(packet);

        //Handle exempts
        this.exempt.handleExempts();

        /*
        Run checks

        This might look ugly, However from my research and a LOT of testing.
        This is the fastest way to loop through heavy objects especially when using an ArrayList

        You're welcome to change this into a forEach or whatever you prefer, However from my testing
        This will always be faster than any type of looping method.
         */
        for (int i = 0; i < this.packetChecks.size(); i++) this.packetChecks.get(i).handle(packet);
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void handleBukkit(Event event) {

        if (this.bypass || this.bukkitChecks.isEmpty()) return;

        //Process data

        //Handle exempts
        this.exempt.handleExempts();

        /*
        Run checks

        This might look ugly, However from my research and a LOT of testing.
        This is the fastest way to loop through heavy objects especially when using an ArrayList

        You're welcome to change this into a forEach or whatever you prefer, However from my testing
        This will always be faster than any type of looping method.
         */
        for (int i = 0; i < this.bukkitChecks.size(); i++) this.bukkitChecks.get(i).handle(event);
    }

    public void handleTick() {

        //Handle tick here
    }

    public List<Check> getAllChecks() {

        final List<Check> checks = new LinkedList<>();

        checks.addAll(this.packetChecks);
        checks.addAll(this.bukkitChecks);

        return checks;
    }

    public MovementData getMovementData() {
        return movementData;
    }

    public RotationData getRotationData() {
        return rotationData;
    }

    public void kick(String reason) {

        Player p = getPlayer();

        if (p == null) return;

        TaskUtils.task(() -> p.kickPlayer(ChatUtils.format(reason)));
    }

    public Exempt isExempt() {
        return exempt;
    }

    public ProfileThread getProfileThread() {
        return profileThread;
    }

    public Player getPlayer() {
        if (this.player == null) this.player = Bukkit.getPlayer(getUUID());

        return this.player;
    }

    public UUID getUUID() {
        if (this.uuid == null) this.uuid = this.player.getUniqueId();

        return this.uuid;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}