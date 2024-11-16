package br.otaviof.czech_accidents.adt;

import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.ArrayQueue;
import br.otaviof.czech_accidents.adt.queue.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Classe de teste de unidade para ArrayQueueTest
 * @author otavio-f
 */
class ArrayQueueTest {

    public Queue<Integer> queue;

    /**
     * Cria uma instância nova de fila com tamanho máximo fixo antes de cada teste
     */
    @BeforeEach
    void setup() {
        queue = new ArrayQueue<>(5);
    }

    /**
     * Deve ser possível copiar os dados a partir de outra estrutura de dados abstrata
     */
    @Test
    void testCopyOver() {
        final DynamicList<Integer> list = new DynamicList<>();
        list.append(3);
        list.append(4);
        list.append(5);

        final ArrayQueue<Integer> testQueue = new ArrayQueue<>(list);

        assertArrayEquals(list.toArray(), testQueue.toArray());
    }

    /**
     * Após adicionar um elemento, a fila deve possuir esse elemento
     */
    @Test
    void testEnqueue() {
        queue.enqueue(1);
        assertEquals(1, queue.peekHead());
    }

    /**
     * O elemento desenfileirado deve ser o elemento enfileirado mais antigamente
     */
    @Test
    void testDequeue() {
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    /**
     * O elemento na última posição deve ser o inserido mais recentemente
     */
    @Test
    void testPeekTail() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.peekTail());
    }

    /**
     * O elemento da primeira posição da fila deve ser o elemento que foi inserido mais antigamente
     */
    @Test
    void testPeekHead() {
        queue.enqueue(1);
        queue.enqueue(12);
        assertEquals(1, queue.peekHead());
    }

    /**
     * A fila deve estar vazia antes da inserção de elementos e após inserir e remover um elemento
     */
    @Test
    void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    /**
     * A fila não deve estar cheia antes da inserção de elementos
     * A fila deve estar cheia depois da inserção de muitos elementos
     */
    @Test
    void testIsFull() {
        assertFalse(queue.isFull());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());

        assertTrue(queue.isFull());
    }

    /**
     * Adicionar um elemento a uma fila cheia deve lançar uma exceção
     */
    @Test
    void testEnqueueOnFullThrows() {
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());
        queue.enqueue(TestUtils.genInt());

        assertThrowsExactly(FullException.class, ()->
                queue.enqueue(TestUtils.genInt()));
    }

    /**
     * Remover um elemento de uma fila vazia deve lançar uma exceção
     */
    @Test
    void testDequeueOnEmptyThrows() {
        queue.enqueue(1);
        queue.dequeue();

        assertThrowsExactly(EmptyException.class, ()->queue.dequeue());
    }

    /**
     * A ordem dos elementos enfileirados deve ser a mesma dos elementos desenfileirados
     */
    @Test
    void testEnqueueDequeueOrder() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    /**
     * A fila deve estar vazia após inserir e remover elementos repetidas vezes
     */
    @Test
    void testPushPull() {
        for(int i=0; i<100; i++) {
            queue.enqueue(TestUtils.genInt());
            queue.dequeue();
        }
        assertTrue(queue.isEmpty());
    }

    /**
     * A fila deve estar vazia após encher e esvaziar
     */
    @Test
    void testFillDrain() {
        for(int i=0; i<20; i++) {
            for(int j=0; j<5; j++)
                queue.enqueue(TestUtils.genInt());

            for(int j=0; j<5; j++)
                queue.dequeue();
        }
        assertTrue(queue.isEmpty());
    }

    /**
     * Verificar o último elemento em uma fila vazia deve lançar exceção
     */
    @Test
    void testPeekTailOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, ()->queue.peekTail());
    }

    /**
     * Verificar o último elemento em uma fila esvaziada deve lançar exceção
     */
    @Test
    void testPeekTailOnEmptiedThrows() {
        queue.enqueue(1);
        queue.dequeue();

        assertThrowsExactly(EmptyException.class, ()->queue.peekTail());
    }

    /**
     * Verificar o primeiro elemento em uma fila vazia deve lançar exceção
     */
    @Test
    void testPeekHeadOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, ()->queue.peekHead());
    }

    /**
     * Verificar o primeiro elemento em uma fila esvaziada deve lançar exceção
     */
    @Test
    void testPeekHeadOnEmptiedThrows() {
        queue.enqueue(1);
        queue.dequeue();

        assertThrowsExactly(EmptyException.class, ()->queue.peekHead());
    }


    /**
     * O array de uma fila é a representação dos elementos contidos na fila em ordem.
     */
    @Test
    void testToArray() {
        queue.enqueue(30);
        queue.enqueue(96);
        queue.enqueue(1);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(30);
        queue.enqueue(96);
        queue.enqueue(1);
        queue.enqueue(40);

        assertArrayEquals(new Integer[] {1, 30, 96, 1, 40}, queue.toArray());
    }

    /**
     * O array de uma fila vazia não deve conter nenhum elemento.
     */
    @Test
    void testEmptyToArray() {
        assertArrayEquals(new Integer[] {}, queue.toArray());

    }

    /**
     * Iterar sobre uma fila em ordem, sem modificar a coleção.
     */
    @Test
    void testIterator() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        Iterator<Integer> it = queue.getIterator();

        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());

        assertThrowsExactly(NoSuchElementException.class, () -> {
            it.next();
        });
    }

    /**
     * Iterar sobre uma fila vazia deve gerar uma exceção.
     */
    @Test
    void testIteratorOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            queue.getIterator();
        });
    }
}