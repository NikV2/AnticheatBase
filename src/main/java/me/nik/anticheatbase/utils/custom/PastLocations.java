package me.nik.anticheatbase.utils.custom;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple tool that we're going to be using on our Reach + Hitbox Checks.
 */
public class PastLocations {

    private final ConcurrentSampleList<CustomLocation> pastLocations = new ConcurrentSampleList<>(20, true);

    /**
     * @param timeStamp Usually the player's ping
     * @param delta     The locations to look for, Lower delta = less locations, more sensitive
     * @return The estimated locations
     */
    public List<CustomLocation> getEstimatedLocationsFromPing(long currentTime, long timeStamp, long delta) {

        final List<CustomLocation> locations = new LinkedList<>();

        final long deltaTime = currentTime - timeStamp;

        for (CustomLocation location : this.pastLocations) {

            if (Math.abs(deltaTime - location.getTimeStamp()) < delta) {

                locations.add(location);
            }
        }

        if (locations.isEmpty()) locations.add(this.pastLocations.getLast());

        return locations;
    }

    /**
     * @param timeStamp Usually the player's ping
     * @param delta     The locations to look for, Lower delta = less locations, more sensitive
     * @return The estimated locations as vectors
     */
    public List<Vector> getEstimatedVectorsFromPing(long currentTime, long timeStamp, long delta) {

        final List<Vector> locations = new LinkedList<>();

        final long deltaTime = currentTime - timeStamp;

        for (CustomLocation location : this.pastLocations) {

            if (Math.abs(deltaTime - location.getTimeStamp()) < delta) {

                locations.add(location.toVector());
            }
        }

        if (locations.isEmpty()) locations.add(this.pastLocations.getLast().toVector());

        return locations;
    }

    public boolean isCollected() {
        return this.pastLocations.isCollected();
    }

    public void clear() {
        this.pastLocations.clear();
    }

    public void addLocation(Location location) {
        this.pastLocations.add(new CustomLocation(location));
    }

    public ConcurrentSampleList<CustomLocation> getPastLocations() {
        return pastLocations;
    }
}