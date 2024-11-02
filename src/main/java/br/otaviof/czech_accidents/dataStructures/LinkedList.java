package br.otaviof.czech_accidents.dataStructures;

import java.util.Iterator;

/**
 * @author otavio-f
 * Implementação de lista encadeada
 * @param <T>
 */
public class LinkedList<T extends Comparable<? super T>> implements List<T> {

    /**
     * Nó de uma lista encadeada
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

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    private Node<T> getTail() {
        if(this.isEmpty())
            return null;
        Node<T> node = this.head;
        while(node.getNext() != null)
            node = node.getNext();
        return node;
    }

    @Override
    public void append(T item) {
        final Node<T> node = new Node<T>(item);
        final Node<T> last = this.getTail();
        if(last == null)
            this.head = node;
        else
            last.setNext(node);
    }

    @Override
    public void insertAt(T item, int index) {
    }

    @Override
    public T pop(int index) {
        return null;
    }

    @Override
    public void swap(int i, int j) {
        if(this.isEmpty())
            throw new EmptyException();
        if(i>=this.getSize() || j>=this.getSize())
            throw new IndexOutOfBoundsException();

        // proximo de <i-1> vira <j>, se <i> era cabeca entao <j> vira cabeca
        // proximo de <j-1> vira <i>, se <j> era cabeca entao <i> vira cabeca
        // proximo de <i> vira <j+1>
        // proximo de <j> vira <i+1>
    }

    @Override
    public int getSize() {
        int count = 0;
        Node<T> node = this.head;
        while(node != null) {
            node = node.getNext();
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return (this.head == null);
    }

    @Override
    public boolean contains(T item) {
        return false;
    }

    private Node<T> getNodeAt(int index) {
        Node<T> result = this.head;
        while(index > 0) {
            if(result.getNext() == null)
                throw new IndexOutOfBoundsException();
            result = result.getNext();
            index--;
        }

        return result;
    }

    @Override
    public T getAt(int index) {
        return null;
    }

    @Override
    public int indexOf(T item) {
        return 0;
    }

    @Override
    public T minimum() {
        return null;
    }

    @Override
    public T maximum() {
        return null;
    }

    @Override
    public T[] toArray() {
        return null;
    }

    @Override
    public List<T> copy() {
        return null;
    }

    @Override
    public Iterator<T> getIterator() {
        return null;
    }
}
