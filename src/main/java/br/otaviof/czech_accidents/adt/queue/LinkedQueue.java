package br.otaviof.czech_accidents.adt.queue;

import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.FullException;
import br.otaviof.czech_accidents.adt.node.LinkedNode;

/**
 * Implementação de Fila baseada em elementos encadeados
 * @param <T>
 * @author otavio-f
 */
public class LinkedQueue<T> implements Queue<T> {
    private static final int NO_LIMIT = -1;

    private final int size;
    private final LinkedNode<T> head = new LinkedNode<>(null);
    private final LinkedNode<T> tail = new LinkedNode<>(null);

    /**
     * Cria uma fila com tamanho máximo
     * @param size tamanho máximo dessa fila
     */
    public LinkedQueue(int size) {
        if(size <= 0)
            throw new IllegalArgumentException();
        this.size = size;
        this.head.setNext(this.tail);
    }

    /**
     * Cria uma fila
     */
    public LinkedQueue() {
        this.size = LinkedQueue.NO_LIMIT;
        this.head.setNext(this.tail);
    }

    @Override
    public void enqueue(T item) {
        if(this.isFull())
            throw new FullException();

        LinkedNode<T> last = this.head;
        while(last.getNext() != this.tail)
            last = last.getNext();
        last.setNext(new LinkedNode<>(item));
        last.getNext().setNext(this.tail);
    }

    @Override
    public T dequeue() {
        if(this.isEmpty())
            throw new EmptyException();

        final LinkedNode<T> target = this.head.getNext();
        this.head.setNext(target.getNext());

        return target.getData();
    }

    @Override
    public T peekTail() {
        if(this.isEmpty())
            throw new EmptyException();

        LinkedNode<T> last = this.head;
        while(last.getNext() != this.tail)
            last = last.getNext();
        return last.getData();
    }

    @Override
    public T peekHead() {
        if(this.isEmpty())
            throw new EmptyException();

        return this.head.getNext().getData();
    }

    @Override
    public boolean isEmpty() {
        return this.head.getNext() == this.tail;
    }

    @Override
    public boolean isFull() {
        if(this.size == LinkedQueue.NO_LIMIT) // lista com tamanho máximo nunca vai estar cheia
            return false;

        int count = 0;
        LinkedNode<T> node = this.head;

        while(node.getNext() != this.tail) {
            count++;
            if(count == this.size)
                return true;
            node = node.getNext();
        }

        return false;
    }
}
