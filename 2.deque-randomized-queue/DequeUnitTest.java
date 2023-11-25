/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DequeUnitTest {

    private static final List<String> DIGITS =
            List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    @Test
    public void isEmptyTest() {
        Deque<Integer> deque = new Deque<>();
        assertTrue("Deque must be empty after init", deque.isEmpty());

        deque.addFirst(2);
        assertFalse("Deque mustn't be empty", deque.isEmpty());

        deque.removeLast();
        assertTrue("Deque must be empty after removing all elements", deque.isEmpty());
    }

    @Test
    public void sizeTest() {
        Deque<String> deque = new Deque<>();
        assertEquals("Deque must have size 0 after init", 0, deque.size());

        DIGITS.forEach(deque::addLast);
        assertEquals("Wrong deque size", DIGITS.size(), deque.size());

        for (int i = 0; i < DIGITS.size() - 1; i++) {
            deque.removeLast();
        }

        assertEquals("Wrong queue size", 1, deque.size());
    }

    @Test
    public void addRemoveFirstTest() {
        Deque<String> deque = new Deque<>();
        DIGITS.subList(4, DIGITS.size())
              .forEach(deque::addLast);

        List<String> startDigits = new ArrayList<>(DIGITS.subList(0, 4));
        Collections.reverse(startDigits);

        startDigits.forEach(deque::addFirst);

        for (String digit : DIGITS) {
            assertEquals("The content of the deque differs from the reference list", digit,
                         deque.removeFirst());
        }
    }

    @Test
    public void removeLastTest() {
        Deque<String> deque = new Deque<>();
        DIGITS.forEach(deque::addLast);

        List<String> reverseDigits = new ArrayList<>(DIGITS);
        Collections.reverse(reverseDigits);

        for (String digit : reverseDigits) {
            assertEquals("The content of the deque differs from the reference list", digit,
                         deque.removeLast());
        }
    }

    @Test
    public void removeLastAndFirstTest() {
        Deque<String> deque = new Deque<>();
        DIGITS.forEach(deque::addLast);

        deque.removeFirst();
        deque.removeLast();

        for (String digit : DIGITS.subList(1, DIGITS.size() - 1)) {
            assertEquals("The content of the deque differs from the reference", digit,
                         deque.removeFirst());
        }
    }

    @Test
    public void iteratorTest() {
        Deque<String> deque = new Deque<>();
        DIGITS.forEach(deque::addLast);

        int digitsI = 0;

        // foreach uses iterator.next() and hasNext() method under the hood
        for (String s : deque) {
            assertEquals("Deque iterator gives wrong values", DIGITS.get(digitsI++), s);
        }

        // Make sure that iterator does not modify deque
        for (String s : DIGITS) {
            assertEquals("Iterator modifies deque, but it mustn't", s, deque.removeFirst());
        }
    }

}
