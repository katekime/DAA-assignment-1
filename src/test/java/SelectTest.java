package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {

    private final Random random = new Random();
    private final DeterministicSelect selector = new DeterministicSelect();
    private static final int NUM_TRIALS = 100;

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 2);
        }
        return arr;
    }
    @Test
    void testCorrectnessAcrossRandomTrials() {
        final int N = 1000;
        for (int trial = 0; trial < NUM_TRIALS; trial++) {
            int[] original = generateRandomArray(N);
            int[] expectedArray = Arrays.copyOf(original, N);

            Arrays.sort(expectedArray);

            int k = random.nextInt(N) + 1;

            int expected = expectedArray[k - 1];

            int actual = selector.select(original, k);

            assertEquals(expected, actual,
                    String.format("Failed for k=%d in trial %d. Expected %d, got %d.", k, trial, expected, actual)
            );
        }
    }
    @Test
    void testLinearTimeRecursionDepth() {
        final int N = 100_000;
        int[] largeArr = generateRandomArray(N);

        selector.select(largeArr, N / 2);

        int actualDepth = selector.getRecursionDepth();

        final int MAX_EXPECTED_DEPTH = 50;

        String message = String.format("Recursion depth (%d) is too high for N=%d. Expected < %d (Confirming O(N) complexity).",
                actualDepth, N, MAX_EXPECTED_DEPTH);

        assertTrue(actualDepth < MAX_EXPECTED_DEPTH, message);
    }
    @Test
    void testEdgeCases() {
        int[] arr = {5, 5, 5, 5, 5};
        assertEquals(5, selector.select(arr, 3), "Duplicates failed.");

        int[] arr2 = {1, 2, 3, 4, 5};
        assertEquals(1, selector.select(arr2, 1), "Smallest k=1 failed.");
        assertEquals(5, selector.select(arr2, 5), "Largest k=N failed.");

        assertThrows(IllegalArgumentException.class, () -> selector.select(new int[]{1, 2}, 3));
    }
}