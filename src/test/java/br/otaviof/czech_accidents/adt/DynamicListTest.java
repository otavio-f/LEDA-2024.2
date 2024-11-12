package br.otaviof.czech_accidents.adt;

import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.list.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de unidade para lista de elementos dinâmica
 */
class DynamicListTest {

    List<Integer> list;

    /**
     * Cria uma nova lista antes de cada teste
     */
    @BeforeEach
    void setup() {
        list = new DynamicList<>();
    }

    /**
     * A lista deve conter quaisquer elementos inseridos na mesma ordem que foram inseridos
     */
    @Test
    void testAppend() {
        list.append(3);
        list.append(4);
        list.append(5);

        assertArrayEquals(new Integer[] {3, 4, 5}, list.toArray());
    }

    /**
     * Inserir um elemento em uma posição específica deve posicionar esse elemento no índice específico
     */
    @Test
    void testInsertAt() {
        int size = TestUtils.genInt(5,100); // lista possui entre 5 e 100 itens
        for(int i=0; i<size; i++)
            list.append(TestUtils.genInt(10,100)); // garante que qualquer elemento vai ser entre 10-100

        list.insertAt(1, 2);
        assertEquals(1, list.getAt(2));
    }

    /**
     * Inserir um elemento na posição zero deve posicionar esse elemento como o primeiro da lista
     */
    @Test
    void testInsertAtStart() {
        int size = TestUtils.genInt(5,100); // lista possui entre 5 e 100 itens
        for(int i=0; i<size; i++)
            list.append(TestUtils.genInt(10,100)); // garante que qualquer elemento vai ser entre 10-100

        list.insertAt(8, 0);
        assertEquals(8, list.getAt(0));
    }

    /**
     * Inserir um elemento na posição k-1 em uma lista de tamanho k deve posicionar o novo elemento na penúltima posição
     */
    @Test
    void testInsertBeforeLast() {
        int k = TestUtils.genInt(5,100); // lista possui entre 5 e 100 itens
        for(int i=0; i<k; i++)
            list.append(TestUtils.genInt(10,100)); // garante que qualquer elemento vai ser entre 10-100

        list.insertAt(2, k-1);

        assertEquals(2, list.getAt(k-1));
    }

    /**
     * Inserir um elemento na posição k em uma lista de tamanho k deve posicionar o novo elemento na última posição
     */
    @Test
    void testInsertAfterLast() {
        int k = TestUtils.genInt(5,100); // lista possui entre 5 e 100 itens
        for(int i=0; i<k; i++)
            list.append(TestUtils.genInt(10,100)); // garante que qualquer elemento vai ser entre 10-100

        list.insertAt(9, k);

        assertEquals(9, list.getAt(k));
    }

