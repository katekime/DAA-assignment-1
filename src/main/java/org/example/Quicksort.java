package org.example;

import java.util.Random;

public class Quicksort {
    private int comparisons = 0;
    private int recursionDepth = 0;
    private Random random = new Random();

    public void sort(int[] arr) {
        comparisons = 0;
        recursionDepth = 0;
        quickSort(arr, 0, arr.length - 1, 1);
    }

    private void quickSort(int[] arr, int low, int high, int depth) {
        if (low >= high) return;
        if (depth > recursionDepth) recursionDepth = depth;

        int pivotIndex = low + random.nextInt(high - low + 1);
        int pivot = arr[pivotIndex];
        int i = low, j = high;

        while (i <= j) {
            while (arr[i] < pivot) {
                comparisons++;
                i++;
            }
            while (arr[j] > pivot) {
                comparisons++;
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        if (low < j) quickSort(arr, low, j, depth + 1);
        if (i < high) quickSort(arr, i, high, depth + 1);
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public int getComparisons() { return comparisons; }
    public int getRecursionDepth() { return recursionDepth; }
}
