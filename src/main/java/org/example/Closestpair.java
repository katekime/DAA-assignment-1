// ClosestPair.java
package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class Closestpair {
    private int comparisons = 0;
    private int recursionDepth = 0;
    private static final int BRUTE_FORCE_CUTOFF = 3;

    public double findClosest(Point[] points) {
        if (points == null || points.length < 2) return Double.MAX_VALUE;
        comparisons = 0;
        recursionDepth = 0;

        Point[] px = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));

        Point[] py = points.clone();
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        return closestUtil(px, py, 0, px.length - 1, 1);
    }

    private double closestUtil(Point[] px, Point[] py, int low, int high, int depth) {
        recursionDepth = Math.max(recursionDepth, depth);
        int n = high - low + 1;

        if (n <= BRUTE_FORCE_CUTOFF) {
            return bruteForce(px, low, high);
        }

        int mid = low + n / 2;
        Point midPoint = px[mid];

        Point[] pyl = new Point[mid - low];
        Point[] pyr = new Point[high - mid + 1];
        int li = 0, ri = 0;

        for (Point p : py) {
            if (isPointInRange(p, px, low, high)) {
                if (p.x < midPoint.x) {
                    if (li < pyl.length) pyl[li++] = p;
                } else {
                    if (ri < pyr.length) pyr[ri++] = p;
                }
            }
        }

        double dl = closestUtil(px, pyl, low, mid - 1, depth + 1);
        double dr = closestUtil(px, pyr, mid, high, depth + 1);
        double d = Math.min(dl, dr);

        return Math.min(d, stripClosest(py, n, midPoint.x, d));
    }

    private double stripClosest(Point[] py, int size, double midX, double d) {
        List<Point> stripList = new ArrayList<>(size);
        for (Point p : py) {
            if (Math.abs(p.x - midX) < d) {
                stripList.add(p);
            }
        }

        Point[] strip = stripList.toArray(new Point[0]);
        int j = strip.length;

        for (int i = 0; i < j; i++) {
            for (int k = i + 1; k < j; k++) {
                if ((strip[k].y - strip[i].y) >= d) break;

                comparisons++;
                d = Math.min(d, dist(strip[i], strip[k]));
            }
        }
        return d;
    }

    private double bruteForce(Point[] points, int low, int high) {
        double min = Double.MAX_VALUE;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                comparisons++;
                min = Math.min(min, dist(points[i], points[j]));
            }
        }
        return min;
    }

    private double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    private boolean isPointInRange(Point p, Point[] px, int low, int high) {
        for (int i = low; i <= high; i++) {
            if (p.x == px[i].x && p.y == px[i].y) {
                return true;
            }
        }
        return false;
    }

    public int getComparisons() { return comparisons; }
    public int getRecursionDepth() { return recursionDepth; }
}