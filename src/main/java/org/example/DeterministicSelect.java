package org.example;

import java.util.Arrays;

public class DeterministicSelect {
    private int comparisons = 0;
    private int recursionDepth = 0;

    public int select(int[] arr, int k) {
        comparisons = 0;
        recursionDepth = 0;
        return selectRecursive(arr, 0, arr.length - 1, k, 1);
    }

    private int selectRecursive(int[] arr, int left, int right, int k, int depth) {
        if (left == right) return arr[left];
        if (depth > recursionDepth) recursionDepth = depth;

        int pivot = medianOfMedians(arr, left, right);
        int pivotIndex = partition(arr, left, right, pivot);
        int rank = pivotIndex - left + 1;

        if (k == rank) return arr[pivotIndex];
        else if (k < rank) return selectRecursive(arr, left, pivotIndex - 1, k, depth + 1);
        else return selectRecursive(arr, pivotIndex + 1, right, k - rank, depth + 1);
    }

    private int partition(int[] arr, int left, int right, int pivot) {
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) { comparisons++; i++; }
            while (arr[j] > pivot) { comparisons++; j--; }
            if (i <= j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        return i - 1;
    }

    private int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return arr[left + n / 2];
        }
        int[] medians = new int[(n + 4) / 5];
        for (int i = 0; i < medians.length; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            medians[i] = arr[subLeft + (subRight - subLeft) / 2];
        }
        return medianOfMedians(medians, 0, medians.length - 1);
    }
}
