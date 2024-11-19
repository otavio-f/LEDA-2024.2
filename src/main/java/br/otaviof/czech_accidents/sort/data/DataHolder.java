package br.otaviof.czech_accidents.sort.data;

public class DataHolder<T extends Comparable<? super T>> implements Comparable<DataHolder<T>> {
    private final T data;
    private final int index;

    public DataHolder(T data, int index) {
        this.data = data;
        this.index = index;
    }

    public T getData() {
        return data;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(DataHolder<T> other) {
        return this.data.compareTo(other.data);
    }

    public int compareTo(T other) {
        return this.data.compareTo(other);
    }
}
