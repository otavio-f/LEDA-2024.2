package br.otaviof.czech_accidents.dataStructures;

import br.otaviof.czech_accidents.utils.GenericsUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author otavio-f
 * Implementa a interface lista sobre um array variável
 * @param <T> Um tipo genérico
 */
public class DynamicList<T extends Comparable<? super T>> implements List<T> {
    private T[] data;
    private int insertAt;

    public DynamicList() {
        this.data = GenericsUtils.createArrayOfSize(1);
        this.insertAt = 0;
    }

    @Override
    public void append(T item) {
        this.insertAt(item, this.insertAt);
    }

    @Override
    public void insertAt(T item, int index) {
        if(index > this.getSize() || index < 0)
            throw new IndexOutOfBoundsException("Index out of range!");

        if(this.isFull()) {
            T[] newData = GenericsUtils.createArrayOfSize(this.data.length*2);
            int i=this.insertAt;
            for(; i>index; i--)
                newData[i] = this.data[i-1];

            newData[i] = item;
            i--;

            for(;i>=0; i--)
                newData[i] = this.data[i];
            this.data = newData;
        } else {
            for(int i=this.insertAt; i>index; i--)
                this.data[i] = this.data[i-1];

            this.data[index] = item;
        }
        this.insertAt++;
    }

    @Override
    public int indexOf(T item) {
        if(item == null)
            throw new NullPointerException();
        for(int i=0; i<this.insertAt; i++) {
            if(item.equals(this.data[i]))
                return i;
        }
        return -1;
    }

    @Override
    public T pop(int index) {
        if(this.isEmpty())
            throw new EmptyException();

        if(index < 0 || index >= this.insertAt) {
            throw new IndexOutOfBoundsException();
        }

        T result = this.data[index];

        for(int i=index+1; i<this.insertAt; i++) {
            this.data[i-1] = this.data[i];
        }
        this.insertAt--;

        return result;
    }

    @Override
    public void swap(int i, int j) {
        if(this.isEmpty())
            throw new EmptyException();

        if(i < 0 || j < 0 || i >= this.insertAt || j >= this.insertAt) {
            throw new IndexOutOfBoundsException();
        }

        T temp = this.data[i];
        this.data[i] = this.data[j];
        this.data[j] = temp;
    }

    @Override
    public int getSize() {
        return this.insertAt;
    }

    @Override
    public boolean isEmpty() {
        return (this.insertAt == 0);
    }

    private boolean isFull() {
        return (this.insertAt == this.data.length);
    }

    @Override
    public boolean contains(T item) {
        for(int i=0; i<this.insertAt; i++)
            if(Objects.equals(this.data[i], item))
                return true;

        return false;
    }

    @Override
    public T getAt(int index) {
        if(this.isEmpty())
            throw new EmptyException();

        if(index < 0 || index >= this.insertAt)
            throw new IndexOutOfBoundsException();

        return this.data[index];
    }

    @Override
    public T minimum() {
        if(this.isEmpty())
            throw new EmptyException();

        T result = this.data[0];
        for(int i=1; i<this.insertAt; i++) {
            if(this.data[i].compareTo(result) < 0)
                result = this.data[i];
        }
        return result;
    }

    @Override
    public T maximum() {
        if(this.isEmpty())
            throw new EmptyException();
        T result = this.data[0];
        for(int i=1; i<this.insertAt; i++) {
            if(this.data[i].compareTo(result) > 0)
                result = this.data[i];
        }
        return result;
    }

    @Override
    public T[] toArray() {
        T[] result = GenericsUtils.createArrayOfSize(this.insertAt);

        System.arraycopy(this.data, 0, result, 0, this.insertAt);
//        for(int i=0; i<this.insertAt; i++) {
//            result[i] = this.data[i];
//        }

        return result;
    }

    @Override
    public List<T> copy() {
        DynamicList<T> result = new DynamicList<>();

        result.insertAt = this.insertAt;
        if (this.insertAt >= 0)
            System.arraycopy(this.data, 0, result.data, 0, this.insertAt);
//        for(int i=0; i<this.insertAt; i++)
//            result.data[i] = this.data[i];

        return result;
    }

    public Iterator<T> getIterator() {
        if(this.isEmpty())
            throw new EmptyException();

        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < insertAt;
            }

            @Override
            public T next() {
                if(!this.hasNext())
                    throw new NoSuchElementException();

                T result = data[this.index];
                this.index++;
                return result;
            }
        };
    }
}
