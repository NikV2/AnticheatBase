package me.nik.anticheatbase.utils.custom.aim;

import me.nik.anticheatbase.utils.fastmath.FastMath;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple heuristics tool that can prove useful when coding AimAssist related checks.
 * Example usage:
 * <p>
 * private final RotationHeuristics heuristics = new RotationHeuristics(100, 1.25F, 7.5F);
 *
 * @Override public void handle(Packet packet) {
 * if (!packet.isRotation() || profile.isExempt().aim(15, 0)) return;
 * <p>
 * RotationData data = profile.getRotationData();
 * <p>
 * this.heuristics.process(data.getDeltaYaw());
 * <p>
 * if (!this.heuristics.isFinished()) return;
 * <p>
 * final RotationHeuristics.HeuristicsResult result = this.heuristics.getResult();
 * <p>
 * final float average = result.getAverage();
 * final float min = result.getMin();
 * final float max = result.getMax();
 * final int lowCount = result.getLowCount();
 * final int highCount = result.getHighCount();
 * final int duplicates = result.getDuplicates();
 * final int roundedCount = result.getRoundedCount();
 * }
 */
public class RotationHeuristics {

    private final int maxSize;
    private final float lowThreshold, highThreshold;
    private final Set<Float> distinctRotations = new HashSet<>();
    private int size;
    private float average, min, max;
    private int highCount, lowCount, roundedCount;

    /**
     * @param maxSize       The maximum sample size.
     * @param lowThreshold  The amount to be considered as "low".
     * @param highThreshold The amount to be considered as "high".
     */
    public RotationHeuristics(int maxSize, float lowThreshold, float highThreshold) {
        this.size = this.maxSize = maxSize;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
        reset();
    }

    /**
     * Process the rotation given if processing is not finished yet.
     *
     * @param rotation The rotation made.
     */
    public void process(float rotation) {

        /*
        Collection is finished, No more data can be processed.
         */
        if (isFinished()) return;

        /*
        If this is the last element we're going to process, Get the correct average amount.
        Otherwise add the amount in order to use the average variable as our sum.
         */
        this.average = (this.size - 1) == 0 ? (this.average + rotation) / this.maxSize : this.average + rotation;

        /*
        Set implementations do not allow duplicates, So we can simply add the value there
        And use it in the end to get the duplicate amounts.
         */
        this.distinctRotations.add(rotation);

        /*
        Update the high rotation count if the current one meets the high amount condition.
         */
        this.highCount = rotation > this.highThreshold ? this.highCount + 1 : this.highCount;

        /*
        Update the low rotation count if the current one meets the low amount condition.
         */
        this.lowCount = rotation < this.lowThreshold ? this.lowCount + 1 : this.lowCount;

        /*
        Update the min rotation if the current rotation is lower than the current min amount.
         */
        this.min = Math.min(rotation, this.min);

        /*
        Update the max rotation if the current rotation is higher than the current max amount.
         */
        this.max = Math.max(rotation, this.max);

        /*
        Update the rounded count if the current rotation is big enough and rounded.
         */
        this.roundedCount = rotation > 1F && rotation % 1.5 != 0F
                    /*
                    Make sure any type of rounding is applied.
                     */
                && (FastMath.round(rotation) == 0F || rotation % 1 == 0F) ? this.roundedCount + 1 : this.roundedCount;

        /*
        Decrement the size since processing for this value is finished.
         */
        this.size--;
    }

    public void reset() {
        this.size = this.maxSize;
        this.average = this.highCount = this.lowCount = this.roundedCount = 0;
        this.min = Float.MAX_VALUE;
        this.max = Float.MIN_VALUE;
        this.distinctRotations.clear();
    }

    private boolean isFinished() {
        return this.size == 0;
    }

    /**
     * Get the heuristics result if processing is finished, Null otherwise.
     */
    public HeuristicsResult getResult() {

        /*
        Collection is finished, Return the result and reset.
         */
        if (isFinished()) {

            return new HeuristicsResult(
                    this.average, this.min, this.max,
                    (this.maxSize - this.distinctRotations.size()),
                    this.highCount, this.lowCount, this.roundedCount
            );
        }

        /*
        Analysis result not finished yet.
         */
        return null;
    }

    public static class HeuristicsResult {

        private final float average, min, max;
        private final int duplicates, highCount, lowCount, roundedCount;

        public HeuristicsResult(float average, float min, float max, int duplicates, int highCount, int lowCount, int roundedCount) {
            this.average = average;
            this.min = min;
            this.max = max;
            this.duplicates = duplicates;
            this.highCount = highCount;
            this.lowCount = lowCount;
            this.roundedCount = roundedCount;
        }

        public float getAverage() {
            return average;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public int getDuplicates() {
            return duplicates;
        }

        public int getHighCount() {
            return highCount;
        }

        public int getLowCount() {
            return lowCount;
        }

        public int getRoundedCount() {
            return roundedCount;
        }
    }
}