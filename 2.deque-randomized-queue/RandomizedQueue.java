/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        requireNonNull(item);
        if (size >= items.length) {
            items = arrayCopy(items, items.length * 2);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");

        int index = StdRandom.uniformInt(size);
        Item res = items[index];

        items[index] = items[size - 1];
        items[--size] = null;

        if (size > 1 && size < items.length / 4) {
            items = arrayCopy(items, items.length / 2);
        }

        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");
        return items[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(arrayCopy(items, size));
    }

    // Using java.util.Arrays or System.arraycopy is restricted by the terms of the task
    private Item[] arrayCopy(Item[] array, int length) {
        // Using java.util.Arrays or System.arraycopy is restricted by the terms of the task
        Item[] copy = (Item[]) new Object[length];
        for (int i = 0; i < Math.min(length, array.length); i++) {
            copy[i] = array[i];
        }
        return copy;
    }

    private void requireNonNull(Item item) {
        if (item == null) throw new IllegalArgumentException("Item musn't be null");
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] items;
        private int pointer;

        RandomizedQueueIterator(Item[] items) {
            this.items = items;
            pointer = items.length - 1;
        }

        public boolean hasNext() {
            return pointer >= 0;
        }

        public Item next() {
            if (pointer == -1) throw new NoSuchElementException("Iterator has no more items");
            int index = StdRandom.uniformInt(pointer + 1);
            Item res = items[index];
            items[index] = items[pointer];
            items[pointer--] = null;
            return res;
        }

    }

    // unit testing (required according assignment specification, JUnit tests are located near in this package)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        StdOut.println(queue.isEmpty());
        StdOut.println(queue.size());

        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        queue.enqueue("Fourth");
        queue.enqueue("Fifth");
        queue.enqueue("Sixth");


        StdOut.println(queue.isEmpty());
        StdOut.println(queue.size());

        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());

        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());

        for (String s : queue) {
            StdOut.println(s);
        }
    }

}
