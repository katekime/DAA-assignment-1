package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class Closestpair {
    private int comparisons = 0;
    private int recursionDepth = 0;

    public double findClosest(Point[] points) {
        comparisons = 0;
        recursionDepth = 0;

        Point[] px = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Point[] py = points.clone();
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        return closestUtil(px, py, px.length, 1);
    }

    private double closestUtil(Point[] px, Point[] py, int n, int depth) {
        if (depth > recursionDepth) recursionDepth = depth;
        if (n <= 3) return bruteForce(px, n);

        int mid = n / 2;
        Point midPoint = px[mid];

        Point[] pyl = new Point[mid];
        Point[] pyr = new Point[n - mid];
        int li = 0, ri = 0;
        for (Point p : py) {
            if (p.x <= midPoint.x && li < mid) pyl[li++] = p;
            else pyr[ri++] = p;
        }

        double dl = closestUtil(Arrays.copyOfRange(px, 0, mid), pyl, mid, depth + 1);
        double dr = closestUtil(Arrays.copyOfRange(px, mid, n), pyr, n - mid, depth + 1);
        double d = Math.min(dl, dr);

        return Math.min(d, stripClosest(py, n, midPoint.x, d));
    }

    private double stripClosest(Point[] py, int size, double midX, double d) {
        Point[] strip = new Point[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (Math.abs(py[i].x - midX) < d) {
                strip[j++] = py[i];
            }
        }
        for (int i = 0; i < j; i++) {
            for (int k = i + 1; k < j && (strip[k].y - strip[i].y) < d; k++) {
                comparisons++;
                d = Math.min(d, dist(strip[i], strip[k]));
            }
        }
        return d;
    }

    private double bruteForce(Point[] points, int n) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                min = Math.min(min, dist(points[i], points[j]));
            }
        }
        return min;
    }

    private double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    public int getComparisons() { return comparisons; }
    public int getRecursionDepth() { return recursionDepth; }

    public static class Point {
        public double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }
}
