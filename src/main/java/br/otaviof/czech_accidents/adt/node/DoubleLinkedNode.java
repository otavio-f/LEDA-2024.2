package br.otaviof.czech_accidents.adt.node;

/**
 * Um nodo que pode ser encadeado duplamente a outros
 * @param <T> Um tipo comparável
 * @author otavio-f
 */
public final class DoubleLinkedNode<T> extends LinkedNode<T> {
    private DoubleLinkedNode<T> prev;

    /**
     * Cria um nodo encadeado duplamente
     * @param data O dado a ser armazenado
     */
    public DoubleLinkedNode(T data) {
        super(data);
        this.prev = null;
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

    /**
     * Busca o primeiro nodo, começando por esse
     * @return o nodo sem nodo antes
     */
    public DoubleLinkedNode<T> getFirst() {
        DoubleLinkedNode<T> result = this;
        while(result.prev != null)
            result = result.prev;
        return result;
    }
}