    /**
     * Inserir um elemento em uma posição inválida deve gerar um erro
     */
    @Test
    void testInsertAtInvalidThrows() {
        int k = TestUtils.genInt(5,100); // lista possui entre 5 e 100 itens
        for(int i=0; i<k; i++)
            list.append(TestUtils.genInt(10,100)); // garante que qualquer elemento vai ser entre 10-100

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.insertAt(TestUtils.genInt(), 999);
        });

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.insertAt(TestUtils.genInt(), k+1);
        });

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.insertAt(TestUtils.genInt(), -1);
        });
    }

    /**
     * Remover um elemento de uma posição deve retornar esse elemento e diminuir o tamanho da lista em um.
     */
    @Test
    void testPop() {
        list.append(3);
        list.append(4);
        list.append(5);

        Integer result = list.pop(1);

        assertEquals(4, result);
        assertArrayEquals(new Integer[] {3, 5}, list.toArray());
    }

    /**
     * Remover um elemento de uma lista vazia deve gerar uma exceção
     */
    @Test
    void testPopOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
           list.pop(0);
        });
    }

    /**
     * Remover um elemento de uma posição inválida deve gerar uma exceção.
     */
    @Test
    void testPopInvalidIndexThrows() {
        int size = TestUtils.genInt(5, 100);
        for(int i=0; i<size; i++)
            list.append(TestUtils.genInt());

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.pop(-1);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.pop(size);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.pop(size+1);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.pop(9999);
        });
    }

    /**
     * Ao trocar dois elementos de posição a, somente posição deles deve mudar.
     */
    @Test
    void testSwap() {
        list.append(3);
        list.append(6);
        list.append(9);
        list.append(12);

        list.swap(0, 2);

        assertArrayEquals(new Integer[] {9,6,3,12}, list.toArray());
    }

    /**
     * Trocar elementos em uma lista vazia deve gerar uma exceção
     */
    @Test
    void testSwapOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.swap(0, 1);
        });
    }

    /**
     * Trocar elementos em posições inválidas deve gerar uma exceção
     */
    @Test
    void testSwapInvalidThrows() {
        list.append(3);
        list.append(6);

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.swap(0, 333);
        });

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.swap(1, -1);
        });
    }

    /**
     * O tamanho da lista deve corresponder à quantidade de elementos contidos na lista.
     */
    @Test
    void testGetSize() {
        assertEquals(0, list.getSize());

        list.append(TestUtils.genInt());
        list.append(TestUtils.genInt());
        list.append(TestUtils.genInt());
        list.append(TestUtils.genInt());
        list.append(TestUtils.genInt());
        list.append(TestUtils.genInt());
        assertEquals(6, list.getSize());

        list.pop(0);
        list.pop(0);
        assertEquals(4, list.getSize());
    }

    /**
     * A lista deve estar vazia quando não conter elementos
     */
    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.append(TestUtils.genInt());

        assertFalse(list.isEmpty());

        list.pop(0);

        assertTrue(list.isEmpty());
    }

    /**
     * A lista deve conter um elemento inserido previamente
     */
    @Test
    void testContains() {
        list.append(3);
        list.append(6);
        list.append(2);
        list.append(4);

        assertEquals(0, list.indexOf(3));
        assertTrue(list.contains(3));

        list.pop(0);

        assertFalse(list.contains(3));
    }

    /**
     * É possível recuperar um item em um índice específico da lista.
     */
    @Test
    void testGetAt() {
        list.append(3);
        list.append(6);
        list.append(2);
        list.append(4);

        assertEquals(3, list.getAt(0));
        assertEquals(6, list.getAt(1));
        assertEquals(2, list.getAt(2));
        assertEquals(4, list.getAt(3));
    }

    /**
     * Não deve ser possível recuperar um item em um índice inválido.
     * Neste caso, uma exceção deve ser gerada.
     */
    @Test
    void testGetAtInvalidThrows() {
        int size = TestUtils.genInt(5, 100);
        for(int i=0; i<size; i++)
            list.append(TestUtils.genInt());

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.getAt(-1);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.getAt(size);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.getAt(size+1);
        });
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.getAt(9999);
        });
    }

    /**
     * Tentar recuperar um item de um índice deve gerar uma exçeção em uma lista vazia
     */
    @Test
    void testGetAtEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.getAt(0);
        });
    }

    /**
     * Deve ser possível recuperar o índice de um item se ele estiver presente.
     */
    @Test
    void testIndexOf() {
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(100);

        assertEquals(4, list.indexOf(100));
    }

    /**
     * Ao tentar recuperar o índice de um item não existente, um índice inválido deve ser retornado
     */
    @Test
    void testIndexOfNonExistant() {
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));

        assertEquals(-1, list.indexOf(100));
    }

    /**
     * O mínimo é o menor item da lista.
     */
    @Test
    void testMinimum() {
        list.append(TestUtils.genInt(100,1_000));
        list.append(TestUtils.genInt(100,1_000));
        list.append(TestUtils.genInt(100,1_000));
        list.append(TestUtils.genInt(100,1_000));
        list.append(2);

        assertEquals(2, list.minimum());
    }

    /**
     * Calcular o mínimo de uma lista vazia deve lançar uma exceção.
     */
    @Test
    void testMinimumOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.minimum();
        });
    }

    /**
     * O máximo é o maior item da lista.
     */
    @Test
    void testMaximum() {
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(TestUtils.genInt(0,10));
        list.append(16);

        assertEquals(16, list.maximum());
    }

    /**
     * Calcular o máximo de uma lista vazia deve lançar uma exceção.
     */
    @Test
    void testMaximumOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.maximum();
        });
    }

    /**
     * O array de uma lista é a representação dos elementos contidos na lista na mesma ordem de inserção.
     */
    @Test
    void testToArray() {
        list.append(30);
        list.append(96);
        list.append(1);

        assertArrayEquals(new Integer[] {30, 96, 1}, list.toArray());
    }

    /**
     * O array de uma lista vazia não deve conter nenhum elemento.
     */
    @Test
    void testEmptyToArray() {
        assertArrayEquals(new Integer[] {}, list.toArray());

    }

    /**
     * Iterar sobre uma lista na mesma ordem de inserção dos elementos, sem modificar a coleção.
     */
    @Test
    void testIterator() {
        list.append(1);
        list.append(2);
        list.append(3);

        Iterator<Integer> it = list.getIterator();

        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());

        assertThrowsExactly(NoSuchElementException.class, () -> {
            it.next();
        });
    }

    /**
     * Iterar sobre uma lista vazia deve gerar uma exceção.
     */
    @Test
    void testIteratorOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            list.getIterator();
        });
    }
}