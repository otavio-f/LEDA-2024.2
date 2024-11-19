package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;

/**
 * Implementação do método de ordenação quick sort
 * @author otavio-f
 */
public class Quick<T extends Comparable<? super T>> implements Method<T> {

    private CustomList<T> data;

    public Quick() {}

    private int partition(int p, int r) {
        T x = this.data.getAt(r);
        int i = p-1;

        for(int j=p; j<r; j++) {
            if(this.data.getAt(j).compareTo(x) <= 0) {
                i++;
                this.data.swap(i, j);
            }
        }
        this.data.swap(i+1, r);
        return i+1;
    }

    private void quicksort(int p, int r) {
        if(p>=r)
            return;

        int q = partition(p, r);
        quicksort(p, q-1);
        quicksort(q+1, r);
        // update
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.data = data;
        quicksort(0, length-1);
    }
}
