package br.otaviof.czech_accidents.dataStructures;

public interface DynamicList<T extends Comparable<? super T>> {
    public void insert(T item);

    public void insert(T item, int index);

    public T pop(T item); // TODO: talvez remove? ou removeAt(index)?

    public T pop(int index); // TODO: talvez remove sem retorno?

    public void swap(int i, int j); // TODO: Util, talvez remover?

    public T predecessor(T item);

    public T sucessor(T item);

    public int size();

    public boolean isEmpty();

    public T search(T item);

    public T getAt(int index);

    public T minimum();

    public T maximum();

    public T[] toArray();
}
