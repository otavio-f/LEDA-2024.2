package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Testes de unidade para método Counting Sort
 * @author otavio-f
 */
class CountingTest {
    private Counting method;

    @BeforeEach
    void setup() {
        method = new Counting();
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

        assertArrayEquals(new Integer[] {0, 4, 2, 3, 1, 5}, order.toArray());
    }
}
