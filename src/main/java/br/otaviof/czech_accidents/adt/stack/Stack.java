package br.otaviof.czech_accidents.adt.stack;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;

/**
 * Interface para classes de pilha, tipo Last Insert First Out
 * @param <T> Um tipo genérico comparável
 * @author otavio-f
 */
public interface Stack<T> extends AbstractDataType<T> {

    /**
     * Adiciona um elemento no topo da pilha
     * @param item a ser adicionado no topo da pilha
     */
    void push(T item);

    /**
     * Remove o elemento no topo da pilha
     * @return O elemento removido
     */
    T pop();

    /**
     * Verifica qual elemento está no topo da pilha
     * @return O elemento no topo
     */
    T top();

    /**
     * Desempilha múltiplos itens de uma vez, até no máximo k elementos
     * @param k a quantidade máxima de itens a desempilhar
     * @return Uma pilha com os itens desempilhados
     * @throws EmptyException se a pilha estiver vazia
     * @throws IllegalArgumentException se a quantidade de elementos desempilhados for menor ou igual a zero
     */
    Stack<T> multipop(int k);

    /**
     * Verifica se a pilha não contém itens
     * @return true se a pilha não contém nenhum item, senão false
     */
    boolean isEmpty();

    /**
     * Verifica se a pilha está cheia
     * @return true se a pilha está cheia, senão false
     */
    boolean isFull();
}
