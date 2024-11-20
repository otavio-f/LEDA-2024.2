package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.tracker.ProgressTracker;
import br.otaviof.czech_accidents.tracker.Trackable;

/**
 * Implementação do método de ordenação quick sort
 * @author otavio-f
 */
public class Quick<T extends Comparable<? super T>> implements Method<T>, Trackable {

    private final ProgressTracker progress = new ProgressTracker();
    private CustomList<T> data;

    public Quick() {}

    @Override
    public ProgressTracker getTracker() {
        return this.progress;
    }

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
        this.progress.update();
    }

    // quicksort normal dá stackoverflow com arrays largos
    // https://stackoverflow.com/questions/33884057/quick-sort-stackoverflow-error-for-large-arrays
    // iteração na partição menor e recursão na partição maior
    private void quicksortMix(int p, int r) {
        while(p<r) {
            int q = partition(p, r);
            if (q-p <= r-(q+1)) {
                quicksortMix(p, q-1);
                p=q+1;
            } else {
                quicksortMix(q+1, r);
                r=q-1;
            }
        }
        this.progress.update();
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.progress.setTarget(length);

        this.data = data;
        //quicksort(0, length-1);
        quicksortMix(0, length-1);
    }
}
