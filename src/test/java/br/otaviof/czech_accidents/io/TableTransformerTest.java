package br.otaviof.czech_accidents.io;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.adt.queue.LinkedQueue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de unidade para transformações de arquivo de tabela
 */
class TableTransformerTest {

    /**
     * Deve filtrar as linhas de acordo com uma condição e escrever em um arquivo
     * @throws IOException O teste falha
     */
    @Test
    void filterTest() throws IOException {
        TableTransformer tt = new TableTransformer("src/test/resources/rtest.csv");
        int writtenLines = tt.filter(new File("src/test/resources/wftest.csv"), "age", (a) -> (Integer.parseInt(a) >= 18));
        assertEquals(5, writtenLines);


        TableReader tr = new TableReader("src/test/resources/wftest.csv");
        CustomList<Integer> ids = tr.getColumnAs("id", (i) -> Integer.parseInt(i));
        assertArrayEquals(new Integer[] { 2, 4, 5, 8, 9 }, ids.toArray());
    }

    /**
     * Deve reordenar as linhas
     * @throws IOException
     */
    @Test
    void reorderTest() throws IOException {
        TableTransformer tt = new TableTransformer("src/test/resources/rtest.csv");
        CustomQueue<Integer> order = new LinkedQueue<>(9);
        order.enqueue(3); // linha 3 vai pra o comeco
        order.enqueue(4);
        order.enqueue(5);
        order.enqueue(0);
        order.enqueue(1);
        order.enqueue(2);
        order.enqueue(6);
        order.enqueue(7);
        order.enqueue(8);

        tt.reorder(new File("src/test/resources/wrtest.csv"), order);

        TableReader tr = new TableReader("src/test/resources/wrtest.csv");
        CustomList<Integer> ids = tr.getColumnAs("id", (i) -> Integer.parseInt(i));
        assertArrayEquals(new Integer[] { 4, 5, 6, 1, 2, 3, 7, 8, 9 }, ids.toArray());
    }
}