package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;

/**
 * Implementação do método de ordenação heap sort
 * @author otavio-f
 */
public class Heap<T extends Comparable<? super T>> implements Method<T> {

    private CustomList<T> data;
    private CustomList<Integer> result;

    public Heap() {}

    private void maxHeapify(int i, int heapsize) {
        final int l = i*2+1;
        final int r = l+1;
        int largest = i;

        if(l <= heapsize && this.data.compare(l, i) > 0)
            largest = l;

        if(r <= heapsize && this.data.compare(r, largest) > 0)
            largest = r;

        if(largest != i) {
            this.data.swap(i, largest);
            this.result.swap(i, largest);
            maxHeapify(largest, heapsize);
        }
    }

    private void buildMaxHeap(int heapsize) {
        for(int i=heapsize/2; i>=0; i--) {
            maxHeapify(i, heapsize);
        }
    }
    @Override
    public CustomQueue<Integer> sort(CustomList<T> data) {
        final int length = data.getSize();
        this.data = data;
        int heapsize = length - 1;

        this.result = new DynamicList<>();
        for(int counter = 0; counter < length; counter++)
            result.append(counter);

        buildMaxHeap(heapsize);
        for(int i= length - 1; i > 0; i--) {
            this.data.swap(0, i);
            this.result.swap(0, i);
            heapsize--;
            maxHeapify(0, heapsize);
            // update
        }
        // update

        return new DynamicQueue<>(this.result);
    }
}
