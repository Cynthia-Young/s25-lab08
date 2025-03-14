package edu.cmu.cs.cs214.rec08.queue;

import java.util.ArrayDeque;
import java.util.Deque;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

/**
 * Thread-safe implementation of UnboundedBlockingQueue.
 * This implementation blocks on dequeue when the queue is empty until
 * another thread enqueues an element.
 */
@ThreadSafe
public class UnboundedBlockingQueue<E> implements SimpleQueue<E> {
    @GuardedBy("this")
    private Deque<E> queue = new ArrayDeque<>();

    public UnboundedBlockingQueue() { }

    /**
     * Checks if the queue is empty.
     * Synchronized to ensure thread-safety.
     */
    @Override
    public synchronized boolean isEmpty() { 
        return queue.isEmpty(); 
    }

    /**
     * Returns the current size of the queue.
     * Synchronized to ensure thread-safety.
     */
    @Override
    public synchronized int size() { 
        return queue.size(); 
    }

    /**
     * Returns the first element without removing it.
     * Synchronized to ensure thread-safety.
     */
    @Override
    public synchronized E peek() { 
        return queue.peek(); 
    }

    /**
     * Adds an element to the queue and notifies any waiting threads
     * that an element is now available for dequeue.
     * Synchronized to ensure thread-safety.
     */
    public synchronized void enqueue(E element) { 
        queue.add(element); 
        // Notify any waiting threads that an element is available
        notify();
    }

    /**
     * TODO:  Change this method to block (waiting for an enqueue) rather
     * than throw an exception.  Completing this task may require
     * modifying other methods.
     */

     /**
     * Removes and returns the first element from the queue.
     * If the queue is empty, this method blocks until an element
     * becomes available.
     * Synchronized to ensure thread-safety.
     */
    @Override
    public synchronized E dequeue() throws InterruptedException { 
        // While loop is used to handle spurious wakeups
        while (queue.isEmpty()) {
            // If queue is empty, wait until notified
            wait();
        }
        // Once notified and queue is not empty, remove and return the element
        return queue.remove();
    }

    /**
     * Returns a string representation of the queue.
     * Synchronized to ensure thread-safety.
     */
    @Override
    public synchronized String toString() { 
        return queue.toString(); 
    }
}
