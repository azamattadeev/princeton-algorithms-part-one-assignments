/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point point) {
        return (y != point.y)
               ? y - point.y
               : x - point.x;
    }

    public double slopeTo(Point point) {
        if (y == point.y && x == point.x) return Double.NEGATIVE_INFINITY;
        if (x == point.x) return Double.POSITIVE_INFINITY;
        if (y == point.y) return 0;
        return (double) (point.y - y) / (point.x - x);
    }

    public Comparator<Point> slopeOrder() {
        return (q, r) -> Double.compare(
                slopeTo(q),
                slopeTo(r)
        );
    }

}
