package br.otaviof.czech_accidents.dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {

    public Queue<Integer> queue;

    @BeforeEach
    void setup() {
        queue = new ArrayQueue<>(5);
    }

    @Test
    void testEnqueue() {
        queue.enqueue(1);
        assertEquals(1, queue.peekHead());
    }

    @Test
    void testEnqueueOnFullThrows() {
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());

        assertThrowsExactly(FullException.class, ()->
                queue.enqueue(TestUtils.genInt()));
    }

    @Test
    void testDequeue() {
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    @Test
    void testDequeueOnEmptyThrows() {
        queue.enqueue(1);
        queue.dequeue();

        assertThrowsExactly(EmptyException.class, ()->queue.dequeue());
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
        assertFalse(queue.isFull());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());

        assertTrue(queue.isFull());
    }
}