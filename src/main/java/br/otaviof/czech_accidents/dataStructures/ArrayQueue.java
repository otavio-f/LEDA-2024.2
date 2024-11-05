package br.otaviof.czech_accidents.dataStructures;

import br.otaviof.czech_accidents.utils.GenericsUtils;

public class ArrayQueue<T extends Comparable<? super T>> implements Queue<T> {

    private final T[] data;
    private int tail;
    private int head;

    //TODO: Implementar queue circular que remove incrementando cabeca
    public ArrayQueue(int size) {
        this.data = GenericsUtils.createArrayOfSize(size);
        this.tail = 1;
        this.head = 0;
    }

    @Override
    public void enqueue(T item) {
        if(this.isFull())
            throw new FullException();

        this.data[this.tail-1] = item;
        this.tail++;
    }

    @Override
    public T dequeue() {
        if(this.isEmpty())
            throw new EmptyException();

        T result = this.data[this.head];
        for(int i=this.head+1; i<this.tail-1; i++) {
            this.data[i-1] = this.data[i];
        }
        this.tail--;

        return result;
    }

    @Override
    public T peekTail() {
        return this.data[this.tail-1];
    }

    @Override
    public T peekHead() {
        return this.data[this.head];
    }

    @Override
    public boolean isEmpty() {
        return (this.tail-1 == this.head);
    }

    @Override
    public boolean isFull() {
        return (this.tail > this.data.length);
    }
}
