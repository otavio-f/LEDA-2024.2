package br.otaviof.czech_accidents.sorters;

import br.otaviof.czech_accidents.tracker.ProgressTracker;
import br.otaviof.czech_accidents.tracker.ProgressTracker.Tracker;

public abstract class Sorter<T extends Comparable<? super T>> {
    protected final class Data<E extends Comparable<? super E>> implements Comparable<Data<E>> {
        protected final int index;
        protected final E value;

        private Data(E data, int index) {
            this.index = index;
            this.value = data;
        }

        @Override
        public int compareTo(Data<E> o) {
            return this.value.compareTo(o.value);
        }
    }

    protected final Data<T> data[];
    protected final int length;
    protected final ProgressTracker progress;
    private final T values[];

    @SuppressWarnings("unchecked")
    public Sorter(T data[]) {
        this.length = data.length;
        this.progress = new ProgressTracker(this.length);
        this.values = data;
        this.data = (Data<T>[]) new Data[this.length];

        for (int i = 0; i < this.length; i++) {
            this.data[i] = new Data<T>(data[i], i);
        }
    }

    /**
     * Sorts the data array
     */
    protected abstract void doSort();

    /**
     * Sorts the array in place and returns the new order
     *
     * @return An array containing the original order of elements shuffled to their
     *         new position
     * @throws IllegalStateException If the sorting process has already began
     */
    public final int[] sort() throws IllegalStateException {
        this.doSort();
        this.updateValuesOrder();
        return this.getOrder();
    }

    /**
     * Swaps two elements from this array
     *
     * @param i
     * @param j
     */
    public void swap(int i, int j) {
        Data<T> dataTemp = this.data[i];
        this.data[i] = this.data[j];
        this.data[j] = dataTemp;
    }

    /**
     * Gets the order changes relative to the original array
     *
     * @return
     */
    protected int[] getOrder() {
        int result[] = new int[this.length];
        for (int i = 0; i < this.length; i++)
            result[i] = this.data[i].index;
        return result;
    }

    /**
     * Updates the original array order
     */
    protected void updateValuesOrder() {
        for (int i = 0; i < this.length; i++) {
            this.values[i] = this.data[i].value;
        }
    }

    public void setProgressTracker(Tracker t) {
        this.progress.setTracker(t);
    }
}
