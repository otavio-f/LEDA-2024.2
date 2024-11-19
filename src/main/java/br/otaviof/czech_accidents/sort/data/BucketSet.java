package br.otaviof.czech_accidents.sort.data;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.node.LinkedNode;
import br.otaviof.czech_accidents.utils.GenericsUtils;

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
        //TODO: impl. temporaria
        LinkedNode<CustomList<T>> node = new LinkedNode<>(data);
        node.setNext(this.tail);
        this.head.setNext(node);
        //this.split(data);
    }

    // Max-min/4
    private void split(CustomList<T> data) {
        //TODO
        // 1o passe: Escolha referências para separar elementos. Tente deixar os baldes com o mesmo número de elementos
        Iterator<T> iter = data.getIterator();
        while(iter.hasNext()) {
            // ???
        }

        //TODO
        // 2o passe: separe
        iter = data.getIterator();
        while(iter.hasNext()) {
            // ???
        }
    }

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
