package br.otaviof.czech_accidents.sort;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;
import br.otaviof.czech_accidents.adt.stack.CustomStack;
import br.otaviof.czech_accidents.adt.stack.DynamicStack;

import java.util.Iterator;

/**
 * Implementação do método de ordenação counting sort
 * @author otavio-f
 */
public class Counting { // Classe não mistura com outras
    public Counting() {}

    public CustomQueue<Integer> sort(CustomList<Integer> data) {
        final int max = data.maximum() + 1;
        final int length = data.getSize();

        // O custo de insercao em lista dinamica eh maior, mas compensado pelo custo de busca
        // insercao O(log(n)) lista dinamica vs. O(n) em lista encadeada, O(1) em lista duplamente encadeada
        // busca O(1) em lista dinamica vs. O(n) em lista encadeada.
        CustomList<Integer> aux = new DynamicList<>();//dynamic list pq
        for(int i=0; i<max; i++) {
            aux.append(0);
        }

        // Iterador reduz o custo de busca em lista encadeada para O(1), mas n pode ser aplicado em todas situacoes
        Iterator<Integer> iter = data.getIterator();
        while(iter.hasNext()) {
            Integer i = iter.next();
            aux.replace(i, aux.getAt(i) + 1);
        }

        // Aqui nao eh aplicavel o uso de iterador. Duas buscas O(1) em dinamica vs O(n) em encadeada
        for(int i=1; i<max; i++) {
            aux.replace(i, aux.getAt(i-1) + aux.getAt(i));
        }

        // Usa Stack dinamica porque o tamanho final ja eh conhecido.
        // O resultado vai estar na ordem reversa, por isso LILO em vez de FIFO
        CustomStack<Integer> result = new DynamicStack<>(length);
        for(int i=length-1; i>=0; i--) {
            Integer item = data.getAt(i);
            aux.replace(item, aux.getAt(item)-1);
            result.push(aux.getAt(item));
        }

        return new DynamicQueue<>(result); // conversao de pilha -> fila inverte ordem
    }
}
