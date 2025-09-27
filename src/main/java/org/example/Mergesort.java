package org.example;

public class Mergesort {
    private int[] buffer;
    private int comparisons;
    private int allocations;
    private int recursionDepth;

    public Mergesort(int n) {
        buffer = new int[n];
    }

    public void sort(int[] arr) {
        comparisons = 0;
        allocations = 0;
        recursionDepth = 0;
        mergeSort(arr, 0, arr.length - 1, 1);
    }

    private void mergeSort(int[] arr, int left, int right, int depth) {
        recursionDepth = Math.max(recursionDepth, depth);
        if (left >= right) return;

        int mid = (left + right) / 2;
        mergeSort(arr, left, mid, depth + 1);
        mergeSort(arr, mid + 1, right, depth + 1);
        merge(arr, left, mid, right);
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            comparisons++;
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }
        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        for (int p = left; p <= right; p++) {
            arr[p] = buffer[p];
        }
    }

    public int getComparisons() { return comparisons; }
    public int getAllocations() { return allocations; }
    public int getRecursionDepth() { return recursionDepth; }
}
