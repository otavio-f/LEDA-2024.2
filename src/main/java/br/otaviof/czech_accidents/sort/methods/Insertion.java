package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.tracker.ProgressTracker;
import br.otaviof.czech_accidents.tracker.Trackable;

/**
 * Implementação do método de ordenação insertion sort
 * @author otavio-f
 */
public class Insertion<T extends Comparable<? super T>> implements Method<T>, Trackable {
    private final ProgressTracker progress = new ProgressTracker();

    public Insertion() {}

    @Override
    public ProgressTracker getTracker() {
        return this.progress;
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.progress.setTarget(length);

        for(int i=1; i<length; i++) {
            T key = data.getAt(i);

            int j = i-1;
            while(j >= 0 && data.getAt(j).compareTo(key) > 0) {
                data.swap(j, j+1);
                j--;
            }
            data.replace(j+1, key);
            this.progress.update();
        }
        this.progress.update();
    }
}
