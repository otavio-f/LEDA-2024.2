package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.sort.data.BucketSet;
import br.otaviof.czech_accidents.sort.data.DataHolder;
import br.otaviof.czech_accidents.sort.methods.*;
import br.otaviof.czech_accidents.tracker.UpdateAction;

import java.util.Iterator;

/**
 * Classe estática para fácil acesso aos métodos de ordenação
 */
public final class Sorter {

    private Sorter() {}

    /**
     * Encapsula dados em dados atrelados ao índice
     * @param data A coleção de dados originais
     * @return Uma cópia dos dados originais com cada item atrelado ao índice atual
     * @param <T> Um tipo comparável
     */
    private static <T extends Comparable<? super T>> CustomList<DataHolder<T>> encaseData(CustomList<T> data) {
        CustomList<DataHolder<T>> result = new DynamicList<>();
        Iterator<T> iter = data.getIterator();
        int count = 0;

        while(iter.hasNext())
            result.append(new DataHolder<>(iter.next(), count++));

        return result;
    }

    /**
     * Gera um índice de ordenação
     * @param data Os dados atrelados aos índices
     * @return Uma coluna de ordenação que aponta o deslocamento necessário para ordenar os dados originais
     * @param <T> Um tipo comparável
     */
    private static <T extends Comparable<? super T>> CustomQueue<Integer> getOrder(CustomList<DataHolder<T>> data) {
        CustomQueue<Integer> result = new DynamicQueue<>(data.getSize());
        Iterator<DataHolder<T>> iter = data.getIterator();

        while(iter.hasNext())
            result.enqueue(iter.next().getIndex());

        return result;
    }

    /**
     * Ordena uma lista usando um método de ordenação
     * @param sorter O método de ordenação
     * @param list A coleção de dados originais
     * @return Uma coluna de ordenação
     * @param <T> Um tipo comparável
     */
    private static <T extends Comparable<? super T>> CustomQueue<Integer> sortBy(Method<DataHolder<T>> sorter, CustomList<T> list) {
        final CustomList<DataHolder<T>> data = encaseData(list);

        CustomQueue<Integer> result = new DynamicQueue<>(data.getSize());
        BucketSet<DataHolder<T>> buckets = new BucketSet<>(data);
        Iterator<CustomList<DataHolder<T>>> iter = buckets.getIterator();

        while(iter.hasNext()) {
            CustomList<DataHolder<T>> partialData = iter.next();
            sorter.sort(partialData);
            Iterator<Integer> partial = getOrder(partialData).getIterator();
            while(partial.hasNext()) {
                result.enqueue(partial.next());
            }
        }
        return result;
    }

    /**
     * Ordena pelo método counting-sort
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     */
    public static CustomQueue<Integer> countingSort(CustomList<Integer> data, UpdateAction ua) {
        Counting sorter = new Counting();
        sorter.getTracker().setAction(ua);
        return sorter.sort(data);
    }

    /**
     * Ordena pelo método heap-sort
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     * @param <T> Um tipo comparável
     */
    public static <T extends Comparable<? super T>> CustomQueue<Integer> heapSort(CustomList<T> data, UpdateAction ua) {
        Heap<DataHolder<T>> sorter = new Heap<>();
        sorter.getTracker().setAction(ua);
        return sortBy(sorter, data);
    }

    /**
     * Ordena pelo método insertion-sort
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     * @param <T> Um tipo comparável
     */
    public static <T extends Comparable<? super T>> CustomQueue<Integer> insertionSort(CustomList<T> data, UpdateAction ua) {
        Insertion<DataHolder<T>> sorter = new Insertion<>();
        sorter.getTracker().setAction(ua);
        return sortBy(sorter, data);
    }

    /**
     * Ordena pelo método merge-sort
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     * @param <T> Um tipo comparável
     */
    public static <T extends Comparable<? super T>> CustomQueue<Integer> mergeSort(CustomList<T> data, UpdateAction ua) {
        Merge<DataHolder<T>> sorter = new Merge<>();
        sorter.getTracker().setAction(ua);
        return sortBy(sorter, data);
    }

    /**
     * Ordena pelo método quick-sort
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     * @param <T> Um tipo comparável
     */
    public static <T extends Comparable<? super T>> CustomQueue<Integer> quickSort(CustomList<T> data, UpdateAction ua) {
        Quick<DataHolder<T>> sorter = new Quick<>();
        sorter.getTracker().setAction(ua);
        return sortBy(sorter, data);
    }

    /**
     * Ordena pelo método quick-sort (Mediana de 3)
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     * @param <T> Um tipo comparável
     */
    public static <T extends Comparable<? super T>> CustomQueue<Integer> quick3MedianSort(CustomList<T> data, UpdateAction ua) {
        Quick3Median<DataHolder<T>> sorter = new Quick3Median<>();
        sorter.getTracker().setAction(ua);
        return sortBy(sorter, data);
    }

    /**
     * Ordena pelo método selection-sort
     * @param data Uma coleção de dados
     * @param ua Uma ação a ser executada durante a ordenação
     * @return A coluna de reordenação
     * @param <T> Um tipo comparável
     */
    public static <T extends Comparable<? super T>> CustomQueue<Integer> selectionSort(CustomList<T> data, UpdateAction ua) {
        Selection<DataHolder<T>> sorter = new Selection<>();
        sorter.getTracker().setAction(ua);
        return sortBy(sorter, data);
    }

}
