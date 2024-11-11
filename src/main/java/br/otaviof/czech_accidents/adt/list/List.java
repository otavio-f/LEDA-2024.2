package br.otaviof.czech_accidents.adt.list;

import br.otaviof.czech_accidents.adt.AbstractDataType;
import br.otaviof.czech_accidents.adt.EmptyException;

import java.util.Iterator;

/**
 * @author otavio-f
 * Representa uma lista de elementos de mesmo tipo
 * @param <T> Um tipo genérico comparável
 */
public interface List<T extends Comparable<? super T>> extends AbstractDataType<T> {
    /**
     * Insere um item no final dessa lista
     * @param item
     */
    public void append(T item);

    /**
     * Insere um item em uma posição específica.
     * Inserindo na posição determinada por List.size() insere após o último item
     * @param item
     * @param index
     * @throws IndexOutOfBoundsException se o índice for menor que 0 ou maior que o tamanho dessa lista
     */
    public void insertAt(T item, int index);

    /**
     * Remove e retorna um item pelo índice
     * @param index O índice do item a ser removido
     * @return O item removido
     * @throws EmptyException se a lista está vazia
     * @throws IndexOutOfBoundsException se o índice for inválido
     */
    public T pop(int index);

    /**
     * Troca dois itens de posição de acordo com seus índices
     * @param i
     * @param j
     * @throws EmptyException se a lista está vazia
     * @throws IndexOutOfBoundsException se algum dos índices não for válido
     */
    public void swap(int i, int j);

    /**
     * Calcula a quantidade de elementos contidos nessa lista
     * @return O tamanho da lista
     */
    public int getSize();

    /**
     * Identifica se a lista está vazia
     * @return true se a lista não tem nenhum elemento, senão retorna false
     */
    public boolean isEmpty();

    /**
     * Checa se a lista contém um item
     * @param item
     * @return true se existe um item igual a esse, senão false
     * @throws EmptyException se a lista está vazia
     */
    public boolean contains(T item);

    /**
     * Obtém o item no índice especificado
     * @param index
     * @return O item no índice
     * @throws EmptyException se a lista está vazia
     * @throws IndexOutOfBoundsException se o índice é inválido
     */
    public T getAt(int index);

    /**
     * Calcula o índice do item
     * @param item o item a ser encontrado
     * @return O índice do item, ou -1 se não foi encontrado
     */
    public int indexOf(T item);

    /**
     * Calcula o menor item na lista
     * @return o menor item
     * @throws EmptyException se a lista está vazia
     */
    public T minimum();

    /**
     * Calcula o maior item na lista
     *
     * @return O maior item
     * @throws EmptyException se a lista está vazia
     */
    public T maximum();

}
