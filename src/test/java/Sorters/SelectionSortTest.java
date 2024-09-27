package Sorters;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import br.otaviof.czech_accidents.sorters.SelectionSort;

class SelectionSortTest {

    @Test
    void testOrder() {
        Integer sample[] = { 1, 5, 3, 4, 2, 5 };
        int correctOrder[] = { 0, 4, 2, 3, 1, 5 };
        int altOrder[] = { 0, 4, 2, 3, 5, 1 };
        SelectionSort<Integer> sorter = new SelectionSort<>(sample);

        int order[] = sorter.sort();
        assertArrayEquals(order, correctOrder);
        assertTrue(Arrays.equals(order, correctOrder) || Arrays.equals(order, altOrder)); // Não preserva ordem, tanto
                                                                                          // faz
    }

    @Test
    void testSort() {
        Integer sample[] = genIntArray(1_000, -10_000_000, 10_000_000);
        // 241s pra ordenar 100_000 elementos
        SelectionSort<Integer> sorter = new SelectionSort<>(sample);

        sorter.sort();
        assertTrue(isOrdered(sample));
    }

}
