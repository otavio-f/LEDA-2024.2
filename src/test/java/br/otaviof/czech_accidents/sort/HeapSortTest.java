package br.otaviof.czech_accidents.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class HeapSortTest {

    @Test
    void testOrder() {
        Integer sample[] = { 1, 5, 3, 4, 2, 5 };
        int correctOrder[] = { 0, 4, 2, 3, 1, 5 };
        int altOrder[] = { 0, 4, 2, 3, 5, 1 };
        HeapSort<Integer> sorter = new HeapSort<>(sample);

        int order[] = sorter.sort();

        assertTrue(Arrays.equals(order, correctOrder) || Arrays.equals(order,
                altOrder)); // Não preserva ordem, tanto faz // faz
    }

    @Test
    void testSort() {
        Integer sample[] = genIntArray(1_000_000, -10_000_000, 10_000_000);
        HeapSort<Integer> sorter = new HeapSort<>(sample);

        sorter.sort();
        assertTrue(isOrdered(sample));
    }

}
