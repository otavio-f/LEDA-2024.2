package br.otaviof.czech_accidents.sorters;

public class CountingSort extends Sorter<Integer> {

    public CountingSort(Integer[] data) {
        super(data);
    }

    private Data<Integer> getMax() {
        Data<Integer> max = this.data[0];
        for (int i = 1; i < this.length; i++) {
            if (this.data[i].compareTo(max) > 0)
                max = this.data[i];
        }

        return max;
    }

    @Override
    protected void doSort() {
        final Integer max = getMax().value + 1;
        Integer aux[] = new Integer[max];
        for (int i = 0; i < max; i++) {
            aux[i] = 0;
        }

        for (int i = 0; i < this.length; i++) {
            aux[this.data[i].value]++;
        }

        for (int i = 1; i < max; i++) {
            aux[i] += aux[i - 1];
        }

        @SuppressWarnings("unchecked")
        Data<Integer> out[] = (Data<Integer>[]) new Data[length];
        for (int i = this.length - 1; i >= 0; i--) {
            Data<Integer> item = this.data[i];
            aux[item.value]--;
            out[aux[item.value]] = item;
            this.progress.update();
        }

        for (int i = 0; i < this.length; i++)
            this.data[i] = out[i];
    }

}
