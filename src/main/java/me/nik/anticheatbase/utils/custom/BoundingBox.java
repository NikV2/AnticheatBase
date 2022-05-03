package me.nik.anticheatbase.utils.custom;

import me.nik.anticheatbase.utils.ServerVersion;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * A slightly better BoundingBox than bukkit's
 * And a better RayTrace
 */
public class BoundingBox {

    private double minX, minY, minZ, maxX, maxY, maxZ;

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        resize(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static BoundingBox fromBukkit(Entity entity) {
        /*
        Not supported in legacy versions
         */
        if (ServerVersion.getVersion().isLowerThan(ServerVersion.v1_13_R1)) return null;

        org.bukkit.util.BoundingBox bukkitBox = entity.getBoundingBox();

        return new BoundingBox(
                bukkitBox.getMinX(), bukkitBox.getMinY(), bukkitBox.getMinZ(),
                bukkitBox.getMaxX(), bukkitBox.getMaxY(), bukkitBox.getMaxZ()
        );
    }

    public static BoundingBox fromPlayerLocation(CustomLocation loc) {

        final double x = loc.getX();
        final double y = loc.getY();
        final double z = loc.getZ();

        final double minX = x - 0.3D;
        final double minZ = z - 0.3D;

        final double maxX = x + 0.3D;
        final double maxY = y + 1.8D;
        final double maxZ = z + 0.3D;

        return new BoundingBox(minX, y, minZ, maxX, maxY, maxZ);
    }

    public static BoundingBox fromPlayerVector(Vector vec) {

        final double x = vec.getX();
        final double y = vec.getY();
        final double z = vec.getZ();

        final double minX = x - 0.3D;
        final double minZ = z - 0.3D;

        final double maxX = x + 0.3D;
        final double maxY = y + 1.8D;
        final double maxZ = z + 0.3D;

        return new BoundingBox(minX, y, minZ, maxX, maxY, maxZ);
    }

    public static BoundingBox of(Vector min, Vector max) {
        return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    public static BoundingBox of(Location min, Location max) {
        return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    public static BoundingBox of(Block min, Block max) {
        final int x1 = min.getX();
        final int y1 = min.getY();
        final int z1 = min.getZ();
        final int x2 = max.getX();
        final int y2 = max.getY();
        final int z2 = max.getZ();

        final int minX = Math.min(x1, x2);
        final int minY = Math.min(y1, y2);
        final int minZ = Math.min(z1, z2);
        final int maxX = Math.max(x1, x2) + 1;
        final int maxY = Math.max(y1, y2) + 1;
        final int maxZ = Math.max(z1, z2) + 1;

        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static BoundingBox of(Block block) {
        return new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX() + 1, block.getY() + 1, block.getZ() + 1);
    }

    public static BoundingBox of(Vector center, double x, double y, double z) {
        return new BoundingBox(center.getX() - x, center.getY() - y, center.getZ() - z, center.getX() + x, center.getY() + y, center.getZ() + z);
    }

    public static BoundingBox of(Location center, double x, double y, double z) {
        return new BoundingBox(center.getX() - x, center.getY() - y, center.getZ() - z, center.getX() + x, center.getY() + y, center.getZ() + z);
    }

    public double rayTrace(Vector start, Vector direction, double maxDistance) {

        final double startX = start.getX();
        final double startY = start.getY();
        final double startZ = start.getZ();

        final Vector dir = direction.clone();

        double dirX = dir.getX();
        double dirY = dir.getY();
        double dirZ = dir.getZ();

        dir.setX(dirX == -0.0D ? 0.0D : dirX);
        dir.setY(dirY == -0.0D ? 0.0D : dirY);
        dir.setZ(dirZ == -0.0D ? 0.0D : dirZ);

        dirX = dir.getX();
        dirY = dir.getY();
        dirZ = dir.getZ();

        final double divX = 1.0D / dirX;
        final double divY = 1.0D / dirY;
        final double divZ = 1.0D / dirZ;

        double tMin;
        double tMax;

        if (dirX >= 0.0D) {

            tMin = (this.minX - startX) * divX;
            tMax = (this.maxX - startX) * divX;

        } else {

            tMin = (this.maxX - startX) * divX;
            tMax = (this.minX - startX) * divX;
        }

        double tyMin;
        double tyMax;

        if (dirY >= 0.0D) {

            tyMin = (this.minY - startY) * divY;
            tyMax = (this.maxY - startY) * divY;

        } else {

            tyMin = (this.maxY - startY) * divY;
            tyMax = (this.minY - startY) * divY;
        }

        if (tMin <= tyMax && tMax >= tyMin) {

            if (tyMin > tMin) {
                tMin = tyMin;
            }

            if (tyMax < tMax) {
                tMax = tyMax;
            }

            double tzMin;
            double tzMax;

            if (dirZ >= 0.0D) {

                tzMin = (this.minZ - startZ) * divZ;
                tzMax = (this.maxZ - startZ) * divZ;

            } else {

                tzMin = (this.maxZ - startZ) * divZ;
                tzMax = (this.minZ - startZ) * divZ;

            }

            if (tMin <= tzMax && tMax >= tzMin) {

                if (tzMin > tMin) tMin = tzMin;

                if (tzMax < tMax) tMax = tzMax;

                return tMax < 0.0D || tMin >= maxDistance ? -1D : tMin;
            }
        }

        return -1D;
    }

    public double distance(Location location) {
        return Math.sqrt(Math.min(
                Math.pow(location.getX() - this.minX, 2),
                Math.pow(location.getX() - this.maxX, 2)) + Math.min(Math.pow(location.getZ() - this.minZ, 2),
                Math.pow(location.getZ() - this.maxZ, 2)
        ));
    }

    public double distance(CustomLocation location) {
        return Math.sqrt(Math.min(
                Math.pow(location.getX() - this.minX, 2),
                Math.pow(location.getX() - this.maxX, 2)) + Math.min(Math.pow(location.getZ() - this.minZ, 2),
                Math.pow(location.getZ() - this.maxZ, 2)
        ));
    }

    public double distance(double x, double z) {
        final double dx = Math.min(Math.pow(x - minX, 2), Math.pow(x - maxX, 2));
        final double dz = Math.min(Math.pow(z - minZ, 2), Math.pow(z - maxZ, 2));

        return Math.sqrt(dx + dz);
    }

    public double distance(BoundingBox box) {
        final double dx = Math.min(Math.pow(box.minX - minX, 2), Math.pow(box.maxX - maxX, 2));
        final double dz = Math.min(Math.pow(box.minZ - minZ, 2), Math.pow(box.maxZ - maxZ, 2));

        return Math.sqrt(dx + dz);
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public Vector getMin() {
        return new Vector(getMinX(), getMinY(), getMinZ());
    }

    public Vector getMax() {
        return new Vector(getMaxX(), getMaxY(), getMaxZ());
    }

    public BoundingBox resize(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);

        return this;
    }

    public double getWidth() {
        return getMaxX() - getMinX();
    }

    public double getDepth() {
        return getMaxZ() - getMinZ();
    }

    public double getHeight() {
        return getMaxY() - getMinY();
    }

    public double getVolume() {
        return getHeight() * getWidth() * getDepth();
    }

    public double getCenterX() {
        return getMinX() + getWidth() * 0.5D;
    }

    public double getCenterY() {
        return getMinY() + getHeight() * 0.5D;
    }

    public double getCenterZ() {
        return getMinZ() + getDepth() * 0.5D;
    }

    public Vector getCenter() {
        return new Vector(getCenterX(), getCenterY(), getCenterZ());
    }

    public BoundingBox copy(BoundingBox other) {
        return resize(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }

    public BoundingBox expand(double negativeX, double negativeY, double negativeZ, double positiveX, double positiveY, double positiveZ) {
        if (negativeX == 0.0D && negativeY == 0.0D && negativeZ == 0.0D && positiveX == 0.0D && positiveY == 0.0D && positiveZ == 0.0D) {
            return this;
        } else {
            double newMinX = getMinX() - negativeX;
            double newMinY = getMinY() - negativeY;
            double newMinZ = getMinZ() - negativeZ;
            double newMaxX = getMaxX() + positiveX;
            double newMaxY = getMaxY() + positiveY;
            double newMaxZ = getMaxZ() + positiveZ;
            double centerZ;

            if (newMinX > newMaxX) {
                centerZ = getCenterX();
                if (newMaxX >= centerZ) {
                    newMinX = newMaxX;
                } else if (newMinX <= centerZ) {
                    newMaxX = newMinX;
                } else {
                    newMinX = centerZ;
                    newMaxX = centerZ;
                }
            }

            if (newMinY > newMaxY) {
                centerZ = getCenterY();
                if (newMaxY >= centerZ) {
                    newMinY = newMaxY;
                } else if (newMinY <= centerZ) {
                    newMaxY = newMinY;
                } else {
                    newMinY = centerZ;
                    newMaxY = centerZ;
                }
            }

            if (newMinZ > newMaxZ) {
                centerZ = getCenterZ();
                if (newMaxZ >= centerZ) {
                    newMinZ = newMaxZ;
                } else if (newMinZ <= centerZ) {
                    newMaxZ = newMinZ;
                } else {
                    newMinZ = centerZ;
                    newMaxZ = centerZ;
                }
            }

            return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
        }
    }

    public BoundingBox expand(double x, double y, double z) {
        return expand(x, y, z, x, y, z);
    }

    public BoundingBox expand(Vector expansion) {
        return expand(expansion.getX(), expansion.getY(), expansion.getZ());
    }

    public BoundingBox expand(double expansion) {
        return expand(expansion, expansion, expansion, expansion, expansion, expansion);
    }

    public BoundingBox expand(double dirX, double dirY, double dirZ, double expansion) {
        if (expansion == 0.0D) {
            return this;
        } else if (dirX == 0.0D && dirY == 0.0D && dirZ == 0.0D) {
            return this;
        } else {
            final double negativeX = dirX < 0.0D ? -dirX * expansion : 0.0D;
            final double negativeY = dirY < 0.0D ? -dirY * expansion : 0.0D;
            final double negativeZ = dirZ < 0.0D ? -dirZ * expansion : 0.0D;
            final double positiveX = dirX > 0.0D ? dirX * expansion : 0.0D;
            final double positiveY = dirY > 0.0D ? dirY * expansion : 0.0D;
            final double positiveZ = dirZ > 0.0D ? dirZ * expansion : 0.0D;

            return expand(negativeX, negativeY, negativeZ, positiveX, positiveY, positiveZ);
        }
    }

    public BoundingBox expand(Vector direction, double expansion) {
        return expand(direction.getX(), direction.getY(), direction.getZ(), expansion);
    }

    public BoundingBox expand(BlockFace blockFace, double expansion) {
        return blockFace == BlockFace.SELF ? this : expand(blockFace.getDirection(), expansion);
    }

    public BoundingBox expandDirectional(double dirX, double dirY, double dirZ) {
        return expand(dirX, dirY, dirZ, 1.0D);
    }

    public BoundingBox expandDirectional(Vector direction) {
        return expand(direction.getX(), direction.getY(), direction.getZ(), 1.0D);
    }

    public BoundingBox union(double posX, double posY, double posZ) {
        final double newMinX = Math.min(getMinX(), posX);
        final double newMinY = Math.min(getMinY(), posY);
        final double newMinZ = Math.min(getMinZ(), posZ);
        final double newMaxX = Math.max(getMaxX(), posX);
        final double newMaxY = Math.max(getMaxY(), posY);
        final double newMaxZ = Math.max(getMaxZ(), posZ);

        return newMinX == getMinX() && newMinY == getMinY() && newMinZ == getMinZ() && newMaxX == getMaxX() && newMaxY == getMaxY() && newMaxZ == getMaxZ()
                ? this
                : resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    public BoundingBox union(Vector position) {
        return union(position.getX(), position.getY(), position.getZ());
    }

    public BoundingBox union(Location position) {
        return union(position.getX(), position.getY(), position.getZ());
    }

    public BoundingBox union(BoundingBox other) {
        if (contains(other)) {
            return this;
        } else {
            final double newMinX = Math.min(getMinX(), other.getMinX());
            final double newMinY = Math.min(getMinY(), other.getMinY());
            final double newMinZ = Math.min(getMinZ(), other.getMinZ());
            final double newMaxX = Math.max(getMaxX(), other.getMaxX());
            final double newMaxY = Math.max(getMaxY(), other.getMaxY());
            final double newMaxZ = Math.max(getMaxZ(), other.getMaxZ());

            return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
        }
    }

    public BoundingBox intersection(BoundingBox other) {

        final double newMinX = Math.max(getMinX(), other.getMinX());
        final double newMinY = Math.max(getMinY(), other.getMinY());
        final double newMinZ = Math.max(getMinZ(), other.getMinZ());
        final double newMaxX = Math.min(getMaxX(), other.getMaxX());
        final double newMaxY = Math.min(getMaxY(), other.getMaxY());
        final double newMaxZ = Math.min(getMaxZ(), other.getMaxZ());

        return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    public BoundingBox shift(double shiftX, double shiftY, double shiftZ) {
        return shiftX == 0.0D && shiftY == 0.0D && shiftZ == 0.0D ? this
                : resize(getMinX() + shiftX, getMinY() + shiftY, getMinZ() + shiftZ, getMaxX() + shiftX, getMaxY() + shiftY, getMaxZ() + shiftZ);
    }

    public BoundingBox shift(Vector shift) {
        return shift(shift.getX(), shift.getY(), shift.getZ());
    }

    public BoundingBox shift(Location shift) {
        return shift(shift.getX(), shift.getY(), shift.getZ());
    }

    private boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return getMinX() < maxX && getMaxX() > minX && getMinY() < maxY && getMaxY() > minY && getMinZ() < maxZ && getMaxZ() > minZ;
    }

    public boolean intersects(BoundingBox other) {
        return intersects(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }

    public boolean intersects(Vector min, Vector max) {

        final double x1 = min.getX();
        final double y1 = min.getY();
        final double z1 = min.getZ();
        final double x2 = max.getX();
        final double y2 = max.getY();
        final double z2 = max.getZ();

        return intersects(x1, y1, z1, x2, y2, z2);
    }

    public boolean contains(double x, double y, double z) {
        return x >= getMinX() && x < getMaxX() && y >= getMinY() && y < getMaxY() && z >= getMinZ() && z < getMaxZ();
    }

    public boolean contains(Vector position) {
        return contains(position.getX(), position.getY(), position.getZ());
    }

    private boolean contains(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return getMinX() <= minX && getMaxX() >= maxX && getMinY() <= minY && getMaxY() >= maxY && getMinZ() <= minZ && getMaxZ() >= maxZ;
    }

    public boolean contains(BoundingBox other) {

        return contains(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }

    public boolean contains(Vector min, Vector max) {

        final double x1 = min.getX();
        final double y1 = min.getY();
        final double z1 = min.getZ();
        final double x2 = max.getX();
        final double y2 = max.getY();
        final double z2 = max.getZ();

        return contains(x1, y1, z1, x2, y2, z2);
    }

    public int hashCode() {
        int result = 1;
        long temp = Double.doubleToLongBits(getMaxX());
        result = 31 * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(getMaxY());
        result = 31 * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(getMaxZ());
        result = 31 * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(getMinX());
        result = 31 * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(getMinY());
        result = 31 * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(getMinZ());
        result = 31 * result + (int) (temp ^ temp >>> 32);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof BoundingBox)) {
            return false;
        } else {
            BoundingBox other = (BoundingBox) obj;
            if (Double.doubleToLongBits(getMaxX()) != Double.doubleToLongBits(other.getMaxX())) {
                return false;
            } else if (Double.doubleToLongBits(getMaxY()) != Double.doubleToLongBits(other.getMaxY())) {
                return false;
            } else if (Double.doubleToLongBits(getMaxZ()) != Double.doubleToLongBits(other.getMaxZ())) {
                return false;
            } else if (Double.doubleToLongBits(getMinX()) != Double.doubleToLongBits(other.getMinX())) {
                return false;
            } else if (Double.doubleToLongBits(getMinY()) != Double.doubleToLongBits(other.getMinY())) {
                return false;
            } else {
                return Double.doubleToLongBits(getMinZ()) == Double.doubleToLongBits(other.getMinZ());
            }
        }
    }

    public String toString() {
        return "BoundingBox [minX=" +
                getMinX() +
                ", minY=" +
                getMinY() +
                ", minZ=" +
                getMinZ() +
                ", maxX=" +
                getMaxX() +
                ", maxY=" +
                getMaxY() +
                ", maxZ=" +
                getMaxZ() +
                "]";
    }

    public BoundingBox clone() {
        try {
            return (BoundingBox) super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }
}