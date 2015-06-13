package assignment2;

import edu.princeton.cs.introcs.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic data type which represents double-ended queue or deque (pronounced "deck").
 * It is a generalization of a stack and a queue that supports adding and removing items
 * from either the front or the back of the data structure. For more complexity current
 * implementation is based on the array, but it can be based on the {@link java.util.LinkedList}
 * as well.
 *
 * @param <Item> generic type that is stored in {@code Deque}
 * @author Alex Ilyenko
 * @see java.lang.Iterable
 */

public class Deque<Item> implements Iterable<Item> {
    /**
     * Constant holding initial array size
     */
    private static final int MIN_ARRAY_SIZE = 2;
    /**
     * Array structure holding all elements
     */
    private Item[] items;
    /**
     * Values needed for adding and removing operations
     */
    private int itemCount, firstPosition, lastPosition;

    /**
     * Creates an empty {@code Deque} with array implementation.
     * Default array length is 2.
     *
     * @see #MIN_ARRAY_SIZE
     */
    @SuppressWarnings("unchecked")
    public Deque() {
        itemCount = 0;
        items = (Item[]) new Object[MIN_ARRAY_SIZE];
        firstPosition = 0;
        lastPosition = 1;
    }

    /**
     * Checks if {@code Deque} is empty
     *
     * @return {@code true} for empty {@code Deque} and
     * {@code false} if {@code Deque} has at least one item
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of items in {@code Deque}
     *
     * @return {@code int} representing number of items
     */
    public int size() {
        return itemCount;
    }

    /**
     * Adds given item to the front of the {@code Deque} and
     * increases number of items in it by one. Also this method can
     * resize the array if there is no extra space for inserted item
     *
     * @param item given item to add
     * @throws java.lang.NullPointerException if item == {@code null}
     * @see #resize(int, assignment2.Deque.Side)
     * @see #items
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("You can not add Null to deque!");
        }
        if (firstPosition < 0) {
            resize(items.length + itemCount, Side.FRONT);
        }
        items[firstPosition--] = item;
        itemCount++;
    }

    /**
     * Adds given item to the end of the {@code Deque} and
     * increases number of items in it by one. Also this method can
     * resize the array if there is no extra space for inserted item
     *
     * @param item given item to add
     * @throws java.lang.NullPointerException if item == {@code null}
     * @see #resize(int, assignment2.Deque.Side)
     * @see #items
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("You can not add Null to deque!");
        }
        if (lastPosition == items.length) {
            resize(items.length + itemCount, Side.END);
        }
        items[lastPosition++] = item;
        itemCount++;
    }

    /**
     * Removes and returns the item form the front of the {@code Deque}. This method
     * decreases the number of items in the {@code Deque} by one and can shrink the array
     * if there is a lot extra space in the front of it.
     *
     * @return item that was removed from the {@code Deque}
     * @throws java.util.NoSuchElementException if {@code Deque} is already empty
     * @see #resize(int, assignment2.Deque.Side)
     * @see #items
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is already empty!");
        }
        Item item = items[++firstPosition];
        items[firstPosition] = null;
        itemCount--;
        if (itemCount > 0 && firstPosition + items.length - lastPosition >= itemCount << 2) {
            resize(itemCount << 1, Side.BOTH);
        }
        return item;
    }

    /**
     * Removes and returns the item form the end of the {@code Deque}. This method
     * decreases the number of items in the {@code Deque} by one and can shrink the array
     * if there is a lot extra space in the end of it.
     *
     * @return item that was removed from the {@code Deque}
     * @throws java.util.NoSuchElementException if {@code Deque} is already empty
     * @see #resize(int, assignment2.Deque.Side)
     * @see #items
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is already empty!");
        }
        Item item = items[--lastPosition];
        items[lastPosition] = null;
        itemCount--;
        if (itemCount > 0 && firstPosition + items.length - lastPosition >= itemCount << 2) {
            resize(itemCount << 1, Side.BOTH);
        }
        return item;
    }

    /**
     * Gets the {@code Iterator} for iterating through all elements in {@code Deque} in order from front to end
     *
     * @return {@code Iterator}
     * @see java.util.Iterator
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            /**
             * Default position of {@code Iterator} is first position of item in {@code Deque}
             * @see assignment2.Deque#firstPosition
             */
            private int cursor = firstPosition;

            /**
             * Checks if {@code Deque} has next item for iterating
             * @return {@code true} if {@code Deque} has left items for iterating and {@code false} if not
             * @see assignment2.Deque#lastPosition
             */
            @Override
            public boolean hasNext() {
                return cursor != lastPosition - 1;
            }

            /**
             * Returns next item in {@code Deque} in order from front to end
             * @return next item
             * @throws java.util.NoSuchElementException if there is no elements left in {@code Deque}
             * @see #hasNext()
             */
            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("You've already reached the end of deque!");
                }
                return items[++cursor];
            }

            /**
             * Unsupported remove operation
             * @throws java.lang.UnsupportedOperationException
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove action is not supported!");
            }
        };
    }

    /**
     * Increases or decreases the size of the array with items by given capacity from given side.
     * This method does nothing if capacity is less than the number of items in {@code Deque}
     *
     * @param capacity size of the new array
     * @param side     {@code Side} of the resizing
     * @see assignment2.Deque.Side
     * @see java.lang.System#arraycopy(Object, int, Object, int, int)
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity, Side side) {
        if (capacity < itemCount) {
            return;
        }
        Item[] tmpArr = (Item[]) new Object[capacity];
        int destinationPosition = 0;
        int length = lastPosition;
        int start = 0;
        switch (side){
            case BOTH :
                start = firstPosition + 1;
                destinationPosition = (capacity - itemCount) >> 1;
                firstPosition = destinationPosition - 1;
                length = itemCount;
                lastPosition = destinationPosition + length;
                break;
            case FRONT:
                if (capacity < items.length) {
                    start = firstPosition;
                    int difference = items.length - capacity;
                    firstPosition -= difference;
                    lastPosition -= difference;
                    destinationPosition = firstPosition;
                    length = itemCount + 1;
                } else {
                    firstPosition = itemCount - 1;
                    lastPosition += itemCount;
                    destinationPosition = length = itemCount;
                }
                break;
            default:
                // no actions required if side is END
                break;
        }

        System.arraycopy(items, start, tmpArr, destinationPosition, length);
        items = tmpArr;
    }

    /**
     * Enum representing sides of the {@code Deque}
     */
    private enum Side {
        /**
         * Front side
         */
        FRONT,
        /**
         * End side
         */
        END,
        /**
         * Both sides
         */
        BOTH
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                deque.addFirst(i);
            } else {
                deque.addLast(i);
            }
        }
        StdOut.println(deque.size());
        for (int i = 0; i < 12; i++) {
            if (i % 3 == 0) {
                deque.removeLast();
            } else {
                deque.removeFirst();
            }
        }
        StdOut.println(deque.size());
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                deque.addFirst(i);
            } else {
                deque.addLast(i);
            }
        }
        StdOut.println(deque.size());
    }

}