package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;
import br.otaviof.czech_accidents.sorters.SortMethod;

import java.util.Iterator;

/**
 * Implementação do método de ordenação insertion sort
 * @author otavio-f
 */
public class Insertion<T extends Comparable<? super T>> implements Method<T> {

    public Insertion() {}

    @Override
    public CustomQueue<Integer> sort(CustomList<T> data) {
        final int length = data.getSize();

        CustomList<Integer> indexes = new DynamicList<>();
        for(int counter=0; counter<length; counter++)
            indexes.append(counter);

        for(int i=1; i<length; i++) {
            T key = data.getAt(i);
            Integer place = indexes.getAt(i);

            int j = i-1;
            while(j >= 0 && data.getAt(j).compareTo(key) > 0) {
                data.swap(j, j+1);
                indexes.swap(j, j+1);
                j--;
            }
            data.replace(j+1, key);
            indexes.replace(j+1, place);
            // update
        }
        // update
        return new DynamicQueue<>(indexes);
    }
}
