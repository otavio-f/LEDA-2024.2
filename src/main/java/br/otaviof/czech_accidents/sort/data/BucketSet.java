package br.otaviof.czech_accidents.sort.data;

import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.node.LinkedNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * "Balde" de elementos.
 * @param <T>
 */
public class BucketSet<T extends Comparable<? super T>> {
    private final LinkedNode<CustomList<T>> head = new LinkedNode<>(null);
    private final LinkedNode<CustomList<T>> tail = new LinkedNode<>(null);

    public BucketSet(CustomList<T> data) {
        this.head.setNext(this.tail);
        //TODO: temp implementation
        this.append(data);
        // this.split(data);
    }

    /**
     * Divide o conjunto de dados em dois baldes
     * @param data O conjunto de dados
     */
    private void split(CustomList<T> data) {
        // TODO: Como posso dividir em mais baldes?
        // Choose a random item
        final int choiceIndex = data.getSize()/2;
        final T threshold = data.getAt(choiceIndex);

        // FIXME: Empty arrays cause exceptions down the line
        // Initialize buckets
        DynamicList<T> low = new DynamicList<>();
        DynamicList<T> high = new DynamicList<>();

        // Toss data into buckets
        // TODO: Is there any way to parallelize this?
        Iterator<T> iter = data.getIterator();
        while(iter.hasNext()) {
            T item = iter.next();
            if(item.compareTo(threshold) > 0)
                high.append(item);
            else
                low.append(item);
        }

        // Add buckets
        this.append(low);
        this.append(high);
    }

    /**
     * Adiciona um balde de elementos.
     * @param data O balde de elementos
     */
    private void append(CustomList<T> data) {
        LinkedNode<CustomList<T>> last = this.head;
        while(last.getNext() != this.tail)
            last = last.getNext();

        LinkedNode<CustomList<T>> newNode = new LinkedNode<>(data);
        newNode.setNext(this.tail);
        last.setNext(newNode);
    }

    /**
     * Cria um iterador sobre os baldes.
     * @return Um iterador sobre cada balde dessa coleção.
     */
    public Iterator<CustomList<T>> getIterator() {
        if(this.head.getNext() == this.tail)
            throw new EmptyException();

        return new Iterator<CustomList<T>>() {
            LinkedNode<CustomList<T>> node = head.getNext();
            @Override
            public boolean hasNext() {
                return node != tail;
            }

            @Override
            public CustomList<T> next() {
                if(!this.hasNext())
                    throw new NoSuchElementException();
                CustomList<T> result = node.getData();
                node = node.getNext();

                return result;
            }
        };
    }
}
