package org.hostile.anticheat.util;

import lombok.experimental.UtilityClass;
import org.hostile.anticheat.location.CustomLocation;
import org.hostile.anticheat.util.minecraft.MathHelper;
import org.hostile.anticheat.util.minecraft.Vec3;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Credits to sim0n for some of these methods
 */
@UtilityClass
public class MathUtil {

    public final double LOG_TWO = Math.log(2.0D);
    public final double DIVISOR = 1D / 64;
    public final double JUMP_HEIGHT = 0.42F;
    public final double GRAVITY = 0.08;
    public final double VERTICAL_FRICTION = 0.98F;

    /**
     * @param previous The previous location
     * @param current The current location
     * @return The horizontal distance between the two locations
     */
    public double distance(CustomLocation previous, CustomLocation current) {
        return Math.hypot(
                Math.abs(previous.getX() - current.getX()),
                Math.abs(previous.getZ() - current.getZ())
        );
    }

    /**
     * @param values The numbers to return the average of
     * @return The average of the values
     */
    public double getAverage(Collection<? extends Number> values) {
        return values.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .orElse(0D);
    }

    /**
     * @param values The delays to check the CPS of
     * @return The clicks per second of the values
     */
    public double getCps(Collection<? extends Number> values) {
        return 20 / getAverage(values);
    }

    /**
     * @param values The values to return the standard deviation of
     * @return The standard deviation of the values
     */
    public double getStandardDeviation(Collection<? extends Number> values) {
        double average = getAverage(values);

        AtomicReference<Double> variance = new AtomicReference<>(0D);

        values.forEach(delay -> variance.set(variance.get() + Math.pow(delay.doubleValue() - average, 2D)));

        return Math.sqrt(variance.get() / values.size());
    }

    /**
     * @param values The values to check the kurtosis of
     * @return The kurtosis of the values
     */
    public double getKurtosis(Collection<? extends Number> values) {
        double n = values.size();

        if (n < 3)
            return Double.NaN;

        double average = getAverage(values);
        double stDev = getStandardDeviation(values);

        AtomicReference<Double> accum = new AtomicReference<>(0D);

        values.forEach(delay -> accum.set(accum.get() + Math.pow(delay.doubleValue() - average, 4D)));

        return n * (n + 1) / ((n - 1) * (n - 2) * (n - 3)) *
                (accum.get() / Math.pow(stDev, 4D)) - 3 *
                Math.pow(n - 1, 2D) / ((n - 2) * (n - 3));
    }

    /**
     * @param a The first pitch offset
     * @param b The second pitch offset
     * @return The GCD of the pitch offsets
     */
    public double gcd(double a, double b) {
        if (a < b)
            return gcd(b, a);
        else if (Math.abs(b) < 0.001) // base case
            return a;
        else
            return gcd(b, a - Math.floor(a / b) * b);
    }

    public static double covariance(List<? extends Number> left, List<? extends Number> right) {
        double sum = 0;

        double averageLeft = getAverage(left);
        double averageRight = getAverage(right);
        int size = left.size();

        for (int i = 0; i < size; i++) {
            sum = sum + (left.get(i).doubleValue() - averageLeft) * (right.get(i).doubleValue() - averageRight);
        }

        return sum / (size - 1);
    }

    /**
     * @param data The values to check the median of
     * @return The median of the values
     */
    public double getMedian(List<? extends Number> data) {
        int middleIndex = data.size() / 2;

        if (data.size() % 2 == 0) {
            return (data.get(middleIndex).doubleValue() + data.get(middleIndex - 1).doubleValue()) / 2D;
        } else {
            return data.get(middleIndex).doubleValue();
        }
    }

    /**
     * @param samples The samples to check the skewness of
     * @return The skewness of the samples
     */
    public double skewness(List<? extends Number> samples) {
        double average = getAverage(samples);
        double standardDeviation = getStandardDeviation(samples);
        double median = getMedian(samples);

        return 3 * (average - median) / standardDeviation;
    }

    /**
     * @param samples The collection to return the variance of
     * @return The variance of the collection
     */
    public double getVariance(Collection<? extends Number> samples) {
        double average = getAverage(samples);

        AtomicReference<Double> sqDiff = new AtomicReference<>();
        samples.forEach(sample -> sqDiff.set(sqDiff.get() + (sample.doubleValue() - average) * (sample.doubleValue() - average)));

        return sqDiff.get() / samples.size();
    }

    /**
     * @param numbers The collection to check
     * @return The amount of rounded numbers in {@param numbers}
     */
    public int countRounded(Collection<? extends Number> numbers) {
        return (int) numbers.stream().filter(val -> val.doubleValue() % 1.0 == 0).count();
    }

    /**
     * @param collection The collection to calculate the entropy of
     * @return The entropy of the collection
     */
    public double getEntropy(Collection<? extends Number> collection) {
        double size = collection.size();
        if (size < 2.0) {
            return Double.NaN;
        }

        Map<Number, Double> numbers = new HashMap<>();
        collection.stream().mapToInt(Number::intValue).forEach(value -> {
            numbers.put(value, numbers.computeIfAbsent(value, Number::doubleValue) + 1);
        });

        return -numbers.values().stream()
                .mapToDouble(value -> value / size)
                .map(value -> value * LOG_TWO).sum();
    }

    public double getMode(Collection<? extends Number> collection) {
        if (collection.size() == 0) {
            return 0;
        }

        Map<Number, Integer> occurrences = new HashMap<>();

        collection.forEach(number -> {
            occurrences.put(number, occurrences.getOrDefault(number, 0));
        });

        return occurrences.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .get()
                .getKey().doubleValue();
    }

    public Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);

        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);

        return new Vec3(f1 * f2, f3, f * f2);
    }
}
