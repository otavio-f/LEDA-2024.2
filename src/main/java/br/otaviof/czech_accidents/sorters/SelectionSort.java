package br.otaviof.czech_accidents.sorters;

public class SelectionSort<T extends Comparable<? super T>> extends Sorter<T> {

    public SelectionSort(T data[]) {
        super(data);
    }

    private int getMin(int st) {
        int result = st;
        for (int i = st + 1; i < this.length; i++) {
            if (this.data[i].compareTo(this.data[result]) < 0)
                result = i;
        }
        return result;
    }

    @Override
    public void doSort() {
        for (int i = 0; i < this.length - 1; i++) {
            this.swap(getMin(i), i);
            this.progress.update();
        }

        this.progress.update();
    }
}
