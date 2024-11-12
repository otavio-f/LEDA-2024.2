package br.otaviof.czech_accidents.adt.stack;

import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.FullException;
import br.otaviof.czech_accidents.adt.node.LinkedNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de fila baseada em nodos encadeados
 * @param <T> tipo genérico
 * @author otavio-f
 */
public class LinkedStack<T> implements Stack<T>  {
    private static final int NO_LIMIT = -1;

    private final LinkedNode<T> head = new LinkedNode<>(null);
    private final LinkedNode<T> tail = new LinkedNode<>(null);
    private final int size;

    /**
     * Cria uma pilha com tamanho máximo
     * @param size tamanho máximo dessa fila
     */
    public LinkedStack(int size) {
        this.size = size;
        this.head.setNext(this.tail);
    }

    /**
     * Cria uma pilha
     */
    public LinkedStack() {
        this.size = NO_LIMIT;
        this.head.setNext(this.tail);
    }

    @Override
    public void push(T item) {
        if(this.isFull())
            throw new FullException();

        LinkedNode<T> first = new LinkedNode<>(item);
        first.setNext(this.head.getNext());
        this.head.setNext(first);
    }

    @Override
    public T pop() {
        if(this.isEmpty())
            throw new EmptyException();

        LinkedNode<T> target = this.head.getNext();
        this.head.setNext(target.getNext());
        return target.getData();
    }

    @Override
    public T top() {
        if(this.isEmpty())
            throw new EmptyException();
        return this.head.getNext().getData();
    }

    @Override
    public Stack<T> multipop(int k) {
        if(this.isEmpty())
            throw new EmptyException();

        LinkedStack<T> result = new LinkedStack<>(k);
        for(int i=0; i<k; i++) {
            if(this.isEmpty())
                break;
            result.push(this.pop());
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return (this.head.getNext() == this.tail);
    }

    @Override
    public boolean isFull() {
        if(this.size == LinkedStack.NO_LIMIT) // lista com tamanho máximo nunca vai estar cheia
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

    @Override
    public Iterator<T> getIterator() {
        if(this.isEmpty())
            throw new EmptyException();

        return new Iterator<T>() {
            LinkedNode<T> node = head;
            @Override
            public boolean hasNext() {
                return this.node != tail;
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

    @Override
    public T[] toArray() {
        // TODO
        return null;
    }
}
