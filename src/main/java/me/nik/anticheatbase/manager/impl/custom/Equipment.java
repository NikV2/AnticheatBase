package me.nik.anticheatbase.manager.impl.custom;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * A simple equipment class holding a profile's equipment
 * <p>
 * Surprisingly getting the player's equipment every single tick can hinder perfomance
 * And it does add up and make a difference on a big playercount.
 * <p>
 * With this class we'll be getting all of them every 5 ticks in order to save perfomance.
 */
public class Equipment {

    private static final ItemStack EMPTY = new ItemStack(Material.AIR);

    private ItemStack
            boots = EMPTY,
            chestplate = EMPTY;

    private int ticks;

    public void handle(Player player) {

        //Perfomance
        if (this.ticks++ < 5) return;

        PlayerInventory inventory = player.getInventory();

        ItemStack boots = inventory.getBoots();
        this.boots = boots != null ? boots : EMPTY;

        ItemStack chestplate = inventory.getChestplate();
        this.chestplate = chestplate != null ? chestplate : EMPTY;

        this.ticks = 0;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }
}