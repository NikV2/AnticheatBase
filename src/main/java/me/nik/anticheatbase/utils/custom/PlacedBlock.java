package me.nik.anticheatbase.utils.custom;

import org.bukkit.block.BlockFace;

public class PlacedBlock {

    private final CustomLocation location;
    private final BlockFace face;

    public PlacedBlock(CustomLocation location, BlockFace face) {
        this.location = location;
        this.face = face;
    }

    public CustomLocation getLocation() {
        return location;
    }

    public BlockFace getFace() {
        return face;
    }
}