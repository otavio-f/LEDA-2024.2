package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

/**
 * Implementação do método de ordenação quick sort com mediana de 3
 * @author otavio-f
 */
public class Quick3Median<T extends Comparable<? super T>> implements Method<T> {
    private CustomList<T> data;

    public Quick3Median() {}

    /**
     * Calcula o termo mediano entre três termos contidos nos índices
     * @param a O primeiro índice
     * @param b O segundo índice
     * @param c O terceiro índice
     * @return O termo mediano, o segundo maior dos três
     */
    private int medianOf3(int a, int b, int c) {
        if((this.data.compare(a, b) > 0) ^ (this.data.compare(a, c) > 0)) // ^ == xor
            return a;
        if((this.data.compare(b, a) > 0) ^ (this.data.compare(b, c) > 0)) // ^ == xor
            return b;
        return c;
    }

    private int partition(int p, int r) {
        this.data.swap(this.medianOf3(p, r, (r-p)/2), r);
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
        // update
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.data = data;
        quicksort(0, length-1);
    }
}
