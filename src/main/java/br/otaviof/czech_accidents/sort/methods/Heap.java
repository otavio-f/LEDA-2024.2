package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.tracker.ProgressTracker;
import br.otaviof.czech_accidents.tracker.Trackable;

/**
 * Implementação do método de ordenação heap sort
 * @author otavio-f
 */
public class Heap<T extends Comparable<? super T>> implements Method<T>, Trackable {

    private final ProgressTracker progress = new ProgressTracker();
    private CustomList<T> data;

    public Heap() {}

    @Override
    public ProgressTracker getTracker() {
        return this.progress;
    }

    private void maxHeapify(int i, int heapsize) {
        final int l = i * 2 + 1;
        final int r = l + 1;
        int largest = i;

        if(l <= heapsize && this.data.compare(l, i) > 0)
            largest = l;

        if(r <= heapsize && this.data.compare(r, largest) > 0)
            largest = r;

        if(largest != i) {
            this.data.swap(i, largest);
            maxHeapify(largest, heapsize);
        }
    }

    private void buildMaxHeap(int heapsize) {
        for(int i=heapsize/2; i>=0; i--) {
            maxHeapify(i, heapsize);
        }
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.progress.setTarget(length);

        this.data = data;
        int heapsize = length - 1;

        buildMaxHeap(heapsize);
        for(int i= length - 1; i > 0; i--) {
            this.data.swap(0, i);
            heapsize--;
            maxHeapify(0, heapsize);
            this.progress.update();
        }
        this.progress.update();
    }
}
