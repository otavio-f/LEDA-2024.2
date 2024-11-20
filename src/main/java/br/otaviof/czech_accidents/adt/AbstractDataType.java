package br.otaviof.czech_accidents.adt;

import java.util.Iterator;

/**
 * Tipo abstrato de dados
 * As operações contidas neste tipo não devem modificar a coleção original
 * @param <T> um tipo genérico
 */
public interface AbstractDataType<T> {

    //TODO: implementar métodos em todas as classes herdeiras

    /**
     * Cria um iterador sobre essa coleção.
     * O iterador não deve modificar a lista original
     * @return Um iterador sobre os itens dessa lista
     * @throws EmptyException se a lista está vazia
     */
    Iterator<T> getIterator();

    /**
     * Constroi um array a partir dos itens dessa lista.
     * A coleção de dados original não será modificada.
     * @return Um array
     */
    T[] toArray();

}
