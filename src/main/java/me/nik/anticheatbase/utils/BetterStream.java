package me.nik.anticheatbase.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A BetterStream utility class
 * <p>
 * Obviously this does not include every single Stream method
 * However these methods are much faster than using streams.
 *
 * @author Nik, GTX
 */
public final class BetterStream {

    private BetterStream() {
    }

    public static <T> boolean anyMatch(final Collection<T> data, final Predicate<T> condition) {
        if (condition == null) return false;

        for (final T object : data) {

            if (condition.test(object)) return true;
        }

        return false;
    }

    public static <T> boolean allMatch(final Collection<T> data, final Predicate<T> condition) {
        if (condition == null) return false;

        for (final T object : data) {

            if (!condition.test(object)) return false;
        }

        return true;
    }

    public static <T> Collection<T> filter(final Collection<T> data, final Predicate<T> filter) {

        final List<T> list = new LinkedList<>();

        if (filter == null || data.isEmpty()) return list;

        for (final T object : data) {

            if (filter.test(object)) list.add(object);
        }

        return list;
    }

    public static <T> Collection<T> distinct(final Collection<T> data) {
        return new HashSet<>(data);
    }

    public static int getDuplicates(final Collection<?> data) {
        if (data.isEmpty()) return 0;

        return data.size() - distinct(data).size();
    }

    public static double getMaximumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0.0D;

        double max = Double.MIN_VALUE;

        for (final double val : nums) {
            if (val > max) max = val;
        }

        return max;
    }

    public static int getMaximumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        int max = Integer.MIN_VALUE;

        for (final int val : nums) {
            if (val > max) max = val;
        }

        return max;
    }

    public static long getMaximumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        long max = Long.MIN_VALUE;

        for (final long val : nums) {

            if (val > max) max = val;
        }

        return max;
    }

    public static float getMaximumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0.0F;

        float max = Float.MIN_VALUE;

        for (final float val : nums) {

            if (val > max) max = val;
        }

        return max;
    }

    public static double getMinimumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0.0D;

        double min = Double.MAX_VALUE;

        for (final double val : nums) {

            if (val < min) min = val;
        }

        return min;
    }

    public static int getMinimumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;

        for (final int val : nums) {

            if (val < min) min = val;
        }

        return min;
    }

    public static long getMinimumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        long min = Long.MAX_VALUE;

        for (final long val : nums) {

            if (val < min) min = val;
        }

        return min;
    }

    public static float getMinimumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0.0F;

        float min = Float.MAX_VALUE;

        for (final float val : nums) {

            if (val < min) min = val;
        }

        return min;
    }

    public static double getSumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0D;

        double sum = 0D;

        for (double num : nums) sum += num;

        return sum;
    }

    public static int getSumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        int sum = 0;

        for (int num : nums) sum += num;

        return sum;
    }

    public static long getSumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        long sum = 0;

        for (long num : nums) sum += num;

        return sum;
    }

    public static float getSumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0F;

        float sum = 0F;

        for (float num : nums) sum += num;

        return sum;
    }

    public static double getAverageDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0D;

        return getSumDouble(nums) / nums.size();
    }

    public static int getAverageInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        return getSumInt(nums) / nums.size();
    }

    public static long getAverageLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        return getSumLong(nums) / nums.size();
    }

    public static float getAverageFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0F;

        return getSumFloat(nums) / nums.size();
    }
}