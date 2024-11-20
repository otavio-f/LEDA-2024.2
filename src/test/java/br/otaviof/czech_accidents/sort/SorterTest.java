package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// ordem estável: { 0, 4, 2, 3, 1, 5 }
// ordem não estável: { 0, 4, 2, 3, 5, 1 }

public class SorterTest {
    @Test
    void countingSortTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.countingSort(sample, null);

        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 1, 5 }, result.toArray());
    }


    @Test
    void heapSortTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.heapSort(sample, null);
        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 5, 1 }, result.toArray());
    }

    @Test
    void insertionSortTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.insertionSort(sample, null);
        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 1, 5 }, result.toArray());
    }

    @Test
    void mergeSortTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.mergeSort(sample, null);
        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 1, 5 }, result.toArray());
    }

    @Test
    void quickSortTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.quickSort(sample, null);
        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 1, 5 }, result.toArray());
    }

    @Test
    void quick3MedianTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.quick3MedianSort(sample, null);
        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 5, 1 }, result.toArray());
    }


    @Test
    void selectionTest() {
        CustomList<Integer> sample = new DynamicList<>();
        sample.append(1);
        sample.append(5);
        sample.append(3);
        sample.append(4);
        sample.append(2);
        sample.append(5);

        CustomQueue<Integer> result = Sorter.selectionSort(sample, null);
        assertArrayEquals(new Integer[] { 0, 4, 2, 3, 1, 5 }, result.toArray());
    }
}
