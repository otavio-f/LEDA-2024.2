package br.otaviof.czech_accidents.adt.stack;

import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.FullException;
import br.otaviof.czech_accidents.utils.GenericsUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de pilha sobre array
 * @param <T> Um tipo genérico
 * @author otavio-f
 */
public class ArrayStack<T> implements Stack<T> {

    private final T[] data;
    private int top;

    /**
     * Cria uma pilha com tamanho máximo
     * @param size A quantidade máxima de itens que essa pilha pode conter
     * @throws IllegalArgumentException se a quantidade for menor ou igual a zero
     */
    public ArrayStack(int size) {
        if(size <= 0)
            throw new IllegalArgumentException();

        this.data = GenericsUtils.createArrayOfSize(size);
        this.top = 0;
    }

    @Override
    public void push(T item) {
        if(this.isFull())
            throw new FullException();

        this.data[this.top] = item;
        this.top++;
    }

    @Override
    public T pop() {
        if(this.isEmpty())
            throw new EmptyException();

        T result = this.data[this.top -1];
        this.top--;
        return result;
    }

    @Override
    public T top() {
        if(this.isEmpty())
            throw new EmptyException();

        return this.data[this.top -1];
    }

    @Override
    public Stack<T> multipop(int k) {
        if(k<=0)
            throw new IllegalArgumentException();
        if(this.isEmpty())
            throw new EmptyException();

        if(k > this.top) {
            k = this.top;
        }

        ArrayStack<T> result = new ArrayStack<>(k);
        for(int i=0; i<k; i++)
            result.push(this.pop());

        return result;
    }

    @Override
    public boolean isEmpty() {
        return (this.top == 0);
    }

    @Override
    public boolean isFull() {
        return (this.top == this.data.length);
    }

    @Override
    public Iterator<T> getIterator() {
        if(this.isEmpty())
            throw new EmptyException();

        return new Iterator<T>() {
            int index = top;
            @Override
            public boolean hasNext() {
                return (this.index >= 0);
            }

            @Override
            public T next() {
                this.index--;
                if(!this.hasNext())
                    throw new NoSuchElementException();
                return data[this.index];
            }
        };
    }

    @Override
    public T[] toArray() {
        T[] result = GenericsUtils.createArrayOfSize(this.top);

        final int topIndex = top-1;

        for(int i=0; i<this.top; i++) {
            result[topIndex-i] = this.data[i];
        }
        return result;
    }
}
