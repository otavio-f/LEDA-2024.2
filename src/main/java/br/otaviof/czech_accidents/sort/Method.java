package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

/**
 * Interface comum a todos métodos de ordenação
 * @author otavio-f
 */
public interface Method {
    /**
     * Ordena uma lista
     * @param data o conjunto de dados a ser ordenado
     * @return O conjunto ordenado
     */
    CustomQueue<Integer> sort(CustomList<?> data);
    //aqui vai unchecked mesmo, o tipo do dado só deve importar mais adiante
}
