package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuicksortTest { // why is no woooooork???????

    private final Random random = new Random();

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size);
        }
        return arr;
    }
    @Test
    void testBasicCorrectness() {
        int[] input = {10, 7, 8, 9, 1, 5};
        int[] expected = {1, 5, 7, 8, 9, 10};

        Quicksort sorter = new Quicksort();
        sorter.sort(input);

        assertArrayEquals(expected, input, "Basic sorting failed.");
    }

    @Test
    void testWithDuplicates() {
        int[] input = {5, 1, 5, 10, 3, 3, 5};
        int[] expected = {1, 3, 3, 5, 5, 5, 10};

        Quicksort sorter = new Quicksort();
        sorter.sort(input);

        assertArrayEquals(expected, input, "Sorting with duplicates failed.");
    }

    @Test
    void testAdversarialCases() {
        // Already Sorted
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] sortedCopy = Arrays.copyOf(sorted, sorted.length);
        new Quicksort().sort(sorted);
        assertArrayEquals(sortedCopy, sorted, "Issue with already sorted array.");

        // Reverse Sorted
        int[] reversed = {8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8};
        new Quicksort().sort(reversed);
        assertArrayEquals(expected, reversed, "Issue with reverse sorted array.");
    }

    @Test
    void testRecursionDepthIsBounded() {
        final int N = 100_000;
        int[] largeArr = generateRandomArray(N);

        Quicksort sorter = new Quicksort();
        sorter.sort(largeArr);

        int actualDepth = sorter.getRecursionDepth();

        double log2n = Math.log(N) / Math.log(2);

        int expectedMaxBound = (int) (3 * log2n) + 30;

        String message = String.format("Recursion depth (%d) is too high for N=%d. Expected < %d (O(log N)).",
                actualDepth, N, expectedMaxBound);

        assertTrue(actualDepth < expectedMaxBound, message);

        int[] referenceArr = Arrays.copyOf(largeArr, largeArr.length);
        Arrays.sort(referenceArr);
        assertArrayEquals(referenceArr, largeArr, "Sorting of large array failed.");
    }

}