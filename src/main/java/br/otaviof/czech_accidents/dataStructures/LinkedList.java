package br.otaviof.czech_accidents.dataStructures;

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

    /**
     * Nodo de uma lista encadeada
     * @param <T>
     */
    private static class Node<T> {
        // TODO: remove getters e setters já que essa classe não é visível
        private final T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private final Node<T> head;
    private final Node<T> tail;

    public LinkedList() {
        this.head = new Node<T>(null);
        this.tail = new Node<T>(null);
        this.head.next = this.tail;
    }

    /**
     * Calcula qual nodo está na posição
     * @param index A posição do nodo
     * @return O nodo
     * @throws EmptyException se a lista está vazia
     * @throws IndexOutOfBoundsException se a posição é inválida
     */
    private Node<T> getNodeAt(int index) {
        if(this.isEmpty())
            throw new EmptyException();
        Node<T> result = this.head.next;
        while(index > 0) {
            if(result == this.tail)
                throw new IndexOutOfBoundsException();
            result = result.next;
            index--;
        }

        return result;
    }

    @Override
    public void append(T item) {
        final Node<T> node = new Node<T>(item);
        Node<T> last = this.head;
        while(last.next != this.tail)
            last = last.next;

        node.next = this.tail;
        last.next = node;
    }

    @Override
    public void insertAt(T item, int index) {
        if(index < 0)
            throw new IndexOutOfBoundsException();

        Node<T> before = this.head;
        while(index > 0) {
            if(before == this.tail)
                throw new IndexOutOfBoundsException();
            before = before.next;
            index--;
        }
        Node<T> node = new Node<>(item);
        node.next = before.next;
        before.next = node;
    }

    @Override
    public T pop(int index) {
        if(this.isEmpty())
            throw new EmptyException();
        if(index < 0)
            throw new IndexOutOfBoundsException();

        Node<T> before = this.head;
        while(index > 0) {
            if(before == this.tail)
                throw new IndexOutOfBoundsException();
            before = before.next;
            index--;
        }

        Node<T> result = before.next;
        before.next = result.next;
        return result.data;
    }

    @Override
    public void swap(int i, int j) {
        if(this.isEmpty())
            throw new EmptyException();
        if(i==j) // nao faz nada se forem iguais
            return;
        if(j<i) { // menor indice sempre vai ser <i>
            int temp = i;
            i = j;
            j = temp;
        }
        Node<T> temp = this.head;

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
        Node<T> node = this.head.next;
        while(node != this.tail) {
            node = node.next;
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return (this.head.next == this.tail);
    }

    @Override
    public boolean contains(T item) {
        Node<T> node = this.head.next;
        while(node != this.tail) {
            if(Objects.equals(item, node.data))
                return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public T getAt(int index) {
        return this.getNodeAt(index).data;
    }

    @Override
    public int indexOf(T item) {
        Node<T> node = this.head.next;
        int counter = 0;

        while(node != this.tail) {
            if(Objects.equals(item, node.data))
                return counter;
            counter++;
            node = node.next;
        }
        return -1;
    }

    @Override
    public T minimum() {
        if(this.isEmpty())
            throw new EmptyException();

        Node<T> node = this.head.next;
        T result = node.data;
        node = node.next; // comeca pelo 2o elemento

        while(node != this.tail) {
            if(result.compareTo(node.data) > 0)
                result = node.data;
            node = node.next;
        }

        return result;
    }

    @Override
    public T maximum() {
        if(this.isEmpty())
            throw new EmptyException();

        Node<T> node = this.head.next;
        T result = node.data;
        node = node.next; // comeca pelo 2o elemento

        while(node != this.tail) {
            if(result.compareTo(node.data) < 0)
                result = node.data;
            node = node.next;
        }

        return result;
    }

    @Override
    public T[] toArray() {
        final int length = this.getSize();
        T[] result = GenericsUtils.createArrayOfSize(length);
        Node<T> node = this.head.next;

        for(int i=0; i<length; i++) {
            result[i] = node.data;
            node = node.next;
        }

        return result;
    }

    @Override
    public List<T> copy() {

        return null;
    }

    @Override
    public Iterator<T> getIterator() {
        if(this.isEmpty())
            throw new EmptyException();

        return new Iterator<T>() {
            Node<T> node = head;
            @Override
            public boolean hasNext() {
                return (node != null);
            }

            @Override
            public T next() {
                if(!this.hasNext())
                    throw new NoSuchElementException();

                final T item = node.data;
                node = node.next;
                return item;
            }
        };
    }
}
