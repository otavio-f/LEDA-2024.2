package br.otaviof.czech_accidents.sorters;

public class InsertionSort<T extends Comparable<? super T>> extends Sorter<T> {

    public InsertionSort(T[] data) {
        super(data);
    }

    @Override
    protected void doSort() {
        for (int i = 1; i < this.length; i++) {
            Data<T> key = this.data[i];
            int j = i - 1;
            while (j >= 0 && data[j].compareTo(key) > 0) {
                this.swap(j, j + 1);
                j--;
            }
            this.data[j + 1] = key;
            this.progress.update();
        }
        this.progress.update();
    }

}
