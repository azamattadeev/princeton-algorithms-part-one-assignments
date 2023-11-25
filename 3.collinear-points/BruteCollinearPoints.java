/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<Point[]> lines = new ArrayList<>();
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.stream(points).forEach(this::checkNull);

        points = Arrays.copyOf(points, points.length);

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].equals(points[i - 1])) throw new IllegalArgumentException();
        }

        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {

                        if (points[i1].slopeTo(points[i2]) == points[i2].slopeTo(points[i3])
                                && points[i2].slopeTo(points[i3]) == points[i3].slopeTo(
                                points[i4])) {

                            Point[] line = null;
                            for (int i5 = i4 + 1; i5 < points.length; i5++) {
                                if (points[i1].slopeTo(points[i2]) == points[i4].slopeTo(
                                        points[i5])) {
                                    line = new Point[] {
                                            points[i1],
                                            points[i2],
                                            points[i3],
                                            points[i4],
                                            points[i5]
                                    };
                                }
                            }

                            if (line == null) {
                                line = new Point[] {
                                        points[i1],
                                        points[i2],
                                        points[i3],
                                        points[i4]
                                };
                            }

                            if (line.length == 4) {
                                addLineIfNotAdded(line);
                            }
                            else {
                                lines.add(line);
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        if (lineSegments == null) {
            lineSegments = lines.stream()
                                .map(this::getLineSegment)
                                .toArray(LineSegment[]::new);
        }
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private void checkNull(Object notNullable) {
        if (notNullable == null) throw new IllegalArgumentException();
    }

    private LineSegment getLineSegment(Point[] line) {
        return new LineSegment(line[0], line[line.length - 1]);
    }

    private void addLineIfNotAdded(Point[] line) {
        boolean contains = false;
        for (Point[] added : lines) {
            if (added.length == 5 && isSameLine(added, line)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            lines.add(line);
        }
    }

    private boolean isSameLine(Point[] l1, Point[] l2) {
        int sharedPoints = 0;
        for (int i = 0; i < l1.length; i++) {
            if (l1[i].equals(l2[0])) sharedPoints++;
            if (l1[i].equals(l2[1])) sharedPoints++;
            if (l1[i].equals(l2[2])) sharedPoints++;
            if (l1[i].equals(l2[3])) sharedPoints++;
        }
        return sharedPoints > 1;
    }

}
