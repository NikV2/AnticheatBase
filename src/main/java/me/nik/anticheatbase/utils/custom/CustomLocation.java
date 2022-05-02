package me.nik.anticheatbase.utils.custom;

import me.nik.anticheatbase.utils.fastmath.FastMath;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class CustomLocation {

    private final World world;
    private final float yaw, pitch;
    private final long timeStamp;
    private double x, y, z;
    private int blockX, blockY, blockZ;

    public CustomLocation(World world, double x, double y, double z, float yaw, float pitch, long timeStamp) {

        this.world = world;

        this.blockX = FastMath.floorInt(this.x = x);
        this.blockY = FastMath.floorInt(this.y = y);
        this.blockZ = FastMath.floorInt(this.z = z);

        this.yaw = yaw;
        this.pitch = pitch;

        this.timeStamp = timeStamp;
    }

    public CustomLocation(World world, double x, double y, double z, float yaw, float pitch) {

        this.world = world;

        this.blockX = FastMath.floorInt(this.x = x);
        this.blockY = FastMath.floorInt(this.y = y);
        this.blockZ = FastMath.floorInt(this.z = z);

        this.yaw = yaw;
        this.pitch = pitch;

        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(World world, double x, double y, double z) {

        this.world = world;

        this.blockX = FastMath.floorInt(this.x = x);
        this.blockY = FastMath.floorInt(this.y = y);
        this.blockZ = FastMath.floorInt(this.z = z);

        this.yaw = this.pitch = 0.0F;

        this.timeStamp = System.currentTimeMillis();
    }

    public CustomLocation(Location location) {

        this.world = location.getWorld();

        this.blockX = FastMath.floorInt(this.x = location.getX());
        this.blockY = FastMath.floorInt(this.y = location.getY());
        this.blockZ = FastMath.floorInt(this.z = location.getZ());

        this.yaw = location.getYaw();
        this.pitch = location.getPitch();

        this.timeStamp = System.currentTimeMillis();
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public Location toBukkit() {
        return new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public CustomLocation clone() {
        return new CustomLocation(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.blockX = FastMath.floorInt(this.x = x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.blockY = FastMath.floorInt(this.y = y);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.blockZ = FastMath.floorInt(this.z = z);
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public double distance(CustomLocation other) {
        return FastMath.sqrt(this.distanceSquared(other));
    }

    public double distance(Location other) {
        return FastMath.sqrt(this.distanceSquared(other));
    }

    public double distanceSquared(CustomLocation other) {

        double distX = this.x - other.getX();
        double distY = this.y - other.getY();
        double distZ = this.z - other.getZ();

        return (distX * distX) + (distY * distY) + (distZ * distZ);
    }

    public double distanceSquared(Location other) {

        double distX = this.x - other.getX();
        double distY = this.y - other.getY();
        double distZ = this.z - other.getZ();

        return (distX * distX) + (distY * distY) + (distZ * distZ);
    }

    public double length() {
        return FastMath.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    }

    public Block getBlock() {
        return this.world.getBlockAt(this.blockX, this.blockY, this.blockZ);
    }

    public float getAngle(Vector other) {

        final double dot = Math.min(Math.max(
                        (this.x * other.getX() + this.y * other.getY() + this.z * other.getZ())
                                / (this.length() * other.length()),
                        -1.0),
                1.0);

        return (float) FastMath.acos(dot);
    }

    public Vector getDirection() {

        Vector vector = new Vector();

        double x = FastMath.toRadians(this.pitch);

        vector.setY(-FastMath.sin(x));

        double xz = FastMath.cos(x);

        double radiansRotX = FastMath.toRadians(this.yaw);

        vector.setX(-xz * FastMath.sin(radiansRotX));
        vector.setZ(xz * FastMath.cos(radiansRotX));

        return vector;
    }

    public CustomLocation add(CustomLocation loc) {
        this.blockX = FastMath.floorInt(this.x += loc.getX());
        this.blockY = FastMath.floorInt(this.y += loc.getY());
        this.blockZ = FastMath.floorInt(this.z += loc.getZ());
        return this;
    }

    public CustomLocation add(Location loc) {
        this.blockX = FastMath.floorInt(this.x += loc.getX());
        this.blockY = FastMath.floorInt(this.y += loc.getY());
        this.blockZ = FastMath.floorInt(this.z += loc.getZ());
        return this;
    }

    public CustomLocation add(Vector vec) {
        this.blockX = FastMath.floorInt(this.x += vec.getX());
        this.blockY = FastMath.floorInt(this.y += vec.getY());
        this.blockZ = FastMath.floorInt(this.z += vec.getZ());
        return this;
    }

    public CustomLocation add(double x, double y, double z) {
        this.blockX = FastMath.floorInt(this.x += x);
        this.blockY = FastMath.floorInt(this.y += y);
        this.blockZ = FastMath.floorInt(this.z += z);
        return this;
    }

    public CustomLocation subtract(CustomLocation loc) {
        this.blockX = FastMath.floorInt(this.x -= loc.getX());
        this.blockY = FastMath.floorInt(this.y -= loc.getY());
        this.blockZ = FastMath.floorInt(this.z -= loc.getZ());
        return this;
    }

    public CustomLocation subtract(Location loc) {
        this.blockX = FastMath.floorInt(this.x -= loc.getX());
        this.blockY = FastMath.floorInt(this.y -= loc.getY());
        this.blockZ = FastMath.floorInt(this.z -= loc.getZ());
        return this;
    }

    public CustomLocation subtract(Vector vec) {
        this.blockX = FastMath.floorInt(this.x -= vec.getX());
        this.blockY = FastMath.floorInt(this.y -= vec.getY());
        this.blockZ = FastMath.floorInt(this.z -= vec.getZ());
        return this;
    }

    public CustomLocation subtract(double x, double y, double z) {
        this.blockX = FastMath.floorInt(this.x -= x);
        this.blockY = FastMath.floorInt(this.y -= y);
        this.blockZ = FastMath.floorInt(this.z -= z);
        return this;
    }
}