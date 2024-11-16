package br.otaviof.czech_accidents.adt.queue;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.FullException;
import br.otaviof.czech_accidents.utils.GenericsUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

    /**
     * Cria uma instância baseada nos dados de outra coleção
     * @param collection Uma coleção abstrata de dados
     */
    public ArrayQueue(AbstractDataType<T> collection) {
        int count = 0;
        Iterator<T> iter = collection.getIterator();
        while(iter.hasNext()) {
            count++;
            iter.next();
        }

        this.data = GenericsUtils.createArrayOfSize(count);
        iter = collection.getIterator();
        for(int i=0; i<count; i++) {
            this.data[i] = iter.next();
        }
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

    @Override
    public Iterator<T> getIterator() {
        if(this.isEmpty())
            throw new EmptyException();

        return new Iterator<T>() {
            int index = head;
            @Override
            public boolean hasNext() {
                return (this.index != tail);
            }

            @Override
            public T next() {
                if(!this.hasNext())
                    throw new NoSuchElementException();
                T result = data[this.index];
                this.index++;
                if(this.index==data.length)
                    this.index = 0;
                return result;
            }
        };
    }

    @Override
    public T[] toArray() {
        if(this.isEmpty)
            return GenericsUtils.createArrayOfSize(0);

        final int length = (this.head < this.tail)? (tail-head) : (data.length + tail - head);
        T[] result = GenericsUtils.createArrayOfSize(length);

        for(int i=0; i<length; i++)
            result[i] = this.data[(this.head+i)%this.data.length];

        return result;
    }
}
