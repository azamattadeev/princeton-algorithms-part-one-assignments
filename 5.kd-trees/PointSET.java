import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private final SET<Point2D> pointsSet;

    public PointSET() {
        this.pointsSet = new SET<>();
    }

    public void insert(Point2D p) {
        pointsSet.add(p);
    }

    public boolean contains(Point2D p) {
        return pointsSet.contains(p);
    }

    public boolean isEmpty() {
        return pointsSet.isEmpty();
    }

    public int size() {
        return pointsSet.size();
    }

    public void draw() {
        pointsSet.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rect shouldn't be null");
        List<Point2D> range = new ArrayList<>();
        for (Point2D p : pointsSet) {
            if (rect.contains(p)) range.add(p);
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point shouldn't be null");
        Point2D nearest = null;
        double nearDist = Double.NaN;
        for (Point2D point : pointsSet) {
            if (nearest != null) {
                double distance = p.distanceSquaredTo(point);
                if (distance < nearDist) {
                    nearDist = distance;
                    nearest = point;
                }
            }
            else {
                nearest = point;
                nearDist = p.distanceSquaredTo(point);
            }
        }

        return nearest;
    }

}
