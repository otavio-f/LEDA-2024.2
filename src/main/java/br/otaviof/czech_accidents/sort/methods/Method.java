package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

/**
 * Interface comum a todos métodos de ordenação
 * @author otavio-f
 */
public interface Method<T extends Comparable<? super T>> {
    /**
     * Ordena uma lista
     * @param data o conjunto de dados a ser ordenado
     */
    void sort(CustomList<T> data);
}
