package br.otaviof.czech_accidents.io;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.LinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe leitora de tabela
 * @author otavio-f
 */
public class TableReader {

    private final TableOperator operator;

    public TableReader(String file) {
        this.operator = new TableOperator(file);
    }

    /**
     * Obtém todas as linhas de uma coluna
     * @param name O nome da coluna
     * @param converter O conversor de tipo
     * @return As células da coluna convertidas para o tipo especificado
     * @param <T> O tipo
     * @throws IOException Se ocorrer algum erro de leitura
     */
    public <T extends Comparable<? super T>> CustomList<T> getColumnAs(String name, CellConverter<T> converter) throws IOException {
        BufferedReader br = this.operator.getReader();
        String line = br.readLine();

        this.operator.readHeaders(line);

        int columnCount = this.operator.getHeaders().getSize();

        final int index = this.operator.findColumnIndex(name);

        CustomList<T> result = new LinkedList<>();
        line = br.readLine();

        while(line != null) {
            CustomList<String> cells = this.operator.splitLine(line, columnCount, "");
            T converted = converter.convert(cells.getAt(index));
            result.append(converted);
            line = br.readLine();
        }

        br.close();

        return result;
    }
}
