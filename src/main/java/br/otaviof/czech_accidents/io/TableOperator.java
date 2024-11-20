package br.otaviof.czech_accidents.io;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.list.LinkedList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe leitora de tabela
 * @author otavio-f
 */
public class TableOperator {

    /**
     * Padrão de uma célula de um arquivo separado por vírgulas
     * Referência: <a href="https://regex101.com/library/eH1zP0">...</a>
     */
    final private static Pattern CELL_REGEX = Pattern.compile("(?:\\s*(?:\\\"([^\\\"]*)\\\"|([^,]*))\\s*,?)+?", Pattern.CASE_INSENSITIVE);

    private final File input;
    private CustomList<String> headers;

    protected TableOperator(String file) {
        this.input = new File(file);
    }
    protected TableOperator(File file) {
        this.input = file;
    }

    protected CustomList<String> getHeaders() {
        return this.headers;
    }

    /**
     * Conta a quantidade de linhas
     * @return I
     * @throws IOException Se ocorrer erro de leitura de arquivo
     */
    protected int countLines() throws IOException {
        BufferedReader br = getReader();

        int count = -1;
        while(br.readLine() != null)
            count++;
        br.close();

        return count;
    }

    /**
     * Cria um BufferedReader no arquivo fonte
     * @return Uma instância de BufferedReader
     * @throws IOException Se ocorrer erro de leitura
     */
    protected BufferedReader getReader() throws IOException {
        FileReader fr = new FileReader(this.input);
        return new BufferedReader(fr);
    }

    /**
     * Separa a linha de cabeçalho em cabeçalhos
     * @param line A primeira linha
     */
    protected void readHeaders(String line) {
        Matcher matcher = CELL_REGEX.matcher(line);

        CustomList<String> result = new LinkedList<>();
        while (matcher.find()) {
            String cell = (matcher.group(1) != null) ? matcher.group(1) : matcher.group(2);
            result.append(cell);
        }

        this.headers = result;
    }

    /**
     * Parte uma linha em células
     * @param line A linha do arquivo tabular
     * @param cellCount A quantidade de células a ser extraída
     * @param fill O que colocar caso não possua células suficientes
     * @return Uma lista com as células
     */
    protected CustomList<String> splitLine(String line, int cellCount, String fill) {
        Matcher matcher = CELL_REGEX.matcher(line);
        CustomList<String> result = new LinkedList<>();

        for(int i=0; i<cellCount; i++) {
            if(!matcher.find()) {
                result.append(fill);
                continue;
            }
            String cell = (matcher.group(1) != null)? matcher.group(1) : matcher.group(2);
            result.append(cell);
        }

        return result;
    }

    /**
     * Encontra o índice da coluna que possui o cabeçalho
     * @param headerName O nome do cabeçalho
     * @return O índice da coluna
     * @throws IllegalArgumentException se o nome do cabeçalho não estiver contido na primeira linha
     */
    protected int findColumnIndex(String headerName) {
        int count = 0;
        Iterator<String> iter = this.headers.getIterator();
        while(iter.hasNext()) {
            if(Objects.equals(iter.next(), headerName))
                return count;
            count++;
        }
        throw new IllegalArgumentException(String.format("Column %s not found!", headerName));
    }
}
