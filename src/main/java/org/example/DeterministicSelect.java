package org.example;

import java.util.Arrays;

public class DeterministicSelect {
    private int comparisons = 0;
    private int recursionDepth = 0;
    private static final int GROUP_SIZE = 5;

    public int select(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k must be between 1 and array length.");
        }
        comparisons = 0;
        recursionDepth = 0;
        int[] clonedArr = Arrays.copyOf(arr, arr.length);
        return selectRecursive(clonedArr, 0, clonedArr.length - 1, k, 1);
    }
    private int selectRecursive(int[] arr, int left, int right, int k, int depth) {
        recursionDepth = Math.max(recursionDepth, depth);

        if (left == right) {
            return arr[left];
        }

        int pivotValue = medianOfMedians(arr, left, right, depth);
        int pivotIndex = findAndSwap(arr, left, right, pivotValue);
        int pivotFinalIndex = partition(arr, left, right);

        int p_rank = pivotFinalIndex - left + 1;

        if (k == p_rank) {
            return arr[pivotFinalIndex];
        } else if (k < p_rank) {
            return selectRecursive(arr, left, pivotFinalIndex - 1, k, depth + 1);
        } else {
            return selectRecursive(arr, pivotFinalIndex + 1, right, k - p_rank, depth + 1);
        }
    }
    private int medianOfMedians(int[] arr, int left, int right, int depth) {
        int n = right - left + 1;
        if (n <= GROUP_SIZE) {
            return simpleSortAndSelect(arr, left, right);
        }

        int numGroups = (n + GROUP_SIZE - 1) / GROUP_SIZE; // (n + 4) / 5
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupStart = left + i * GROUP_SIZE;
            int groupEnd = Math.min(groupStart + GROUP_SIZE - 1, right);

            medians[i] = simpleSortAndSelect(arr, groupStart, groupEnd);
        }
        int k_median = (medians.length + 1) / 2;
        return selectRecursive(medians, 0, medians.length - 1, k_median, depth + 1);
    }
    private int simpleSortAndSelect(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
        return arr[left + (right - left) / 2];
    }
    private int findAndSwap(int[] arr, int left, int right, int pivotValue) {
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivotValue) {
                swap(arr, i, right);
                return right;
            }
        }
        return right;
    }
    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public int getComparisons() { return comparisons; }
    public int getRecursionDepth() { return recursionDepth; }
}