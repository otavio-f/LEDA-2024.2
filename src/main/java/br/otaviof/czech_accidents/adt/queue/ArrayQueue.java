package br.otaviof.czech_accidents.adt.queue;

import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.FullException;
import br.otaviof.czech_accidents.utils.GenericsUtils;

/**
 * Implementação da interface Queue sobre um array genérico de tamanho fixo
 * @author otavio-f
 * @param <T> um tipo comparável
 */
public class ArrayQueue<T> implements Queue<T> {

    private final T[] data;
    private int tail;
    private int head;
    private boolean isEmpty;

    /**
     * Inicia uma fila de tamanho máximo fixo
     * @param size O tamanho máximo da fila
     */
    public ArrayQueue(int size) {
        this.data = GenericsUtils.createArrayOfSize(size);
        this.head = 0;
        this.tail = 0;
        this.isEmpty = true;
    }

    @Override
    public void enqueue(T item) {
        if(this.isFull())
            throw new FullException();

        this.data[this.tail] = item;
        this.tail = (this.tail+1) % this.data.length;
        this.isEmpty = false;
    }

    @Override
    public T dequeue() {
        if(this.isEmpty())
            throw new EmptyException();

        T result = this.data[this.head];
        this.head = (this.head+1) % this.data.length;
        if(this.head == this.tail)
            this.isEmpty = true;

        return result;
    }

    @Override
    public T peekTail() {
        if(this.isEmpty)
            throw new EmptyException();

        return this.data[this.tail-1];
    }

    @Override
    public T peekHead() {
        if(this.isEmpty)
            throw new EmptyException();

        return this.data[this.head];
    }

    @Override
    public boolean isEmpty() {
        return this.isEmpty;
    }

    @Override
    public boolean isFull() {
        return (!this.isEmpty && this.head == this.tail);
    }
}
