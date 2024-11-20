package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.tracker.ProgressTracker;
import br.otaviof.czech_accidents.tracker.Trackable;

/**
 * Implementação do método de ordenação selection sort
 * @author otavio-f
 */
public class Selection<T extends Comparable<? super T>> implements Method<T>, Trackable {

    private final ProgressTracker progress = new ProgressTracker();
    public Selection() {}

    @Override
    public ProgressTracker getTracker() {
        return this.progress;
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.progress.setTarget(length);

        for(int i=0; i<length-1; i++) {
            int min = i;
            for(int j = i+1; j<length; j++)
                if(data.compare(j, min) < 0)
                    min = j;

            data.swap(min, i);
            this.progress.update();
        }

        this.progress.update();
    }
}
