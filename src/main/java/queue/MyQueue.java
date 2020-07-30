package queue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main queue class
 *
 * @author  Jimmy Zhou
 * @version 1.0
 * @since   2020-07-30
 */
public class MyQueue<E> {

    private int count;
    private LinkedList<E> list = new LinkedList<>();
    private final ReentrantLock queueLock = new ReentrantLock();

    /**
     * Returns the number of elements in this queue.
     *
     * @return the number of elements in this queue
     */
    public int size() {
        return count;
    }

    /**
     * Returns if this collection contains no elements.
     *
     * @return true if this collection contains no elements
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Inserts the specified element into this queue, returning
     * <tt>true</tt>
     *
     * @param o the element to add
     * @return true
     */
    public boolean add(E o) {

        final ReentrantLock queueLock = this.queueLock;

        try {
            queueLock.lock();
            list.add(o);
            count++;

            return true;
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Adds all of the elements in the specified collection to this
     * queue.
     *
     * @param c collection containing elements to be added to this queue
     * @return true if this queue changed as a result of the call
     * @throws NullPointerException if the specified collection contains a
     *         null element and this queue does not permit null elements,
     *         or if the specified collection is null
     */
    public boolean addAll(Collection<E> c) {

        if (c == null) throw new NullPointerException();

        boolean modified = false;

        for (E o : c) {
            add(o);
            modified = true;
        }

        return modified;
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    public E poll() {
        final ReentrantLock queueLock = this.queueLock;
        queueLock.lock();
        try {
            if (count == 0) {
                return null;
            } else {
                E o = list.poll();
                count--;
                return o;
            }
        } finally {
            queueLock.unlock();
        }
    }
}
