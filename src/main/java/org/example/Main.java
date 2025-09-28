package org.example;

import org.example.Closestpair;

public class Main {
    public static void main(String[] args) {
        Closestpair.Point[] pts = new Closestpair.Point[] {
                new Closestpair.Point(2, 3),
                new Closestpair.Point(12, 30),
                new Closestpair.Point(40, 50),
                new Closestpair.Point(5, 1),
                new Closestpair.Point(12, 10),
                new Closestpair.Point(3, 4)
        };

        Closestpair solver = new Closestpair();
        double d = solver.findClosest(pts);

        System.out.println(d);
        System.out.println(solver.getComparisons());
        System.out.println(solver.getRecursionDepth());
    }
}