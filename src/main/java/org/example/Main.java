package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MetricCollector collector = new MetricCollector();

        System.out.println("Starting comprehensive metrics collection N=1k to 100k");

        collector.collectAndWrite("MergeSort", 1000, 100000, 10000, collector.getMergeSortRunner());

        collector.collectAndWrite("QuickSort", 1000, 100000, 10000, collector.getQuickSortRunner());

        collector.collectAndWrite("DeterministicSelect", 1000, 100000, 10000, collector.getSelectRunner());

        collector.collectAndWrite("ClosestPair", 1000, 100000, 10000, collector.getClosestPairRunner());

        System.out.println("Collection finished. Check your project root for CSV files.");
    }
}