package br.otaviof.czech_accidents.IO;

import br.otaviof.czech_accidents.tracker.ProgressTracker;

import java.io.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Extrair funcionalidade de conversão para Converter
// TODO: Substituir criação de array T[] por GenericUtils.createArrayOfSize()
// TODO: Talvez juntar nome da coluna com filter??

/**
 * @author otavio-f
 * Classe de abertura de arquivos csv em disco
 */
public class Streamer {
    /**
     * Interface funcional de filtragem de strings
     */
    public interface Filter {
        /**
         * Filtra itens de acordo com uma condição
         * @param item String a ser submetida a filtragem
         * @return true se o item é aceito pelo filtro, senão false
         */
        public boolean accepts(String item);
    }

    /**
     * Interface funcional de conversão de tipos
     * @param <T> Um tipo comparável
     */
    public interface Converter<T extends Comparable<? super T>> {
        /**
         * Converte uma célula string
         * @param item a célula a ser convertida
         * @return A célula convertida para o tipo correto
         */
        public T convert(String item);
    }

    private static Logger logger = Logger.getLogger("CSVStreamer");

    /**
     * Padrão de uma célula de um arquivo separado por vírgulas
     * Encontrado em <a href="https://regex101.com/library/eH1zP0">...</a>
     */
    final private static Pattern PATTERN = Pattern.compile( //TODO: Renomeie isso
        "(?:\\s*(?:\\\"([^\\\"]*)\\\"|([^,]*))\\s*,?)+?",
        Pattern.CASE_INSENSITIVE);

    private final File input;
    /** Quantidade de colunas */
    public final int columnCount;
    /** Quantidade de linhas incluindo cabeçalhos */
    public final int lineCount;

    public Streamer(File file) throws IOException {
        this.input = file;

        final BufferedReader br = new BufferedReader(new FileReader(this.input));

        final String header = br.readLine();
        assert(header != null);
        int cc = 0;
        final Matcher match = PATTERN.matcher(header);
        while(match.find())
            cc++;
        this.columnCount = cc-1;

        int lc = 1;
        while(br.readLine() != null)
            lc++;
        this.lineCount = lc;

        br.close();
    }

    /**
     * Obtém a coluna através de uma expressão regular
     * @param matcher O objeto que contém o <i>match</i> da expressão regular
     * @return A coluna ou <i>null</i> se não há <i>match</i>
     */
    private String findMatch(Matcher matcher) {
        if (!matcher.find())
            return null; // WARN: It seems some columns are missing
        // Already matches, so either group 1 or 2 has the value
        String result = matcher.group(1);
        if (result == null)
            result = matcher.group(2);
        return result;
    }

    /**
     * Divide uma linha de texto em colunas
     * @param line Uma linha separada por vírgulas
     * @param cellCount A quantidade de colunas esperadas
     * @return Uma linha separada por colunas.
     * Se houve falha em obter o número de colunas esperado, células serão preenchidas com string vazia
     */
    private String[] getCells(String line, int cellCount) {
        Matcher matcher = PATTERN.matcher(line);
        String[] result = new String[cellCount];
        for (int i = 0; i < cellCount; i++) {
            String match = findMatch(matcher);
            result[i] = (match==null) ? "" : match;
        }
        return result;
    }

    /**
     * Encontra o índice da coluna pelo nome
     * @param header A linha de cabeçalhos
     * @param column O nome da coluna
     * @return O índice da coluna
     * @throws IllegalArgumentException se a coluna não foi encontrada
     */
    private int findColumnIndex(String[] header, String column) {
        for(int i=0; i<header.length; i++)
            if(header[i].equals(column))
                return i;
        throw new IllegalArgumentException(String.format("Column %s", column));
    }

