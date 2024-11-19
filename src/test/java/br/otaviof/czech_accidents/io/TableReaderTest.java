package br.otaviof.czech_accidents.io;

import br.otaviof.czech_accidents.adt.list.CustomList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste de unidade para leitor de tabelas
 */
class TableReaderTest {
    /**
     * Deve extrair uma coluna com os dados convertidos na mesma ordem dos dados originais.
     * @throws IOException Se o teste falhar
     */
    @Test
    void getColumnAsTest() throws IOException {
        TableReader reader = new TableReader("src/test/resources/rtest.csv");
        CustomList<Integer> ages = reader.getColumnAs("age", (a) -> Integer.parseInt(a));
        assertArrayEquals(new Integer[]{ 14, 31, 10, 44, 18, 15, 8, 51, 21 }, ages.toArray());
    }
}