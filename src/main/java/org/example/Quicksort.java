package org.example;

import java.util.Random;

public class Quicksort {
    private int comparisons = 0;
    private int recursionDepth = 0;
    private final Random random = new Random();

    public void sort(int[] arr) {
        comparisons = 0;
        recursionDepth = 0;
        quickSort(arr, 0, arr.length - 1, 1);
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    private int partition(int[] arr, int low, int high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        swap(arr, low, pivotIndex);
        int pivot = arr[low];

        int i = low + 1, j = high;

        while (true) {
            while (i <= high && arr[i] < pivot) {
                comparisons++;
                i++;
            }
            while (arr[j] > pivot) {
                comparisons++;
                j--;
            }

            if (i >= j) break;
            swap(arr, i, j);
            i++;
            j--;
        }

        swap(arr, low, j);
        return j;
    }
    private void quickSort(int[] arr, int low, int high, int depth) {
        while (low < high) {
            recursionDepth = Math.max(recursionDepth, depth);

            int p = partition(arr, low, high);

            int size_left = p - low;
            int size_right = high - p;

            if (size_left < size_right) {
                quickSort(arr, low, p - 1, depth + 1);

                low = p + 1;
            } else {
                quickSort(arr, p + 1, high, depth + 1);

                high = p - 1;
            }
        }
    }

    public int getComparisons() { return comparisons; }
    public int getRecursionDepth() { return recursionDepth; }
}