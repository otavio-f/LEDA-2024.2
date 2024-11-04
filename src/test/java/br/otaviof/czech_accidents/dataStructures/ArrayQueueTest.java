package br.otaviof.czech_accidents.dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {

    public Queue<Integer> queue;

    @BeforeEach
    void setup() {
        queue = new ArrayQueue<>(10);
    }

    @Test
    void testEnqueue() {
        queue.enqueue(1);
        assertEquals(1, queue.peekHead());
    }

    @Test
    void testDequeue() {
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    @Test
    void testPeekTail() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.peekTail());
    }

    @Test
    void testPeekHead() {
        queue.enqueue(1);
        queue.enqueue(12);
        assertEquals(1, queue.peekHead());
    }

    @Test
    void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testIsFull() {
        queue = null; // queue with size 3
        assertFalse(queue.isFull());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertTrue(queue.isFull());
    }
}