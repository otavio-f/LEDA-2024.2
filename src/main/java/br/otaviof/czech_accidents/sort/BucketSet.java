package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;
import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.node.LinkedNode;
import br.otaviof.czech_accidents.utils.GenericsUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * "Balde" de elementos. Contém elementos
 * @param <T>
 */
public class BucketSet<T extends Comparable<? super T>> implements AbstractDataType<CustomList<T>> {
    private final LinkedNode<CustomList<T>> head = new LinkedNode<>(null);
    private final LinkedNode<CustomList<T>> tail = new LinkedNode<>(null);

    public BucketSet(CustomList<T> data) {
        this.head.setNext(this.tail);
        this.split(data);
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

    private int getSize() {
        int count = 0;
        LinkedNode<?> node = this.head;
        while(node.getNext() != this.tail)
            count++;
        return count;
    }

    @Override
    public Iterator<CustomList<T>> getIterator() {
        if(this.head.getNext() == this.tail)
            throw new EmptyException();
        return new Iterator<CustomList<T>>() {
            LinkedNode<CustomList<T>> node = head;
            @Override
            public boolean hasNext() {
                return node == tail;
            }

            @Override
            public CustomList<T> next() {
                node = node.getNext();
                if(!this.hasNext())
                    throw new NoSuchElementException();
                return node.getData();
            }
        };
    }

    @Override
    public CustomList<T>[] toArray() {
        CustomList<T>[] result = GenericsUtils.createArrayOfSize(this.getSize());
        Iterator<CustomList<T>> it = this.getIterator();
        for(int i=0; i<result.length; i++) {
            result[i] = it.next();
        }
        return result;
    }
}
