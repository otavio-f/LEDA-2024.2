package br.otaviof.czech_accidents.adt.list;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.node.LinkedNode;
import br.otaviof.czech_accidents.utils.GenericsUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @param <T>
 * @author otavio-f
 * Implementação de lista encadeada usando nodos sentinelas
 */
public class LinkedList<T extends Comparable<? super T>> implements List<T> {

    private final LinkedNode<T> head = new LinkedNode<>(null);
    private final LinkedNode<T> tail = new LinkedNode<>(null);

    public LinkedList() {
        this.head.setNext(this.tail);
    }

    /**
     * Cria uma instância baseada nos dados de outra coleção
     * @param collection Uma coleção abstrata de dados
     */
    public LinkedList(AbstractDataType<T> collection) {
        final Iterator<T> iter = collection.getIterator();
        LinkedNode<T> last = this.head;
        while(iter.hasNext()) {
            final LinkedNode<T> newNode = new LinkedNode<>(iter.next());
            last.setNext(newNode);
            last = newNode;
        }
        last.setNext(this.tail);
    }

    @Override
    public void append(T item) {
        final LinkedNode<T> node = new LinkedNode<>(item);
        LinkedNode<T> last = this.head;
        while (last.getNext() != this.tail)
            last = last.getNext();

        node.setNext(this.tail);
        last.setNext(node);
    }

    @Override
    public void insertAt(T item, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException();

        LinkedNode<T> before = this.head;
        while(index > 0) {
            if(before.getNext() == this.tail)
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
        if (this.isEmpty())
            throw new EmptyException();
        if (index < 0)
            throw new IndexOutOfBoundsException();

        LinkedNode<T> before = this.head;
        while(index > 0) {
            if(before.getNext() == this.tail)
                throw new IndexOutOfBoundsException();
            before = before.getNext();
            index--;
        }

        if(before.getNext() == this.tail) // index == size
            throw new IndexOutOfBoundsException();
        LinkedNode<T> result = before.getNext();
        before.setNext(result.getNext());
        return result.getData();
    }

    @Override
    public void swap(int i, int j) {
        if (this.isEmpty())
            throw new EmptyException();
        if (i < 0 || j < 0)
            throw new IndexOutOfBoundsException();
        if (i == j) // nao faz nada se forem iguais
            return;
        if (j < i) { // menor indice sempre vai ser <i>
            int temp = i;
            i = j;
            j = temp;
        }

        // recupera nodo anterior a <i>
        int count = 0;
        LinkedNode<T> temp = this.head;
        while (count < i) {
            if (temp.getNext() == this.tail)
                throw new IndexOutOfBoundsException();
            temp = temp.getNext();
            count++;
        }
        final LinkedNode<T> prev_i = temp;

        // recupera nodo anterior a <j>
        while (count < j) {
            if (temp.getNext() == this.tail)
                throw new IndexOutOfBoundsException();
            temp = temp.getNext();
            count++;
        }
        final LinkedNode<T> prev_j = temp;

        // recupera nodos <i> e <j>
        final LinkedNode<T> node_i = prev_i.getNext();
        final LinkedNode<T> node_j = prev_j.getNext();

        // troca nodos encadeados aos nodos anteriores
        // anterior a <i> aponta pra <j>
        // anterior a <j> aponta pra <i>
        prev_i.setNext(node_j);
        prev_j.setNext(node_i);

        // troca nodos encadeados aos nodos <i> e <j>
        // <i> aponta pra proximo de <j>
        // <j> aponta pra proximo de <i>
        temp = node_i.getNext();
        node_i.setNext(node_j.getNext());
        node_j.setNext(temp);
    }

    @Override
    public int getSize() {
        int count = 0;
        LinkedNode<T> node = this.head.getNext();
        while (node != this.tail) {
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
        while (node != this.tail) {
            if (Objects.equals(item, node.getData()))
                return true;
            node = node.getNext();
        }
        return false;
    }

    @Override
    public T getAt(int index) {
        if(this.isEmpty())
            throw new EmptyException();
        if(index < 0)
            throw new IndexOutOfBoundsException();

        LinkedNode<T> result = this.head.getNext();
        while(index > 0) {
            if(result.getNext() == this.tail)
                throw new IndexOutOfBoundsException();
            result = result.getNext();
            index--;
        }

        return result.getData();
    }

    @Override
    public int indexOf(T item) {
        LinkedNode<T> node = this.head.getNext();
        int counter = 0;

        while (node != this.tail) {
            if (Objects.equals(item, node.getData()))
                return counter;
            counter++;
            node = node.getNext();
        }
        return -1;
    }

    @Override
    public T minimum() {
        if (this.isEmpty())
            throw new EmptyException();

        LinkedNode<T> node = this.head.getNext();
        T result = node.getData();
        node = node.getNext(); // comeca pelo 2o elemento

        while (node != this.tail) {
            if (result.compareTo(node.getData()) > 0)
                result = node.getData();
            node = node.getNext();
        }

        return result;
    }

    @Override
    public T maximum() {
        if (this.isEmpty())
            throw new EmptyException();

        LinkedNode<T> node = this.head.getNext();
        T result = node.getData();
        node = node.getNext(); // comeca pelo 2o elemento

        while (node != this.tail) {
            if (result.compareTo(node.getData()) < 0)
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

        for (int i = 0; i < length; i++) {
            result[i] = node.getData();
            node = node.getNext();
        }

        return result;
    }

    @Override
    public Iterator<T> getIterator() {
        if (this.isEmpty())
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
                if (!this.hasNext())
                    throw new NoSuchElementException();

                return node.getData();
            }
        };
    }
}
