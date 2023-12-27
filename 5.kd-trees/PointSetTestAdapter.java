/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Adapter for parametrized test.
 * <p>
 * KdTree and PointSET shouldn't implement any interfaces by the terms of assessment.
 * <p>
 * However, for testing both implementations on the same test classes should implement same
 * interface.
 * <p>
 * So I use adapters which implement this interface
 */
public class PointSetTestAdapter implements IntersectionChecker {
    private PointSET pointSET = new PointSET();

    public boolean isEmpty() {
        return pointSET.isEmpty();
    }

    public int size() {
        return pointSET.size();
    }

    public void insert(Point2D p) {
        pointSET.insert(p);
    }

    public boolean contains(Point2D p) {
        return pointSET.contains(p);
    }

    public void draw() {
        pointSET.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        return pointSET.range(rect);
    }

    public Point2D nearest(Point2D p) {
        return pointSET.nearest(p);
    }
}
