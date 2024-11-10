package br.otaviof.czech_accidents.adt.stack;

/**
 * Interface para classes de pilha, tipo Last Insert Firtst Out
 * @param <T> Um tipo genérico comparável
 * @author otavio-f
 */
public interface Stack <T extends Comparable<? super T>> {

    /**
     * Adiciona um elemento no topo da pilha
     * @param item
     */
    void push(T item);

    /**
     * Remove um elemento da pilha
     * @return O elemento removido
     */
    T pop();

    /**
     * Verifica qual elemento está no topo da pilha
     * @return O elemento no topo
     */
    T top();

    // TODO: Entender como funciona isso
    Stack<T> multitop(int k);

    /**
     * Verifica se a pilha não contém itens
     * @return true se a pilha não contém nenhum item, senão false
     */
    boolean isEmpty();
}
