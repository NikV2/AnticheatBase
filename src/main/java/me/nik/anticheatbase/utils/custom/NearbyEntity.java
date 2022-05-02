package me.nik.anticheatbase.utils.custom;

import org.bukkit.entity.Entity;

/**
 * A nearby entity class storing all the information we need about nearby entities
 * So we can later use this in case we need to check if an specific entity is nearby
 * Or within a certain distance.
 */
public class NearbyEntity {

    private final Entity entity;
    private final double horizontalDistance, verticalDistance;

    public NearbyEntity(Entity entity, double horizontalDistance, double verticalDistance) {
        this.entity = entity;
        this.horizontalDistance = horizontalDistance;
        this.verticalDistance = verticalDistance;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    public double getVerticalDistance() {
        return verticalDistance;
    }
}