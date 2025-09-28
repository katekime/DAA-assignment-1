package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.Random;
import java.util.Arrays;

public class MetricCollector {
    private static final Random RANDOM = new Random();

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(size * 10);
        }
        return arr;
    }

    private Point[] generateRandomPoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(RANDOM.nextDouble() * 1000, RANDOM.nextDouble() * 1000);
        }
        return points;
    }
    public void collectAndWrite(String algorithmName, int sizeStart, int sizeEnd, int sizeStep,
                                Consumer<FileWriter> algorithmRunner) {

        String filename = algorithmName.toLowerCase().replace(" ", "") + "_metrics.csv";
        System.out.printf("Collecting data for %s...\n", algorithmName);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("N,Runtime_ns,Comparisons,RecursionDepth,Allocations\n");
            algorithmRunner.accept(writer);

            System.out.printf("Successfully wrote metrics to %s\n", filename);

        } catch (IOException e) {
            System.err.printf("Error writing metrics for %s: %s\n", algorithmName, e.getMessage());
        }
    }
    public Consumer<FileWriter> getMergeSortRunner() {
        return writer -> {
            for (int N = 1000; N <= 100000; N += 10000) {
                int[] arr = generateRandomArray(N);
                Mergesort sorter = new Mergesort(N);

                long startTime = System.nanoTime();
                sorter.sort(arr);
                long duration = System.nanoTime() - startTime;

                try {
                    writer.write(String.format("%d,%d,%d,%d,%d\n",
                            N, duration, sorter.getComparisons(), sorter.getRecursionDepth(), sorter.getAllocations()
                    ));
                } catch (IOException e) { throw new RuntimeException(e); }
            }
        };
    }
    public Consumer<FileWriter> getQuickSortRunner() {
        return writer -> {
            for (int N = 1000; N <= 100000; N += 10000) {
                int[] arr = generateRandomArray(N);
                Quicksort sorter = new Quicksort();

                long startTime = System.nanoTime();
                sorter.sort(arr);
                long duration = System.nanoTime() - startTime;

                try {
                    writer.write(String.format("%d,%d,%d,%d,%d\n",
                            N, duration, sorter.getComparisons(), sorter.getRecursionDepth(), 0
                    ));
                } catch (IOException e) { throw new RuntimeException(e); }
            }
        };
    }
    public Consumer<FileWriter> getSelectRunner() {
        return writer -> {
            for (int N = 1000; N <= 100000; N += 10000) {
                int[] arr = generateRandomArray(N);
                DeterministicSelect selector = new DeterministicSelect();

                long startTime = System.nanoTime();
                selector.select(arr, N / 2);
                long duration = System.nanoTime() - startTime;

                try {
                    writer.write(String.format("%d,%d,%d,%d,%d\n",
                            N, duration, selector.getComparisons(), selector.getRecursionDepth(), 0
                    ));
                } catch (IOException e) { throw new RuntimeException(e); }
            }
        };
    }
    public Consumer<FileWriter> getClosestPairRunner() {
        return writer -> {
            for (int N = 1000; N <= 100000; N += 10000) {
                Point[] pts = generateRandomPoints(N);
                Closestpair solver = new Closestpair();

                long startTime = System.nanoTime();
                solver.findClosest(pts);
                long duration = System.nanoTime() - startTime;

                try {
                    writer.write(String.format("%d,%d,%d,%d,%d\n",
                            N, duration, solver.getComparisons(), solver.getRecursionDepth(), 0
                    ));
                } catch (IOException e) { throw new RuntimeException(e); }
            }
        };
    }
}