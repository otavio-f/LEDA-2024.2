package br.otaviof.czech_accidents.dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DynamicListTest {

    List<Integer> list;
    Random rng;

    @BeforeEach
    void setup() {
        list = null;
        rng = new Random();
    }

    @Test
    void testInsert() {
        list.insert(3);
        list.insert(6);

        assertEquals(2, list.size());
        assertArrayEquals(new Integer[] {3, 6}, list.toArray());
    }

    @Test
    void testInsertAtStart() {
        list.insert(3);
        list.insert(6);

        list.insert(1, 0);

        assertEquals(3, list.size());
        assertArrayEquals(new Integer[] {0, 3, 6}, list.toArray());
    }

    @Test
    void testPopItem() {
        list.insert(3);
        list.insert(6);

        Integer item = list.remove(3);

        assertEquals(1, list.size());
        assertEquals(3, item);
    }

    @Test
    void testPopIndex() {
        list.insert(3);
        list.insert(6);

        Integer item = list.pop(0);
        assertEquals(1, list.size());
        assertEquals(3, item);
    }

    @Test
    void swap() {
        list.insert(3);
        list.insert(6);

        list.swap(0, 1);

        assertArrayEquals(new Integer[] {6,3}, list.toArray());
    }

    @Test
    void testPredecessor() {
        list.insert(3);
        list.insert(6);

        assertEquals(3, list.predecessor(6));
        assertNull(list.predecessor(3));
    }

    @Test
    void testSucessor() {
        list.insert(3);
        list.insert(6);

        assertEquals(6, list.predecessor(3));
        assertNull(list.sucessor(6));
    }

    @Test
    void testSize() {
        list.insert(3);
        list.insert(6);
        list.insert(9);
        list.insert(2);
        list.insert(4);
        list.insert(8);
        list.remove(3);
        list.remove(8);

        assertEquals(6, list.size());
    }

    @Test
    void testIsEmpty() {
        list.insert(3);
        list.insert(6);
        list.insert(2);
        list.insert(4);

        assertFalse(list.isEmpty());

        list.pop(0);
        list.pop(0);
        list.pop(0);
        list.pop(0);

        assertTrue(list.isEmpty());
    }

    @Test
    void testSearch() {
        list.insert(3);
        list.insert(6);
        list.insert(2);
        list.insert(4);

        assertEquals(3, list.search(3));

        list.remove(3);
        list.remove(2);

        assertNull(list.search(3));
    }

    @Test
    void testGetAt() {
        list.insert(3);
        list.insert(6);
        list.insert(2);
        list.insert(4);

        assertEquals(3, list.getAt(0));

        list.remove(3);
        list.remove(2);

        assertEquals(6, list.getAt(0));
    }

    @Test
    void testMinimum() {
        list.insert(3);
        list.insert(6);
        list.insert(2);
        list.insert(4);

        assertEquals(2, list.minimum());
    }

    @Test
    void testMaximum() {
        list.insert(3);
        list.insert(16);
        list.insert(2);
        list.insert(4);

        assertEquals(16, list.maximum());
    }

    @Test
    void testToArray() {
        list.insert(30);
        list.insert(96);
        list.insert(1);

        assertArrayEquals(new Integer[] {30, 96, 1}, list.toArray());
    }
}