/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<Point[]> preLineSegments = new ArrayList<>();
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.stream(points).forEach(this::checkNull);

        points = Arrays.copyOf(points, points.length);

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].equals(points[i - 1])) throw new IllegalArgumentException();
        }

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {
            Point origin = pointsCopy[i];
            Arrays.sort(points, origin.slopeOrder());

            int sameCounter = 1;
            for (int j = 1; j <= points.length; j++) {
                if (j != points.length && origin.slopeTo(points[j]) == origin.slopeTo(
                        points[j - 1])) {
                    sameCounter++;
                }
                else {
                    if (sameCounter > 2) {
                        Point[] line = new Point[sameCounter + 1];
                        for (int k = 0; k < line.length - 1; k++) {
                            line[k] = points[j - sameCounter + k];
                        }
                        line[line.length - 1] = origin;

                        addIfUnique(toPreLineSegment(line));
                    }
                    sameCounter = 1;
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        if (lineSegments == null) {
            lineSegments = preLineSegments.stream()
                                          .map((pls) -> new LineSegment(pls[0], pls[1]))
                                          .toArray(LineSegment[]::new);
        }
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private void checkNull(Object notNullable) {
        if (notNullable == null) throw new IllegalArgumentException();
    }

    private Point[] toPreLineSegment(Point[] line) {
        Arrays.sort(line);
        return new Point[] { line[0], line[line.length - 1] };
    }

    private void addIfUnique(Point[] newSegment) {
        for (Point[] segment : preLineSegments) {
            if (segment[0] == newSegment[0] && segment[1] == newSegment[1]
                    || segment[0] == newSegment[1] && segment[1] == newSegment[0]) {
                return;
            }
        }
        preLineSegments.add(newSegment);
    }

}
