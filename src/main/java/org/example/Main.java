package org.example;

import org.example.Closestpair;

public class Main {
    public static void main(String[] args) {
        Point[] pts = new Point[] {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        Closestpair solver = new Closestpair();
        double d = solver.findClosest(pts);

        System.out.println(d);
        System.out.println(solver.getComparisons());
        System.out.println(solver.getRecursionDepth());
    }
}