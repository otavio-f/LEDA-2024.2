package br.otaviof.czech_accidents.sort.methods;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;

/**
 * Implementação do método de ordenação merge sort
 * @author otavio-f
 */
public class Merge<T extends Comparable<? super T>> implements Method<T> {

    private CustomList<T> data;

    public Merge() {}

    private void merge(int left, int middle, int right) {
        CustomList<T> leftSide = new DynamicList<>();
        CustomList<T> rightSide = new DynamicList<>();

        int i, j;
        for(i = 0; i<middle-left+1; i++)
            leftSide.append(this.data.getAt(left+i));

        for(i=0; i<right-middle; i++)
            rightSide.append(this.data.getAt(middle+i+1));

        i=0;
        j=0;
        int k=left;
        while(i < leftSide.getSize() && j<rightSide.getSize()) {
            T li = leftSide.getAt(i);
            T rj = rightSide.getAt(j);
            if(li.compareTo(rj) <= 0) {
                this.data.replace(k, li);
                i++;
            } else {
                this.data.replace(k, rj);
                j++;
            }
            k++;
        }

        while(i < leftSide.getSize()) {
            this.data.replace(k, leftSide.getAt(i));
            i++;
            k++;
        }

        while(j < rightSide.getSize()) {
            this.data.replace(k, rightSide.getAt(j));
            j++;
            k++;
        }
    }

    private void split(int left, int right) {
        if(left >= right)
            return;

        int middle = left + (right-left) / 2;

        this.split(left, middle);
        this.split(middle+1, right);

        this.merge(left, middle, right);
        //update
    }

    @Override
    public void sort(CustomList<T> data) {
        final int length = data.getSize();
        this.data = data;
        this.split(0, length-1);
    }
}
