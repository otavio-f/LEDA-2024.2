package br.otaviof.czech_accidents.sorters;

public class MergeSort<T extends Comparable<? super T>> extends Sorter<T> {

    public MergeSort(T[] data) {
        super(data);
    }

    @SuppressWarnings("unchecked")
    private void merge(int left, int middle, int right) {
        Data<T> leftSide[] = (Data<T>[]) new Data[middle - left + 1]; // temp arrays, will cast to
                                                                      // correct type later
        Data<T> rightSide[] = (Data<T>[]) new Data[right - middle];

        int i, j;
        for (i = 0; i < leftSide.length; i++) {
            leftSide[i] = this.data[left + i];
        }
        for (i = 0; i < rightSide.length; i++) {
            rightSide[i] = this.data[middle + 1 + i];
        }

        i = 0;
        j = 0;
        int k = left;
        while (i < leftSide.length && j < rightSide.length) {
            Data<T> li = leftSide[i];
            Data<T> rj = rightSide[j];
            if (li.compareTo(rj) <= 0) {
                this.data[k] = li;
                i++;
            } else {
                this.data[k] = rj;
                j++;
            }
            k++;
        }

        while (i < leftSide.length) {
            this.data[k] = (Data<T>) leftSide[i];
            i++;
            k++;
        }

        while (j < rightSide.length) {
            this.data[k] = (Data<T>) rightSide[j];
            j++;
            k++;
        }
    }

    private void split(int left, int right) {
        if (left >= right)
            return;
        int middle = left + (right - left) / 2;

        split(left, middle);
        split(middle + 1, right);

        merge(left, middle, right);
        this.progress.update();
    }

    @Override
    public void doSort() {
        split(0, this.length - 1);
        this.progress.update();
    }

}
