package br.otaviof.czech_accidents.io;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.DynamicList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.tracker.ProgressTracker;
import br.otaviof.czech_accidents.tracker.Trackable;

import java.io.*;
import java.util.Iterator;

/**
 * Classe de criação de novas tabelas com base em arquivos antigos
 */
public class TableTransformer implements Trackable {

    private final ProgressTracker progress = new ProgressTracker();
    private final TableOperator operator;

    /**
     * Cria uma nova instância
     *
     * @param source base para futuras operações
     */
    public TableTransformer(String source) {
        this.operator = new TableOperator(source);
    }

    /**
     * Cria uma nova instância
     *
     * @param source base para futuras operações
     */
    public TableTransformer(File source) {
        this.operator = new TableOperator(source);
    }

    @Override
    public ProgressTracker getTracker() {
        return this.progress;
    }

    /**
     * Adiciona uma linha no arquivo
     * @param bw BufferedWriter
     * @param line linha
     * @throws IOException se ocorrer erro de escrita
     */
    private void writeLine(BufferedWriter bw, String line) throws IOException {
        bw.append(line);
        bw.newLine();
        bw.flush();
    }

    /**
     * Filtra um arquivo de acordo com um filtro de células aplicado a uma coluna
     *
     * @param output     arquivo de saída
     * @param columnName nome da coluna a ser filtrada
     * @param filter     O filtro de célula
     */
    public int filter(File output, String columnName, CellFilter filter) throws IOException {
        // initialize write process
        output.delete();
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);
        BufferedReader br = this.operator.getReader();

        this.progress.setTarget(this.operator.countLines());

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
            this.progress.update();
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
    public void reorder(File output, CustomQueue<Integer> order) throws IOException {
        this.progress.setTarget(this.operator.countLines());

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
        output.delete();
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);

        // write header
        bw.append(headers);
        bw.newLine();
        bw.flush();

        // write rest of lines
        Iterator<Integer> iter = order.getIterator();
        while (iter.hasNext()) {
            writeLine(bw, allLines.getAt(iter.next()));
            this.progress.update();
        }

        bw.close();
    }
}
