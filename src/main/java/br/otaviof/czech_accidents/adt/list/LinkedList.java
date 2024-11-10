package br.otaviof.czech_accidents.adt.list;

import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.node.LinkedNode;
import br.otaviof.czech_accidents.utils.GenericsUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author otavio-f
 * Implementação de lista encadeada usando nodos sentinelas
 * @param <T>
 */
public class LinkedList<T extends Comparable<? super T>> implements List<T> {

    private final LinkedNode<T> head;
    private final LinkedNode<T> tail;

    public LinkedList() {
        this.head = new LinkedNode<T>(null);
        this.tail = new LinkedNode<T>(null);
        this.head.setNext(this.tail);
    }

    /**
     * Calcula qual nodo está na posição
     * @param index A posição do nodo
     * @return O nodo
     * @throws EmptyException se a lista está vazia
     * @throws IndexOutOfBoundsException se a posição é inválida
     */
    private LinkedNode<T> getNodeAt(int index) {
        if(this.isEmpty())
            throw new EmptyException();
        if(index < 0)
            throw new IndexOutOfBoundsException();
        LinkedNode<T> result = this.head.getNext();
        while(index > 0) {
            if(result == this.tail)
                throw new IndexOutOfBoundsException();
            result = result.getNext();
            index--;
        }

        return result;
    }

    @Override
    public void append(T item) {
        final LinkedNode<T> node = new LinkedNode<T>(item);
        LinkedNode<T> last = this.head;
        while(last.getNext() != this.tail)
            last = last.getNext();

        node.setNext(this.tail);
        last.setNext(node);
    }

    @Override
    public void insertAt(T item, int index) {
        if(index < 0)
            throw new IndexOutOfBoundsException();

        LinkedNode<T> before = this.head;
        while(index > 0) {
            if(before == this.tail)
                throw new IndexOutOfBoundsException();
            before = before.getNext();
            index--;
        }
       LinkedNode<T> node = new LinkedNode<>(item);
        node.setNext(before.getNext());
        before.setNext(node);
    }

    @Override
    public T pop(int index) {
        if(this.isEmpty())
            throw new EmptyException();
        if(index < 0)
            throw new IndexOutOfBoundsException();

       LinkedNode<T> before = this.head;
        while(index > 0) {
            if(before == this.tail)
                throw new IndexOutOfBoundsException();
            before = before.getNext();
            index--;
        }

       LinkedNode<T> result = before.getNext();
        before.setNext(result.getNext());
        return result.getData();
    }

    @Override
    public void swap(int i, int j) {
        if(this.isEmpty())
            throw new EmptyException();
        if(i<0 || j<0)
            throw new IndexOutOfBoundsException();
        if(i==j) // nao faz nada se forem iguais
            return;
        if(j<i) { // menor indice sempre vai ser <i>
            int temp = i;
            i = j;
            j = temp;
        }
       LinkedNode<T> temp = this.head;

        // 1a forma
        /*
         * caminha ate <i->, armazena <i> e <i+>;
         * caminha ate <j->, armazena <j> e <j+>;

         * proximo de <i-> vira <j>
         * proximo de <j-> vira <i>

         * proximo de <j> vira <i+>
         * proximo de <i> vira <j+>
        */

        // 2a forma
        /*
         * Encontra <i-> e armazena
         * Encontra <j-> e armazena
         * proximo de <i-> vira <j>, se <i> era cabeca entao <j> vira cabeca
         * proximo de <j-> vira <i>, se <j> era cabeca entao <i> vira cabeca
         * proximo de <i> vira <j+>
         * proximo de <j> vira <i+>
         */
    }

    @Override
    public int getSize() {
        int count = 0;
       LinkedNode<T> node = this.head.getNext();
        while(node != this.tail) {
            node = node.getNext();
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return (this.head.getNext() == this.tail);
    }

    @Override
    public boolean contains(T item) {
       LinkedNode<T> node = this.head.getNext();
        while(node != this.tail) {
            if(Objects.equals(item, node.getData()))
                return true;
            node = node.getNext();
        }
        return false;
    }

    @Override
    public T getAt(int index) {
        return this.getNodeAt(index).getData();
    }

    @Override
    public int indexOf(T item) {
       LinkedNode<T> node = this.head.getNext();
        int counter = 0;

        while(node != this.tail) {
            if(Objects.equals(item, node.getData()))
                return counter;
            counter++;
            node = node.getNext();
        }
        return -1;
    }

    @Override
    public T minimum() {
        if(this.isEmpty())
            throw new EmptyException();

       LinkedNode<T> node = this.head.getNext();
        T result = node.getData();
        node = node.getNext(); // comeca pelo 2o elemento

        while(node != this.tail) {
            if(result.compareTo(node.getData()) > 0)
                result = node.getData();
            node = node.getNext();
        }

        return result;
    }

    @Override
    public T maximum() {
        if(this.isEmpty())
            throw new EmptyException();

       LinkedNode<T> node = this.head.getNext();
        T result = node.getData();
        node = node.getNext(); // comeca pelo 2o elemento

        while(node != this.tail) {
            if(result.compareTo(node.getData()) < 0)
                result = node.getData();
            node = node.getNext();
        }

        return result;
    }

    @Override
    public T[] toArray() {
        final int length = this.getSize();
        T[] result = GenericsUtils.createArrayOfSize(length);
       LinkedNode<T> node = this.head.getNext();

        for(int i=0; i<length; i++) {
            result[i] = node.getData();
            node = node.getNext();
        }

        return result;
    }

    @Override
    public List<T> copy() {
        LinkedList<T> result = new LinkedList<T>();
       LinkedNode<T> node = this.head.getNext();
       LinkedNode<T> temp = result.head;
        while(node != this.tail) {
            temp.setNext(new LinkedNode<T>(node.getData()));
            node = node.getNext();
            temp = temp.getNext();
        }
        temp.setNext(result.tail);
        return result;
    }

    @Override
    public Iterator<T> getIterator() {
        if(this.isEmpty())
            throw new EmptyException();

        return new Iterator<T>() {
           LinkedNode<T> node = head;
            @Override
            public boolean hasNext() {
                return (node != tail);
            }

            @Override
            public T next() {
                node = node.getNext();
                if(!this.hasNext())
                    throw new NoSuchElementException();

                return node.getData();
            }
        };
    }
}
