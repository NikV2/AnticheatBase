package me.nik.anticheatbase.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * A small utility class to use for nearby blocks and such
 */
public final class CollisionUtils {

    private CollisionUtils() {
    }

    /**
     * An efficient way of getting if the player is against a wall without collisions.
     *
     * @param location The location
     * @return Whether or not the player is against a wall
     */
    public static boolean isNearWall(final Location location) {

        final double x = Math.abs(location.getX() % 1);
        final double z = Math.abs(location.getZ() % 1);

        return (x > .6999999D && x < .7D) || (z > .6999999D && z < .7D)
                || (x > .300000011D && x < .300000012D) || (z > .300000011D && z < .300000012D);
    }

    public static boolean isChunkUnloaded(final Location location) {
        return !location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    private static Block getBlockAsync(final Location location) {
        return !isChunkUnloaded(location) ? location.getBlock() : null;
    }

    private static Block getBlockAsync(final World world, final int blockX, final int blockY, final int blockZ) {
        return world.isChunkLoaded(blockX >> 4, blockZ >> 4)
                ? world.getBlockAt(blockX, blockY, blockZ)
                : null;
    }

    public static Block getBlock(final Location location, boolean async) {
        return async ? getBlockAsync(location) : location.getBlock();
    }

    public static Block getBlock(final World world, final int blockX, final int blockY, final int blockZ, boolean async) {
        return async ? getBlockAsync(world, blockX, blockY, blockZ) : world.getBlockAt(blockX, blockY, blockZ);
    }
}