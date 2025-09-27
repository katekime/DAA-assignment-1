package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 3, 9, 1};
        Mergesort sorter = new Mergesort(arr.length);
        sorter.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(sorter.getComparisons());
        System.out.println(sorter.getRecursionDepth());
    }
}