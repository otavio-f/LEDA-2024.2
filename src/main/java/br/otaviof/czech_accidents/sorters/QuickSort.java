package br.otaviof.czech_accidents.sorters;

import java.util.Random;

public class QuickSort<T extends Comparable<? super T>> extends Sorter<T> {

    public QuickSort(T data[]) {
        super(data);
    }

    private int partition(int p, int r) {
        Data<T> x = this.data[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (this.data[j].compareTo(x) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, r);
        return i + 1;
    }

    private void quicksort(int p, int r) {
        if (p >= r) {
            return;
        }
        int q = partition(p, r);
        quicksort(p, q - 1);
        quicksort(q + 1, r);
        this.progress.update(); // FIXME: Find the correct position to track 100%!!!
    }

    @Override
    protected void doSort() {
        quicksort(0, this.length - 1);
    }

}
