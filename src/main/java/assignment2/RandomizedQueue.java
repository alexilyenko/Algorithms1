package assignment2;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic data type representing randomized queue which is similar to a stack or queue,
 * except that the item removed is chosen uniformly at random from items in the data structure.
 * For more complexity current implementation is based on the array,
 * but it can be based on the {@link java.util.LinkedList}
 *
 * @param <Item> generic type that is stored in {@code RandomizedQueue}
 * @author Alex Ilyenko
 * @see java.lang.Iterable
 * @see assignment2.RandomizedQueue.RandomizedQueueIterator
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * Constant holding initial array size
     */
    private static final int MIN_ARRAY_SIZE = 2;
    /**
     * Array structure holding all elements
     */
    private Item[] items;
    /**
     * Number of elements in {@code RandomizedQueue}
     */
    private int count = 0;

    /**
     * Creates a an empty {@code RandomizedQueue} with array implementation.
     * Default array length is 2.
     *
     * @see #MIN_ARRAY_SIZE
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[MIN_ARRAY_SIZE];
    }

    /**
     * Private constructor that is used by {@code RandomizedQueueIterator} to create its copy
     *
     * @param array array from the original {@code RandomizedQueue}
     * @param count count of the item in original {@code RandomizedQueue}
     * @see assignment2.RandomizedQueue.RandomizedQueueIterator
     */
    @SuppressWarnings("unchecked")
    private RandomizedQueue(Item[] array, int count) {
        int length = array.length;
        items = (Item[]) new Object[length];
        System.arraycopy(array, 0, items, 0, length);
        this.count = count;
    }

    /**
     * Checks if {@code RandomizedQueue} is empty
     *
     * @return {@code true} for empty {@code RandomizedQueue} and
     * {@code false} if {@code RandomizedQueue} has at least one item
     */
    public boolean isEmpty() {
        return count == 0;
    }          // is the queue empty?

    /**
     * Returns the number of items in {@code RandomizedQueue}
     *
     * @return {@code int} representing number of items
     */
    public int size() {
        return count;
    }                     // return the number of items on the queue

    /**
     * Adds given item to the end of the {@code RandomizedQueue} and
     * increases the number of items in it by one. Also this method can
     * resize the array structure if there is no extra space left for inserted item
     *
     * @param item given item to add
     * @throws java.lang.NullPointerException if item == {@code null}
     * @see #resize(int)
     * @see #items
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("You can not add Null to randomized queue!");
        }
        if (count == items.length) {
            resize(count << 1);
        }
        items[count++] = item;
    }

    /**
     * Randomly removes and returns the item from the {@code RandomizedQueue}. This method
     * decreases the number of items in the {@code RandomizedQueue} by one and can shrink the array
     * if there is a lot extra space in the end of it.
     *
     * @return item that was removed from the {@code RandomizedQueue}
     * @throws java.util.NoSuchElementException if {@code RandomizedQueue} is already empty
     * @see #resize(int)
     * @see #items
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized queue is already empty!");
        }
        int index = StdRandom.uniform(0, count);
        Item item = items[index];
        items[index] = items[--count];
        items[count] = null;
        if (count > 0 && count <= items.length >> 2) {
            resize(items.length >> 1);
        }
        return item;
    }

    /**
     * Returns but does not remove the random item in {@code RandomizedQueue}
     *
     * @return random item from the {@code RandomizedQueue}
     * @throws java.util.NoSuchElementException if {@code RandomizedQueue} is already empty
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized queue is already empty!");
        }
        return items[StdRandom.uniform(0, count)];
    }

    /**
     * Returns an independent iterator over the elements in this {@code RandomizedQueue} in random sequence.
     *
     * @return the {@code RandomizedQueueIterator} over the elements in this queue in random sequence
     * @see assignment2.RandomizedQueue.RandomizedQueueIterator
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    /**
     * Increases or decreases the size of the array with items by given capacity.
     * This method does nothing if capacity is less than the number of items in {@code Deque}
     *
     * @param capacity size of the new array
     * @see java.lang.System#arraycopy(Object, int, Object, int, int)
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        if (capacity < count) {
            return;
        }
        Item[] tmpArr = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, tmpArr, 0, count);
        items = tmpArr;
    }

    /**
     * Iterator implementation for {@code RandomizedQueue} for iterating through all its elements in random order
     *
     * @param <T> generic type that is stored in {@code RandomizedQueue}
     * @see java.util.Iterator
     */
    private class RandomizedQueueIterator<T> implements Iterator<T> {
        /**
         * Copy of the original {@code RandomizedQueue} for iterating through all its elements
         */
        @SuppressWarnings("unchecked")
        private RandomizedQueue<T> randomizedQueue = new RandomizedQueue<>((T[]) items, count);

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return randomizedQueue.size() > 0;
        }

        /**
         * Returns the next random element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("You've already reached the end of randomized queue!");
            }
            return randomizedQueue.dequeue();
        }

        /**
         * Unsupported remove operation
         *
         * @throws java.lang.UnsupportedOperationException
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove action is not supported!");
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        StdOut.println("Queue is empty: " + randomizedQueue.isEmpty());
        for (int i = 0; i < 30; i++) {
            randomizedQueue.enqueue(i);
            if (i % 3 == 0) {
                randomizedQueue.dequeue();
            }
        }
        StdOut.println("Size: " + randomizedQueue.size());
        int i = 0;
        for (Integer a : randomizedQueue) {
            StdOut.print(a + " ");
            i++;
        }
        StdOut.println();
        StdOut.println("Number of iterations: " + i);
        for (Integer a : randomizedQueue) {
            StdOut.print(a + " ");
        }
        StdOut.println();
        StdOut.print("Random pick ups: ");
        for (int j = 0; j < 5; j++) {
            StdOut.print(randomizedQueue.sample() + " ");
        }
    }
}
