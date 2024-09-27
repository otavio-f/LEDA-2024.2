package Sorters;

import br.otaviof.czech_accidents.sorters.QuickSortMedianThree;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.genIntArray;
import static utils.TestUtils.isOrdered;

class QuickSortMedianThreeTest {

    @Test
    void testOrder() {
        Integer sample[] = { 1, 5, 3, 4, 2, 5 };
        int correctOrder[] = { 0, 4, 2, 3, 1, 5 };
        int altOrder[] = { 0, 4, 2, 3, 5, 1 }; // ordem alternativa
        QuickSortMedianThree<Integer> sorter = new QuickSortMedianThree<>(sample);

        int order[] = sorter.sort();
        assertTrue(Arrays.equals(order, correctOrder) || Arrays.equals(order, altOrder)); // Não preserva ordem, tanto
                                                                                          // faz
    }

    @Test
    void testSort() {
        Integer sample[] = genIntArray(1_000_000, -10_000_000, 10_000_000);
        QuickSortMedianThree<Integer> sorter = new QuickSortMedianThree<>(sample);

        sorter.sort();
        assertTrue(isOrdered(sample));
    }

}
