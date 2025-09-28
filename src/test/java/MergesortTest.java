package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergesortTest {

    private final Random random = new Random();

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 2);
        }
        return arr;
    }
    @Test
    void testBasicCorrectness() {
        int[] input = {10, 7, 8, 9, 1, 5};
        int[] expected = {1, 5, 7, 8, 9, 10};

        Mergesort sorter = new Mergesort(input.length);
        sorter.sort(input);

        assertArrayEquals(expected, input, "MergeSort basic sorting failed.");
    }
    @Test
    void testAdversarialCases() {
        int[] empty = {};
        new Mergesort(0).sort(empty);
        assertArrayEquals(new int[]{}, empty, "Issue with empty array.");

        int[] single = {5};
        new Mergesort(1).sort(single);
        assertArrayEquals(new int[]{5}, single, "Issue with single element array.");

        int[] duplicates = {5, 1, 5, 10, 3, 3, 5};
        int[] expected = {1, 3, 3, 5, 5, 5, 10};
        new Mergesort(duplicates.length).sort(duplicates);
        assertArrayEquals(expected, duplicates, "Issue with duplicates.");
    }
    @Test
    void testAllocationControl() {
        final int N = 100_000;
        int[] largeArr = generateRandomArray(N);

        Mergesort sorter = new Mergesort(N);
        sorter.sort(largeArr);

        int actualAllocations = sorter.getAllocations();

        assertTrue(actualAllocations <= 1,
                "MergeSort should have at most 1 allocation (for the buffer), but found " + actualAllocations
        );
    }
    @Test
    void testRecursionDepth() {
        final int N = 1024;
        int[] arr = generateRandomArray(N);

        Mergesort sorter = new Mergesort(N);
        sorter.sort(arr);

        int actualDepth = sorter.getRecursionDepth();

        int expectedLog = (int) (Math.log(N) / Math.log(2));

        int minAcceptableDepth = 7;
        int maxAcceptableDepth = 10;

        assertTrue(actualDepth >= minAcceptableDepth && actualDepth <= maxAcceptableDepth,
                String.format("Recursion depth (%d) must be in the range [%d, %d] for N=%d. Log2(N)=%d.",
                        actualDepth, minAcceptableDepth, maxAcceptableDepth, N, expectedLog)
        );
    }
}