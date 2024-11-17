package br.otaviof.czech_accidents.adt.queue;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.FullException;

/**
 * Interface de dados abstratos do tipo fila, primeiro a entrar, primeiro a sair
 * @author otavio-f
 * @param <T> Um tipo comparável
 */
public interface CustomQueue<T> extends AbstractDataType<T> {
    /**
     * Adiciona um item no final da fila
     * @param item O item a ser adicionado ao final
     * @throws FullException se a fila está cheia
     */
    void enqueue(T item);

    /**
     * Remove o primeiro item da fila
     * @return O item removido
     * @throws EmptyException se a fila está vazia
     */
    T dequeue();

    /**
     * Olha quem é o último da fila
     * @return O último item da fila
     * @throws EmptyException se a fila está vazia
     */
    T peekTail();

    /**
     * Olha quem é o primeiro item da fila
     * @return O primeiro item da fila
     * @throws EmptyException se a fila está vazia
     */
    T peekHead();

    /**
     * Verifica se a fila está vazia
     * @return true se a fila está vazia, senão false
     */
    boolean isEmpty();

    /**
     * Verifica se a fila está cheia
     * @return true se a fila está cheia, senão false
     */
    boolean isFull();
}
