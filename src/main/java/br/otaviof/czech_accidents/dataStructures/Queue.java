package br.otaviof.czech_accidents.dataStructures;

// FIFO
public interface Queue<T extends Comparable<? super T>> {
    public void enqueue(T item);

    public T dequeue();

    public T peekTail();

    public T peekHead();

    public boolean isEmpty();

    public boolean isFull();
}
