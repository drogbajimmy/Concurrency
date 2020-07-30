package queue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue<E> {

    private int count;
    private LinkedList<E> list = new LinkedList<>();
    private final ReentrantLock putLock = new ReentrantLock();
    private final ReentrantLock takeLock = new ReentrantLock();

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean contains(E o) {

        if (o == null) return false;
        fullyLock();
        try {
            return list.contains(o);
        } finally {
            fullyUnlock();
        }
    }

    public boolean add(E o) {

        final ReentrantLock putLock = this.putLock;

        try {
            putLock.lock();
            list.add(o);
            count++;

            return true;
        } finally {
            putLock.unlock();
        }
    }

    public boolean addAll(Collection<E> c) {

        if (c == null) throw new NullPointerException();

        boolean modified = false;

        for (E o : c) {
            add(o);
            modified = true;
        }

        return modified;
    }

    public void clear() {
        while (poll() != null)
            ;
    }

    public E poll() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            if (count == 0) {
                return null;
            } else {
                E o = list.poll();
                count--;
                return o;
            }
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * Locks to prevent both puts and takes.
     */
    void fullyLock() {
        putLock.lock();
        takeLock.lock();
    }

    /**
     * Unlocks to allow both puts and takes.
     */
    void fullyUnlock() {
        takeLock.unlock();
        putLock.unlock();
    }
}
