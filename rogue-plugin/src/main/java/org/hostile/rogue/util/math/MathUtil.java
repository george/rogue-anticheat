package org.hostile.rogue.util.math;

import com.google.common.util.concurrent.AtomicDouble;
import com.voidac.util.pair.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class MathUtil {

    private static final double LOG_TWO = Math.log(2.0D);

    public static final double DIVISOR = 1D / 64;
    public static final double JUMP_HEIGHT = 0.42F;
    public static final double GRAVITY = 0.08;
    public static final double VERTICAL_FRICTION = 0.98F;
    /**
     * Calculates sqrt of all the values entries^2
     *
     * @param values The number values
     * @return sqrt(values^2)
     */
    public static double hypot(double... values) {
        AtomicDouble squaredSum = new AtomicDouble(0D);

        Arrays.stream(values).forEach(value -> squaredSum.getAndAdd(Math.pow(value, 2D)));

        return Math.sqrt(squaredSum.get());
    }

    /**
     * Calculates the average (mean) of {@param values}
     *
     * @param values The number values
     * @return The average (mean) of {@param values}
     */
    public static double getAverage(Collection<? extends Number> values) {
        return values.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .orElse(0D);
    }

    public static double getCps(Collection<? extends Number> values) {
        return 20 / getAverage(values);
    }

    /**
     * Calculates the standard deviation of {@param values}
     * @param values The number values
     * @return The standard deviation of {@param values}
     */
    public static double getStandardDeviation(Collection<? extends Number> values) {
        double average = getAverage(values);

        AtomicDouble variance = new AtomicDouble(0D);

        values.forEach(delay -> variance.getAndAdd(Math.pow(delay.doubleValue() - average, 2D)));

        return Math.sqrt(variance.get() / values.size());
    }

    /**
     * Calculates the kurtosis of {@param values}
     * @param values The number values
     * @return The kurtosis of {@param values}
     */
    public static double getKurtosis(Collection<? extends Number> values) {
        double n = values.size();

        if (n < 3)
            return Double.NaN;

        double average = getAverage(values);
        double stDev = getStandardDeviation(values);

        AtomicDouble accum = new AtomicDouble(0D);

        values.forEach(delay -> accum.getAndAdd(Math.pow(delay.doubleValue() - average, 4D)));

        return n * (n + 1) / ((n - 1) * (n - 2) * (n - 3)) *
                (accum.get() / Math.pow(stDev, 4D)) - 3 *
                Math.pow(n - 1, 2D) / ((n - 2) * (n - 3));
    }

    /**
     * Calculates the gcd of {@param a} and {@param b}
     * @return The gcd
     */
    public static double gcd(double a, double b) {
        if (a < b)
            return gcd(b, a);
        else if (Math.abs(b) < 0.001) // base case
            return a;
        else
            return gcd(b, a - Math.floor(a / b) * b);
    }

    /**
     * Returns the outliers of a set of numbers
     *
     * @param - The collection of numbers you want analyze
     * @return - A pair of the high and low outliers
     * @See - https://en.wikipedia.org/wiki/Outlier
     * @author Elevated
     */
    public static Pair<List<Number>, List<Number>> getOutliers(Collection<? extends Number> collection) {
        List<Double> values = collection.stream().map(Number::doubleValue)
                .collect(Collectors.toList());

        double q1 = getMedian(values.subList(0, values.size() / 2));
        double q3 = getMedian(values.subList(values.size() / 2, values.size()));

        double iqr = Math.abs(q1 - q3);
        double lowThreshold = q1 - 1.5 * iqr, highThreshold = q3 + 1.5 * iqr;

        Pair<List<Number>, List<Number>> pair = new Pair<>(new ArrayList<>(), new ArrayList<>());

        values.forEach(value -> {
            if (value < lowThreshold) {
                pair.getLeft().add(value);
            } else if (value > highThreshold) {
                pair.getRight().add(value);
            }
        });

        return pair;
    }

    /**
     * Calculates the median of a set of values
     *
     * @param data The values to calculate the median of
     * @return - The middle number of that data
     * @author Elevated
     */
    public static double getMedian(List<? extends Number> data) {
        if (data.size() % 2 == 0) {
            return (data.get(data.size() / 2).doubleValue() + data.get(data.size() / 2 - 1).doubleValue()) / 2D;
        } else {
            return data.get(data.size() / 2).doubleValue();
        }
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

    public static double skewness(List<? extends Number> samples) {
        double average = getAverage(samples);
        double standardDeviation = getStandardDeviation(samples);
        double median = getMedian(samples);

        return 3 * (average - median) / standardDeviation;
    }

    public static double getVariance(Collection<? extends Number> samples) {
        double average = getAverage(samples);

        AtomicDouble sqDiff = new AtomicDouble();
        samples.forEach(sample -> sqDiff.addAndGet((sample.doubleValue() - average) * (sample.doubleValue() - average)));

        return sqDiff.doubleValue() / samples.size();
    }

    /**
     * Returns the amount of numbers in a collection that are divisible by 1
     *
     * @param numbers The collection to check
     * @return The amount of rounded numbers in {@param numbers}
     */
    public static int countRounded(Collection<? extends Number> numbers) {
        return (int) numbers.stream().filter(val -> val.doubleValue() % 1.0 == 0).count();
    }

    public static double getEntropy(Collection<? extends Number> collection) {
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
}
