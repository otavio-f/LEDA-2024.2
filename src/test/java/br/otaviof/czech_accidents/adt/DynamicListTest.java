package br.otaviof.czech_accidents.adt;

import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.list.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DynamicListTest {

    List<Integer> list;
    Random rng = new Random();

    @BeforeEach
    void setup() {
        list = new DynamicList<Integer>();
    }

    @Test
    void testAppend() {
        list.append(rng.nextInt());
        list.append(rng.nextInt());

        assertEquals(2, list.getSize());
    }

    @Test
    void testInsertAt() {
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());

        list.insertAt(1, 0);

        assertEquals(5, list.getSize());
        assertEquals(1, list.getAt(0));
    }

    @Test
    void testInsertAtEnd() {
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());

        int sz = list.getSize();
        Integer k = rng.nextInt();
        list.insertAt(k, sz);

        assertEquals(5, list.getSize());
        assertEquals(k, list.getAt(sz));
    }

    @Test
    void testInsertAtInvalidThrows() {
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.insertAt(rng.nextInt(), 999);
        });

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.insertAt(rng.nextInt(), -999);
        });
    }

    @Test
    void testPop() {
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        Integer k = rng.nextInt();
        list.insertAt(k, 0);

        Integer item = list.pop(0);
        assertEquals(k, item);

        assertEquals(2, list.getSize());
    }

    @Test
    void testPopOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
           list.pop(0);
        });
    }

    @Test
    void testPopInvalidIndexThrows() {
        list.append(rng.nextInt());
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.pop(-1);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.pop(9999);
        });
    }

    @Test
    void swap() {
        list.append(3);
        list.append(6);

        list.swap(0, 1);

        assertArrayEquals(new Integer[] {6,3}, list.toArray());
    }

    @Test
    void swapOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.swap(0, 1);
        });
    }

    @Test
    void swapInvalidThrows() {
        list.append(3);
        list.append(6);

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.swap(-1, 333);
        });

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.swap(999, -1);
        });
    }

    @Test
    void testGetSize() {
        assertEquals(0, list.getSize());

        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        assertEquals(6, list.getSize());

        list.pop(0);
        list.pop(0);
        assertEquals(4, list.getSize());
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.append(rng.nextInt());

        assertFalse(list.isEmpty());

        list.pop(0);

        assertTrue(list.isEmpty());
    }

    @Test
    void testContains() {
        list.append(3);
        list.append(6);
        list.append(2);
        list.append(4);

        assertEquals(0, list.indexOf(3));

        list.pop(0);

        assertFalse(list.contains(3));
    }

    @Test
    void testGetAt() {
        list.append(3);
        list.append(6);
        list.append(2);
        list.append(4);

        assertEquals(3, list.getAt(0));
        assertEquals(6, list.getAt(1));
        assertEquals(2, list.getAt(2));
        assertEquals(4, list.getAt(3));
    }

    @Test
    void testGetAtInvalidThrows() {
        list.append(rng.nextInt());
        list.append(rng.nextInt());

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.getAt(333);
        });

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.getAt(-1);
        });
    }

    @Test
    void testGetAtEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.getAt(0);
        });
    }

    @Test
    void testIndexOf() {
        list.append(rng.nextInt(10));
        list.append(rng.nextInt(10));
        list.append(rng.nextInt(10));
        list.append(rng.nextInt(10));
        list.append(100);

        assertEquals(4, list.indexOf(100));
    }

    @Test
    void testIndexOfNonExistant() {
        list.append(rng.nextInt(10));
        list.append(rng.nextInt(10));
        list.append(rng.nextInt(10));
        list.append(rng.nextInt(10));
        list.append(100);
        list.pop(4);

        assertEquals(-1, list.indexOf(100));
    }

    @Test
    void testMinimum() {
        list.append(3);
        list.append(6);
        list.append(2);
        list.append(4);

        assertEquals(2, list.minimum());
    }

    @Test
    void testMinimumOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.minimum();
        });
    }

    @Test
    void testMaximum() {
        list.append(3);
        list.append(16);
        list.append(2);
        list.append(4);

        assertEquals(16, list.maximum());
    }

    @Test
    void testMaximumOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.maximum();
        });
    }

    @Test
    void testToArray() {
        assertArrayEquals(new Integer[] {}, list.toArray());

        list.append(30);
        list.append(96);
        list.append(1);

        assertArrayEquals(new Integer[] {30, 96, 1}, list.toArray());
    }

    @Test
    void testCopy() {
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        list.append(rng.nextInt());
        List<Integer> other = list.copy();

        assertEquals(list.getSize(), other.getSize());
        assertEquals(list.getAt(0), other.getAt(0));
        assertEquals(list.getAt(1), other.getAt(1));
        assertEquals(list.getAt(2), other.getAt(2));
    }

    @Test
    void testIterator() {
        list.append(1);
        list.append(2);
        list.append(3);

        Iterator<Integer> it = list.getIterator();

        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());

        assertThrowsExactly(NoSuchElementException.class, () -> {
            it.next();
        });
    }

    @Test
    void testIteratorOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.getIterator();
        });
    }
}