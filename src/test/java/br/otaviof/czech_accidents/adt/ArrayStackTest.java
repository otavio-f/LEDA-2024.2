package br.otaviof.czech_accidents.adt;

import br.otaviof.czech_accidents.adt.queue.ArrayQueue;
import br.otaviof.czech_accidents.adt.stack.ArrayStack;
import br.otaviof.czech_accidents.adt.stack.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste de unidade para ArrayStack
 * @author otavio-f
 */
class ArrayStackTest {

    public Stack<Integer> stack;

    /**
     * Cria uma instância nova de pilha com tamanho máximo fixo antes de cada teste
     */
    @BeforeEach
    void setup() {
        stack = new ArrayStack<>(5);
    }

    /**
     * Deve ser possível copiar os dados a partir de outra estrutura de dados abstrata
     */
    @Test
    void testCopyOver() {
        final ArrayQueue<Integer> queue = new ArrayQueue<>(5);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        final ArrayStack<Integer> testStack = new ArrayStack<>(queue);

        assertArrayEquals(new Integer[] {5, 4, 3}, testStack.toArray());
    }

    /**
     * Após adicionar um elemento, a pilha deve possuir esse elemento
     */
    @Test
    void testPush() {
        stack.push(1);
        assertEquals(1, stack.top());
    }

    /**
     * O elemento desempilhado deve ser o elemento empilhado mais recentemente
     */
    @Test
    void testPop() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
    }

    /**
     * O elemento na última posição deve ser o empilhado mais recentemente
     */
    @Test
    void testTop() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.top());
    }

    /**
     * Deve desempilhar múltiplos elementos de uma única vez
     */
    @Test
    void testMultipop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Stack<Integer> test = stack.multipop(3);

        assertEquals(1, test.pop()); // multipop deixa na ordem reversa, pop deixa na ordem certa de novo
        assertEquals(2, test.pop());
        assertEquals(3, test.pop());
    }

    /**
     * Remover mais elementos de uma só vez do que a pilha possui deve remover todos os elementos
     */
    @Test
    void testMultipopUnderflow() {
        stack.push(1);
        stack.push(2);

        Stack<Integer> test = stack.multipop(100);
        assertEquals(1, test.pop());
        assertEquals(2, test.pop());

        assertTrue(stack.isEmpty());
    }

    /**
     * A pilha deve estar vazia antes da inserção de elementos e após inserir e remover um elemento
     */
    @Test
    void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    /**
     * A pilha não deve estar cheia antes da inserção de elementos
     * A pilha deve estar cheia depois da inserção de muitos elementos
     */
    @Test
    void testIsFull() {
        assertFalse(stack.isFull());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());

        assertTrue(stack.isFull());
    }

    /**
     * Adicionar um elemento a uma pilha cheia deve lançar uma exceção
     */
    @Test
    void testPushOnFullThrows() {
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());
        stack.push(TestUtils.genInt());

        assertThrowsExactly(FullException.class, ()->
                stack.push(TestUtils.genInt()));
    }

    /**
     * Remover um elemento de uma pilha vazia deve lançar uma exceção
     */
    @Test
    void testPopOnEmptyThrows() {
        stack.push(1);
        stack.pop();

        assertThrowsExactly(EmptyException.class, ()-> stack.pop());
    }

    /**
     * Remover um elemento de uma pilha vazia deve lançar uma exceção
     */
    @Test
    void testMultipopOnEmptyThrows() {
        stack.push(1);
        stack.pop();

        assertThrowsExactly(EmptyException.class, ()-> stack.multipop(1));
    }

    /**
     * A ordem dos elementos enfileirados deve ser a mesma dos elementos desenfileirados
     */
    @Test
    void testPushPopOrder() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    /**
     * A pilha deve estar vazia após inserir e remover elementos repetidas vezes
     */
    @Test
    void testPushPull() {
        for(int i=0; i<100; i++) {
            stack.push(TestUtils.genInt());
            stack.pop();
        }
        assertTrue(stack.isEmpty());
    }

    /**
     * A pilha deve estar vazia após encher e esvaziar
     */
    @Test
    void testFillDrain() {
        for(int i=0; i<20; i++) {
            for(int j=0; j<5; j++)
                stack.push(TestUtils.genInt());

            for(int j=0; j<5; j++)
                stack.pop();
        }
        assertTrue(stack.isEmpty());
    }

    /**
     * Verificar o último elemento em uma pilha vazia deve lançar exceção
     */
    @Test
    void testTopOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, ()-> stack.top());
    }

    /**
     * Verificar o último elemento em uma pilha esvaziada deve lançar exceção
     */
    @Test
    void testTopOnEmptiedThrows() {
        stack.push(1);
        stack.pop();

        assertThrowsExactly(EmptyException.class, ()-> stack.top());
    }

    /**
     * O array de uma pilha é a representação dos elementos contidos na pilha em ordem.
     */
    @Test
    void testToArray() {
        stack.push(30);
        stack.push(96);
        stack.push(1);

        assertArrayEquals(new Integer[] {1, 96, 30}, stack.toArray());
    }

    /**
     * O array de uma pilha vazia não deve conter nenhum elemento.
     */
    @Test
    void testEmptyToArray() {
        assertArrayEquals(new Integer[] {}, stack.toArray());

    }

    /**
     * Iterar sobre uma pilha em ordem, sem modificar a coleção.
     */
    @Test
    void testIterator() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Iterator<Integer> it = stack.getIterator();

        assertEquals(3, it.next());
        assertEquals(2, it.next());
        assertEquals(1, it.next());

        assertThrowsExactly(NoSuchElementException.class, () -> {
            it.next();
        });
    }

    /**
     * Iterar sobre uma pilha vazia deve gerar uma exceção.
     */
    @Test
    void testIteratorOnEmptyThrows() {
        assertThrowsExactly(EmptyException.class, () -> {
            stack.getIterator();
        });
    }
}