/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class RandomizedQueueUnitTest {

    private static final List<String> DIGITS =
            List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    @Test
    public void isEmptyTest() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        assertTrue("Queue must be empty after init", queue.isEmpty());

        queue.enqueue(2);
        assertFalse("Queue mustn't be empty", queue.isEmpty());

        queue.dequeue();
        assertTrue("Queue must be empty after removing all elements", queue.isEmpty());
    }

    @Test
    public void sizeTest() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        assertEquals("Queue must have size 0 after init", 0, queue.size());

        DIGITS.forEach(queue::enqueue);
        assertEquals("Wrong queue size", DIGITS.size(), queue.size());

        for (int i = 0; i < DIGITS.size() - 1; i++) {
            queue.dequeue();
        }

        assertEquals("Wrong queue size", 1, queue.size());
    }

    @Test
    public void enqueueDequeueTest() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        List<String> checkList = new ArrayList<>(DIGITS.subList(0, 3));

        DIGITS.subList(0, 3).forEach(queue::enqueue);

        for (int i = 0; i < 3; i++) {
            String dequeued = queue.dequeue();
            assertTrue("Dequeued element missing from the check list",
                       checkList.remove(dequeued));
        }

        assertTrue("Queue must be empty, but it doesn't", queue.isEmpty());
        assertThrows("Empty queue doesn't throw NoSuchElementException on dequeue() call",
                     NoSuchElementException.class, queue::dequeue);
    }

    @Test
    public void sampleMethodUnitTest() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        List<Integer> checkList = new ArrayList<>(Arrays.asList(1, 2, 3));
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        for (int i = 0; i < 3; i++) {
            assertTrue("sample() returned value which shouldn't be in queue",
                       checkList.contains(queue.sample()));
            checkList.remove(queue.dequeue());
        }

        assertThrows("Empty queue doesn't throw NoSuchElementException on sample() call",
                     NoSuchElementException.class, queue::sample);
    }

    @Test
    public void iteratorTest() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        DIGITS.forEach(queue::enqueue);

        List<String> checkList = new ArrayList<>(DIGITS);
        // foreach uses iterator.next() and hasNext() method under the hood
        for (String s : queue) {
            assertTrue("Randomized queue iterator gives wrong values", checkList.remove(s));
        }
        assertTrue(checkList.isEmpty());

        // Make sure that iterator does not modify queue
        List<String> checkList2 = new ArrayList<>(DIGITS);
        for (String s : queue) {
            assertTrue("Iterator modifies queue, but it mustn't", checkList2.remove(s));
        }
    }

}
