// ClosestPairTest.java
package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestpairTest {

    private final Random random = new Random();
    private final Closestpair calculator = new Closestpair();

    private Point[] generateRandomPoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    private double bruteForceCheck(Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = Math.hypot(points[i].x - points[j].x, points[i].y - points[j].y);
                min = Math.min(min, dist);
            }
        }
        return min;
    }

    @Test
    void testCorrectnessSmallSet() {
        Point[] points = {
                new Point(2, 3), new Point(12, 30),
                new Point(40, 50), new Point(5, 1)
        };

        double expected = bruteForceCheck(points);
        double actual = calculator.findClosest(points);

        assertEquals(expected, actual, 1e-9, "Small set calculation failed.");
    }
    @Test
    void testStripCase() {
        Point[] points = {
                new Point(0, 0), new Point(100, 100),
                new Point(5, 5), new Point(5.0000000001, 5) // Очень близко
        };

        double expected = bruteForceCheck(points);
        double actual = calculator.findClosest(points);

        assertEquals(expected, actual, 1e-10, "Strip case calculation failed.");
    }

    @Test
    void testEdgeCases() {
        assertEquals(Double.MAX_VALUE, calculator.findClosest(new Point[]{}), 0, "Empty array failed.");

        assertEquals(Double.MAX_VALUE, calculator.findClosest(new Point[]{new Point(1, 1)}), 0, "Single point array failed.");

        Point[] two = {new Point(0, 0), new Point(3, 4)};
        assertEquals(5.0, calculator.findClosest(two), 1e-9, "Two points distance failed.");
    }

    @Test
    void testRecursionDepthIsLogarithmic() {
        final int N = 1024;
        Point[] largeArr = generateRandomPoints(N);

        calculator.findClosest(largeArr);

        int actualDepth = calculator.getRecursionDepth();

        int expectedLog = (int) (Math.log(N) / Math.log(2));

        int minAcceptableDepth = expectedLog;
        int maxAcceptableDepth = expectedLog + 2;

        assertTrue(actualDepth >= minAcceptableDepth && actualDepth <= maxAcceptableDepth,
                String.format("Recursion depth (%d) must be in the range [%d, %d] for N=%d. Log2(N)=%d. Confirming O(log N) depth.",
                        actualDepth, minAcceptableDepth, maxAcceptableDepth, N, expectedLog)
        );
    }
}