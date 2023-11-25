/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        requireNonNull(item);
        Node node = new Node(item);

        if (size > 0) {
            node.next = first;
            first.previous = node;
            first = node;
        }
        else {
            first = node;
            last = node;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        requireNonNull(item);
        Node node = new Node(item);

        if (size > 0) {
            node.previous = last;
            last.next = node;
            last = node;
        }
        else {
            first = node;
            last = node;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException("Collection is empty");

        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }
        else {
            last = null;
        }

        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("Collection is empty");

        Item item = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }

        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void requireNonNull(Item item) {
        if (item == null) throw new IllegalArgumentException("Item musn't be null");
    }

    private class Node {
        Item item;
        Node previous;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException("Iterator has no more elements");
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // unit testing (required according assignment specification, JUnit tests are located near in this package)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        StdOut.println(deque.isEmpty()); // true
        StdOut.println(deque.size());  // 0

        deque.addFirst("Third");
        deque.addFirst("Second");
        deque.addLast("Fourth");
        deque.addFirst("First");

        StdOut.println(deque.isEmpty()); // false
        StdOut.println(deque.size()); // 4

        deque.addLast("Fifth");

        StdOut.println(deque.removeFirst()); // First
        StdOut.println(deque.removeLast()); // Fifth

        for (String s : deque) {
            StdOut.println(s);
        }
    }

}
