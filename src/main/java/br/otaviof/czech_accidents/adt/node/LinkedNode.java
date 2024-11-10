package br.otaviof.czech_accidents.adt.node;

/**
 * Um nodo que pode ser encadeado a outro
 * @param <T> Um tipo comparável
 * @author otavio-f
 */
public final class LinkedNode<T extends Comparable<? super T>> {
    private final T data;
    private LinkedNode<T> next;

    /**
     * Cria um nodo encadeado
     * @param data O dado a ser armazenado
     */
    public LinkedNode(T data) {
        this.data = data;
        this.next = null;
    }

    /**
     * Obtém o dado contido neste nodo
     * @return
     */
    public T getData() {
        return this.data;
    }

    /**
     * Obtém o nodo imediatamente depois deste
     * @return O nodo ou null se não houver nenhum
     */
    public LinkedNode<T> getNext() {
        return next;
    }

    /**
     * Atribui o próximo nodo
     * @param next
     */
    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }
}
