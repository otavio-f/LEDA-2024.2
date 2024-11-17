package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.LinkedQueue;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

import java.util.Iterator;

public class Sorter<T extends Comparable<? super T>> {

    private final CustomQueue<CustomList<T>> buckets;
    private final Method method;

    private Sorter(CustomList<T> in, Method method) {
        this.method = method;
        this.buckets = new LinkedQueue<>();
        //TODO: Decidir a quantidade de baldes
        //Tente deixar os baldes com o mesmo número de elementos
        //TODO: Separar elementos nos baldes
        //Use intervalo entre maximum()/minimum() ou quantidade de elementos
    }

    private CustomQueue<Integer> sort() {
        // Resultado final
        CustomQueue<Integer> result = new LinkedQueue<>();

        // iterador sobre os baldes
        Iterator<CustomList<T>> iter = this.buckets.getIterator();
        while(iter.hasNext()) {
            // ordena cada balde
            CustomQueue<Integer> output = this.method.sort(iter.next());

            // adiciona resultado parcial ao resultado final
            Iterator<Integer> partial = output.getIterator();
            while(partial.hasNext())
                result.enqueue(partial.next());
        }
        return result;
    }
}
