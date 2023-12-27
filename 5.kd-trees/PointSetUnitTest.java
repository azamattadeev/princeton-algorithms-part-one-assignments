import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests both implementations (PointSET and KdTree) using same test set
 */
@RunWith(Parameterized.class)
public class PointSetUnitTest {
    private IntersectionChecker checker;
    private IntersectionCheckerFactory factory;

    public PointSetUnitTest(IntersectionCheckerFactory factory) {
        this.factory = factory;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                { new PointSetAdapterFactory() },
                { new KdTreeAdapterFactory() }
        });
    }

    @Before
    public void createNewList() {
        checker = factory.createNewChecker();
    }

    private static final List<Point2D> POINTS_5_DISTINCT = List.of(
            new Point2D(0.1, 0.9),
            new Point2D(0.4, 0.2),
            new Point2D(0.23, 0.88),
            new Point2D(0.8, 0.3),
            new Point2D(0.22, 0.33)
    );

    private static final List<Point2D> POINTS_3_DISTINCT_3_COPIES = List.of(
            new Point2D(0.1, 0.9),
            new Point2D(0.4, 0.2),
            new Point2D(0.4, 0.2),
            new Point2D(0.23, 0.88),
            new Point2D(0.23, 0.88),
            new Point2D(0.23, 0.88)
    );

    private static final List<Point2D> POINTS_10_DISTINCT = List.of(
            new Point2D(0.372, 0.497),
            new Point2D(0.564, 0.413),
            new Point2D(0.226, 0.577),
            new Point2D(0.144, 0.179),
            new Point2D(0.083, 0.51),
            new Point2D(0.32, 0.708),
            new Point2D(0.417, 0.362),
            new Point2D(0.862, 0.825),
            new Point2D(0.785, 0.725),
            new Point2D(0.499, 0.208)
    );


    @Test
    public void isEmptyTest() {
        assertTrue("PointSET isn't empty after initialization",
                   checker.isEmpty());
    }

    @Test
    public void notEmptyTest() {
        checker.insert(new Point2D(0, 1));

        assertFalse("PointSet is empty after inserting a point",
                    checker.isEmpty());
    }

    @Test
    public void size0Test() {
        assertEquals("Size of empty PointSET isn't 0",
                     0, checker.size());
    }

    @Test
    public void insert5DistinctSizeTest() {
        POINTS_5_DISTINCT.forEach(checker::insert);

        assertEquals("Size after inserting 5 distinct points is wrong",
                     5, checker.size());
    }

    @Test
    public void insertWithSamePointsSizeTest() {
        POINTS_3_DISTINCT_3_COPIES.forEach(checker::insert);

        assertEquals("Size after inserting 3 distinct points and 3 copies is wrong",
                     3, checker.size());
    }

    @Test
    public void rangeEmptyTest() {
        POINTS_5_DISTINCT.forEach(checker::insert);
        RectHV rect = new RectHV(0.11, 0.88, 0.13, 0.89);

        assertFalse("Rectangle must be empty",
                    checker.range(rect).iterator().hasNext());
    }

    @Test
    public void rangeAllTest() {
        POINTS_10_DISTINCT.forEach(checker::insert);
        RectHV rect = new RectHV(0, 0, 1, 1);

        Iterable<Point2D> inRect = checker.range(rect);
        int count = 0;
        for (Point2D point2D : inRect) {
            count++;
        }

        assertEquals("Rectangle must contain all 10 points",
                     10, count);
    }

    @Test
    public void range1Test() {
        POINTS_10_DISTINCT.forEach(checker::insert);
        RectHV rect = new RectHV(0.079, 0.49, 0.084, 0.534);

        List<Point2D> rangeRes = new ArrayList<>();
        checker.range(rect).iterator().forEachRemaining(rangeRes::add);

        assertEquals("Range result must contain exactly 1 value",
                     1, rangeRes.size());
        assertEquals("Range returns wrong point",
                     new Point2D(0.083, 0.51), rangeRes.get(0));
    }

    @Test
    public void range3Test() {
        POINTS_10_DISTINCT.forEach(checker::insert);
        RectHV rect = new RectHV(0.05, 0.35, 0.45, 0.55);

        Set<Point2D> rangeRes = new HashSet<>();
        checker.range(rect).iterator().forEachRemaining(rangeRes::add);

        List<Point2D> correct = List.of(new Point2D(0.417, 0.362),
                                        new Point2D(0.372, 0.497),
                                        new Point2D(0.083, 0.51));

        assertEquals("Range result must contain exactly 3 values",
                     3, rangeRes.size());

        assertTrue("Range returns wrong set of points",
                   rangeRes.containsAll(correct));
    }

    @Test
    public void nearestTest() {
        POINTS_5_DISTINCT.forEach(checker::insert);
        assertEquals("Returned nearest point is wrong",
                     new Point2D(0.4, 0.2), checker.nearest(new Point2D(0.45, 0.15)));
    }

    @Test
    public void nearestTest2() {
        POINTS_10_DISTINCT.forEach(checker::insert);
        assertEquals("Returned nearest point is wrong",
                     new Point2D(0.32, 0.708), checker.nearest(new Point2D(0.55, 0.73)));
    }

    @Test
    public void nearestItselfTest() {
        POINTS_10_DISTINCT.forEach(checker::insert);
        checker.insert(new Point2D(0.55, 0.73));
        assertEquals("Returned nearest point is wrong",
                     new Point2D(0.55, 0.73), checker.nearest(new Point2D(0.55, 0.73)));
    }

    public interface IntersectionCheckerFactory {
        IntersectionChecker createNewChecker();
    }

    private static class PointSetAdapterFactory implements IntersectionCheckerFactory {
        @Override
        public IntersectionChecker createNewChecker() {
            return new PointSetTestAdapter();
        }
    }

    private static class KdTreeAdapterFactory implements IntersectionCheckerFactory {
        @Override
        public IntersectionChecker createNewChecker() {
            return new KdTreeTestAdapter();
        }
    }

}
