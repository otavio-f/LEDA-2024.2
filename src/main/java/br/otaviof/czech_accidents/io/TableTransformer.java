package br.otaviof.czech_accidents.io;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;

import java.io.*;
import java.util.Iterator;

/**
 * Classe de criação de novas tabelas com base em arquivos antigos
 */
public class TableTransformer {

    private final TableOperator operator;

    private void writeLine(BufferedWriter bw, String line) throws IOException {
        bw.append(line);
        bw.newLine();
        bw.flush();
    }

    /**
     * Cria uma nova instância
     *
     * @param source base para futuras operações
     */
    public TableTransformer(String source) {
        this.operator = new TableOperator(source);
    }

    /**
     * Filtra um arquivo de acordo com um filtro de células aplicado a uma coluna
     *
     * @param output     arquivo de saída
     * @param columnName nome da coluna a ser filtrada
     * @param filter     O filtro de célula
     */
    public int filter(String output, String columnName, CellFilter filter) throws IOException {
        // initialize write process
        File out = new File(output);
        out.delete();
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);
        BufferedReader br = this.operator.getReader();

        //TODO: tracker create

        // write headers
        String line = br.readLine();
        writeLine(bw, line);
        //update tracker

        // find column index
        this.operator.readHeaders(line);
        final int index = this.operator.findColumnIndex(columnName);
        final int colCount = this.operator.getHeaders().getSize();

        // filter and write lines
        int writtenCount = 0;
        CustomList<String> cells;
        line = br.readLine();
        while (line != null) {
            cells = this.operator.splitLine(line, colCount, "");
            if (filter.filter(cells.getAt(index))) {
                writeLine(bw, line);
                writtenCount++;
            }
            // TODO: update tracker
            line = br.readLine();
        }

        bw.close();
        return writtenCount;
    }

    /**
     * Reordena as linhas do arquivo fonte para um novo arquivo
     *
     * @param output O arquivo de saída
     * @param order  A ordem de saída
     */
    public void reorder(String output, CustomQueue<Integer> order) throws IOException {
        String headers;
        CustomList<String> allLines = new DynamicList<>();
        try (BufferedReader br = this.operator.getReader()) {
            // read headers
            String line = br.readLine();
            headers = line;

            // read the rest of lines
            line = br.readLine();
            while (line != null) {
                allLines.append(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // prepare to write lines to file
        File out = new File(output);
        out.delete();
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);

        // write header
        bw.append(headers);
        bw.newLine();
        bw.flush();

        // write rest of lines
        Iterator<Integer> iter = order.getIterator();
        while (iter.hasNext()) {
            writeLine(bw, allLines.getAt(iter.next()));
            // tracker update
        }

        bw.close();
    }
}
