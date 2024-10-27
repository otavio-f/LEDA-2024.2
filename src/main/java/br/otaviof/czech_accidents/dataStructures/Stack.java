package br.otaviof.czech_accidents.dataStructures;

public interface Stack <T extends Comparable<? super T>> {

    public void push(T item);

    public T pop();

    public T top();

    public Stack<T> multitop(int k);

    public boolean isEmpty();
}
