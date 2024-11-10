package br.otaviof.czech_accidents.adt.node;

/**
 * Um nodo que pode ser encadeado duplamente a outros
 * @param <T> Um tipo comparável
 * @author otavio-f
 */
final class DoubleLinkedNode<T extends Comparable<? super T>> {
    private final T data;
    private DoubleLinkedNode<T> next;
    private DoubleLinkedNode<T> prev;

    /**
     * Cria um nodo encadeado duplamente
     * @param data O dado a ser armazenado
     */
    public DoubleLinkedNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
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
    public DoubleLinkedNode<T> getNext() {
        return next;
    }

    /**
     * Atribui o próximo nodo
     * @param next
     */
    public void setNext(DoubleLinkedNode<T> next) {
        this.next = next;
    }

    /**
     * Obtém o nodo imediatamente antes deste
     * @return O nodo ou null se não houver nenhum
     */
    public DoubleLinkedNode<T> getPrev() {
        return this.prev;
    }

    /**
     * Atribui o nodo anterior
     * @param prev
     */
    public void setPrev(DoubleLinkedNode<T> prev) {
        this.prev = prev;
    }
}
