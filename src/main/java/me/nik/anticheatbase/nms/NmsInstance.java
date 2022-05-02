package me.nik.anticheatbase.nms;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NmsInstance {

    float getAttackCooldown(Player player);

    boolean isChunkLoaded(World world, int x, int z);

    Material getType(Block block);

    Entity[] getChunkEntities(World world, int x, int z);

    boolean isWaterLogged(Block block);

    boolean isDead(Player player);

    boolean isSleeping(Player player);

    boolean isGliding(Player player);

    boolean isInsideVehicle(Player player);

    boolean isRiptiding(Player player);

    boolean isBlocking(Player player);

    boolean isSneaking(Player player);

    ItemStack getItemInMainHand(Player player);

    ItemStack getItemInOffHand(Player player);

    float getWalkSpeed(Player player);

    float getAttributeSpeed(Player player);

    boolean getAllowFlight(Player player);

    boolean isFlying(Player player);

    float getFallDistance(Player player);
}