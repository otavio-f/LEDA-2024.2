package br.otaviof.czech_accidents.dataStructures;

public interface DynamicList<T extends Comparable<? super T>> {
    public void insert(T item);

    public T pop(T item);//TODO: talvez remove? ou removeAt(index)?

    public T predecessor(T item);

    public T sucessor(T item);

    public int size();

    public T search(T item);

    public T minimum();

    public T maximum();
}
