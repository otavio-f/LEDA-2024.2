package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.sorters.HeapSort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.genIntArray;
import static utils.TestUtils.isOrdered;

/**
 * Testes de unidade para método Heap Sort
 * @author otavio-f
 */
class HeapTest {
    private Method<Integer> method;

    @BeforeEach
    void setup() {
        method = new Heap<>();
    }

    /**
     * O resultado da ordenação deve refletir a posição dos itens da lista original quando ordenados.
     */
    @Test
    void testOrder() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> order = method.sort(sample);

        assertTrue(
                Arrays.equals(new Integer[] {0, 4, 2, 3, 1, 5}, order.toArray()) ||
                        Arrays.equals(new Integer[] {0, 4, 2, 3, 5, 1}, order.toArray())
        );
    }
}
