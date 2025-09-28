package org.example;

public class Mergesort {
    private int[] buffer;
    private int comparisons;
    private int allocations;
    private int recursionDepth;

    private static final int CUTOFF = 7;

    public Mergesort(int n) {
        this.buffer = new int[n];
        this.allocations = 1;
    }

    public void sort(int[] arr) {
        comparisons = 0;
        recursionDepth = 0;
        mergeSort(arr, 0, arr.length - 1, 1);
    }
    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            if (j >= left) comparisons++;

            arr[j + 1] = key;
        }
    }

    private void mergeSort(int[] arr, int left, int right, int depth) {
        recursionDepth = Math.max(recursionDepth, depth);

        if (right - left < CUTOFF) {
            insertionSort(arr, left, right);
            return;
        }

        if (left >= right) return;

        int mid = left + (right - left) / 2;
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