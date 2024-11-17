package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.LinkedQueue;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

import java.util.Iterator;

public class Sorter<T extends Comparable<? super T>> {
    //TODO: CHANGE SORTERS TO USE LIST<T>
    //TODO: Use buckets to sort
    //SORT BUCKETS (MULTITHREAD?), GET INDEX QUEUES
    //JOIN INDEX QUEUES INTO ONE
    //RETURN BIG INDEX QUEUE

    private CustomQueue<CustomList<T>> buckets;
    private Method method;

    private Sorter(CustomList<T> in) {
        this.buckets = new LinkedQueue<>();
        //DIVIDE IN BUCKETS BY RANGE (TEST LENGTHS FOR OPTIMAL NUMBER OF BUCKETS)
        //TODO: How to decide which element gets in whick bucket? (Aiming to distribute elements evenly)
        //TIP: USE MAXIMUM/MINIMUM RANGE AND BUCKET SIZES
    }

    private void sort() {
        CustomQueue<Integer> output = new LinkedQueue<>();
        Iterator<CustomList<T>> iter = this.buckets.getIterator();
        while(iter.hasNext()) {
            Iterator<Integer> partial = this.method.sort(iter.next()).getIterator();
            while(partial.hasNext())
                output.enqueue(partial.next());
        }
    }

}
