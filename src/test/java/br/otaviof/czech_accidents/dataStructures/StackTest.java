package br.otaviof.czech_accidents.dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    private Stack<Integer> stack;

    @BeforeEach
    void setup() {
        stack = null;
    }

    @Test
    void push() {
        stack.push(2);
        stack.push(1);
        assertEquals(1, stack.top());
    }

    @Test
    void pop() {
        stack.push(2);
        stack.push(1);
        assertEquals(1, stack.pop());
        assertEquals(2, stack.top());
    }

    @Test
    void top() {
        stack.push(2);
        stack.push(1);
        assertEquals(1, stack.top());
    }

    @Test
    void multitop() {
        // TODO
        fail();
    }

    @Test
    void isEmpty() {
        assertTrue(stack.isEmpty());

        stack.push(2);
        stack.push(1);
        assertFalse(stack.isEmpty());

        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
    }
}