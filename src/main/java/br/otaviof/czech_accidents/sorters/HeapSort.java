package br.otaviof.czech_accidents.sorters;

public class HeapSort<T extends Comparable<? super T>> extends Sorter<T> {

    public HeapSort(T[] data) {
        super(data);
    }

    private void maxHeapify(int i, int heapsize) {
        final int l = i * 2 + 1;
        final int r = l + 1;
        int largest = i;

        if (l <= heapsize && this.data[l].compareTo(this.data[i]) > 0) {
            largest = l;
        }

        if (r <= heapsize && this.data[r].compareTo(this.data[largest]) > 0) {
            largest = r;
        }

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest, heapsize);
        }

    }

    private void buildMaxHeap(int heapsize) {
        for (int i = heapsize / 2; i >= 0; i--) {
            maxHeapify(i, heapsize);
        }
    }

    @Override
    protected void doSort() {
        int heapsize = this.length - 1;
        buildMaxHeap(heapsize);
        for (int i = this.length - 1; i > 0; i--) {
            swap(0, i);
            heapsize--;
            maxHeapify(0, heapsize);
            this.progress.update();
        }
        this.progress.update();
    }
}
