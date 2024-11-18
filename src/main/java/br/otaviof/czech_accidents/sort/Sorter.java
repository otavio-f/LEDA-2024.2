package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

import java.util.Iterator;

public final class Sorter<T extends Comparable<? super T>> {

    private Sorter() {}

    private static <T extends Comparable<? super T>> CustomQueue<Integer> sortBy(Method sorter, CustomList<T> data) {
        CustomQueue<Integer> result = new DynamicQueue<>(data.getSize());
        BucketSet<T> buckets = new BucketSet<>(data);
        Iterator<CustomList<T>> iter = buckets.getIterator();
        while(iter.hasNext()) {
            Iterator<Integer> partial = sorter.sort(iter.next()).getIterator();
            while(partial.hasNext())
                result.enqueue(partial.next());
        }
        return result;
    }

    public static CustomQueue<Integer> countingSort(CustomList<Integer> data) {
        CustomQueue<Integer> result = new DynamicQueue<>(data.getSize());
        Counting sorter = new Counting();

        BucketSet<Integer> buckets = new BucketSet<>(data);
        Iterator<CustomList<Integer>> iter = buckets.getIterator();
        while(iter.hasNext()) {
            Iterator<Integer> partial = sorter.sort(iter.next()).getIterator();
            while(partial.hasNext())
                result.enqueue(partial.next());
        }
        return result;
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> heapSort(CustomList<T> data) {
        return sortBy(new Heap(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> insertionSort(CustomList<T> data) {
        return sortBy(new Insertion(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> mergeSort(CustomList<T> data) {
        return sortBy(new Merge(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> quickSort(CustomList<T> data) {
        return sortBy(new Quick(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> quick3MedianSort(CustomList<T> data) {
        return sortBy(new Quick3Median(), data);
    }
}
