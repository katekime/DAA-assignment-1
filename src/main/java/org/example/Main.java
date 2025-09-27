package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        Quicksort sorter = new Quicksort();
        sorter.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(sorter.getComparisons());
        System.out.println(sorter.getRecursionDepth());
    }
}