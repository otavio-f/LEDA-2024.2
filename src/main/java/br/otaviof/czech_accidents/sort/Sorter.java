package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.sort.data.BucketSet;
import br.otaviof.czech_accidents.sort.data.DataHolder;
import br.otaviof.czech_accidents.sort.methods.*;

import java.util.Iterator;

public final class Sorter {

    private Sorter() {}

    private static <T extends Comparable<? super T>> CustomList<DataHolder<T>> encaseData(CustomList<T> data) {
        CustomList<DataHolder<T>> result = new DynamicList<>();
        Iterator<T> iter = data.getIterator();
        int count = 0;

        while(iter.hasNext())
            result.append(new DataHolder<>(iter.next(), count++));

        return result;
    }

    private static <T extends Comparable<? super T>> CustomQueue<Integer> getOrder(CustomList<DataHolder<T>> data) {
        CustomQueue<Integer> result = new DynamicQueue<>(data.getSize());
        Iterator<DataHolder<T>> iter = data.getIterator();

        while(iter.hasNext())
            result.enqueue(iter.next().getIndex());

        return result;
    }

    private static <T extends Comparable<? super T>> CustomQueue<Integer> sortBy(Method<DataHolder<T>> sorter, CustomList<T> list) {
        final CustomList<DataHolder<T>> data = encaseData(list);

        CustomQueue<Integer> result = new DynamicQueue<>(data.getSize());
        BucketSet<DataHolder<T>> buckets = new BucketSet<>(data);
        Iterator<CustomList<DataHolder<T>>> iter = buckets.getIterator();

        while(iter.hasNext()) {
            sorter.sort(iter.next());
            Iterator<Integer> partial = getOrder(data).getIterator();
            while(partial.hasNext())
                result.enqueue(partial.next());
        }
        return result;
    }

    public static CustomQueue<Integer> countingSort(CustomList<Integer> data) {
        Counting sorter = new Counting();
        return sorter.sort(data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> heapSort(CustomList<T> data) {
        return sortBy(new Heap<>(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> insertionSort(CustomList<T> data) {
        return sortBy(new Insertion<>(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> mergeSort(CustomList<T> data) {
        return sortBy(new Merge<>(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> quickSort(CustomList<T> data) {
        return sortBy(new Quick<>(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> quick3MedianSort(CustomList<T> data) {
        return sortBy(new Quick3Median<>(), data);
    }

    public static <T extends Comparable<? super T>> CustomQueue<Integer> selectionSort(CustomList<T> data) {
        return sortBy(new Selection<>(), data);
    }
}