    /**
     * Obtém a coluna pelo nome
     * @param name O nome da coluna
     * @param convertFunc A função conversora de tipos
     * @param trackerFunc O objeto observador de progresso
     * @return Um array de tipo
     * @param <T> Um tipo genérico ordenável
     * @throws IOException se ocorreu algum erro de leitura de arquivo
     */
    public <T extends Comparable<? super T>> T[] getColumn(String name, Converter<T> convertFunc, ProgressTracker.Tracker trackerFunc) throws IOException {
        FileReader fr = new FileReader(this.input);
        BufferedReader br = new BufferedReader(fr);

        ProgressTracker pt = new ProgressTracker(this.lineCount);
        pt.setTracker(trackerFunc);

        // Get column index
        String[] cells = getCells(br.readLine(), this.columnCount);
        final int index = findColumnIndex(cells, name);
        pt.update();

        // Fill result array
        final T[] result = (T[]) new Comparable[this.lineCount-1];
        for(int i=1; i<this.lineCount; i++) {
            cells = getCells(br.readLine(), this.columnCount);
            result[i-1] = convertFunc.convert(cells[index]);
            // output[i-1] = convertFunc.convert(cells[index]);
            pt.update();
        }

        br.close();
        return result;
    }

    /**
     * Filtra as linhas de um arquivo
     * @param output O arquivo de saída. Se o arquivo já existe em disco, ele será deletado
     * @param column A coluna a ser filtrada
     * @param filterFunc A função filtradora de células
     * @param trackerFunc O objeto que mantém progresso da operação
     * @return A quantidade de linhas filtradas
     * @throws IOException Se ocorreu algum erro de leitura ou escrita
     */
    public int filterToFile(File output, String column, Filter filterFunc, ProgressTracker.Tracker trackerFunc) throws IOException {
        FileReader fr = new FileReader(this.input);
        BufferedReader br = new BufferedReader(fr);

        // Delete file if exists
        output.delete(); // TODO: falha em deletar o arquivo deve gerar um erro

        // initialize write process
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);
        ProgressTracker pt = new ProgressTracker(this.lineCount);
        pt.setTracker(trackerFunc);

        // write headers
        String line = br.readLine();
        bw.write(line);
        bw.newLine();
        bw.flush();
        pt.update();

        // find column index
        String[] cells = getCells(line, this.columnCount);
        assert(cells != null); // useful if truncating the entire line upon encountering a missing cell
        final int index = findColumnIndex(cells, column);

        // filter and write out rest of file
        int writtenCount = 0;
        line = br.readLine();
        while(line != null) {
            cells = getCells(line, this.columnCount);
            if(cells != null && filterFunc.accepts(cells[index])) {
                bw.append(line);
                bw.newLine();
                bw.flush();
                writtenCount++;
            }
            pt.update();
            line = br.readLine();
        }

        bw.close();
        fw.close();
        return writtenCount;
    }

    /**
     * Reordena as linhas de um arquivo e escreve em disco
     * @param output O arquivo de saída. Se o arquivo já existe em disco, ele será deletado
     * @param newOrder O array de ordenação
     * @param trackerFunc A função de manter progresso
     * @throws IOException Se ocorrer algum erro de leitura ou escrita
     */
    public void writeReordered(File output, int[] newOrder, ProgressTracker.Tracker trackerFunc) throws IOException {
        FileReader fr = new FileReader(this.input);
        BufferedReader br = new BufferedReader(fr);

        final String[] lines = new String[this.lineCount-1];
        ProgressTracker pt = new ProgressTracker(this.lineCount);
        pt.setTracker(trackerFunc);

        // fetch header
        final String headers = br.readLine();

        // fetch lines
        for(int i=0; i<lines.length; i++) {
            lines[i] = br.readLine();
            if(i%2 == 0)
                pt.update();
        }

        // close read inputs, we stopped reading
        br.close();
        fr.close();

        // write lines to file
        output.delete(); // TODO: falha em deletar o arquivo deve gerar um erro
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);

        // write header
        bw.append(headers);
        bw.newLine();
        bw.flush();

        // write rest of lines
        for(int i=0; i<lines.length; i++) {
            bw.append(lines[newOrder[i]]);
            bw.newLine();
            bw.flush();
            if(i%2==1)
                pt.update();
        }

        bw.close();
        fw.close();
    }
}
