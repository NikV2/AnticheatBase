package me.nik.anticheatbase.utils;

import me.nik.anticheatbase.tasks.TickTask;
import me.nik.anticheatbase.utils.fastmath.FastMath;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtils {

    private MathUtils() {
    }

    //---------------------------------------------------------------------------------------
    public static final float FRICTION = .91F;
    public static final double WATER_FRICTION = .800000011920929D;
    public static final double SERVER_GROUND_DIVISOR = .015625D;
    public static final double MOTION_Y_FRICTION = .9800000190734863D;
    public static final double MAXIMUM_MOTION_Y = .41999998688697815D;
    //---------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------
    public static final double EXPANDER = 1.6777216E7D;
    public static final long MINIMUM_ROTATION_DIVISOR = 131072L;
    //---------------------------------------------------------------------------------------

    public static long getAbsoluteGcd(final float current, final float last) {

        final long currentExpanded = (long) (current * EXPANDER);

        final long lastExpanded = (long) (last * EXPANDER);

        return getGcd(currentExpanded, lastExpanded);
    }

    private static long getGcd(final long current, final long last) {
        return (last <= 16384L) ? current : getGcd(last, current % last);
    }

    public static double getAbsoluteDelta(final double one, final double two) {
        return Math.abs(Math.abs(one) - Math.abs(two));
    }

    public static double decimalRound(final double val, int scale) {
        return BigDecimal.valueOf(val).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static float getAngle(final Vector one, final Vector two) {

        final double dot = Math.min(Math.max(
                        (one.getX() * two.getX() + one.getY() * two.getY() + one.getZ() * two.getZ())
                                / (one.length() * two.length()),
                        -1.0),
                1.0);

        return (float) FastMath.acos(dot);
    }

    public static Vector getDirection(final Location location) {

        Vector vector = new Vector();

        final double rotX = location.getYaw();
        final double rotY = location.getPitch();

        final double radiansRotY = FastMath.toRadians(rotY);

        vector.setY(-FastMath.sin(radiansRotY));

        final double xz = FastMath.cos(radiansRotY);

        final double radiansRotX = FastMath.toRadians(rotX);

        vector.setX(-xz * FastMath.sin(radiansRotX));
        vector.setZ(xz * FastMath.cos(radiansRotX));

        return vector;
    }

    public static Vector getDirection(final float yaw, final float pitch) {

        Vector vector = new Vector();

        final double radiansRotY = FastMath.toRadians(pitch);

        vector.setY(-FastMath.sin(radiansRotY));

        final double xz = FastMath.cos(radiansRotY);

        final double radiansRotX = FastMath.toRadians(yaw);

        vector.setX(-xz * FastMath.sin(radiansRotX));
        vector.setZ(xz * FastMath.cos(radiansRotX));

        return vector;
    }

    public static boolean isScientificNotation(final Number num) {
        return num.doubleValue() < .001D;
    }

    public static int millisToTicks(long millis) {
        return (int) millis / 50;
    }

    public static long nanosToMillis(final long nanoseconds) {
        return (nanoseconds / 1000000L);
    }

    public static long elapsed(final long millis) {
        return System.currentTimeMillis() - millis;
    }

    public static int elapsedTicks(final int ticks) {
        return TickTask.getCurrentTick() - ticks;
    }
}