package br.otaviof.czech_accidents.IO;

// TODO: Colocar substituir Comparable<T> por Comparable<? super T>
// TODO: Substituir criação de array T[] por GenericUtils.createArrayOfSize()

/**
 * @author otavio-f
 * Funções de transformação de dados em disco para arrays de elementos
 */
public class Converter {
    /**
     * Interface funcional de conversão de dados
     * @param <T> Um tipo genérico comparável
     */
    public interface CellConverter<T extends Comparable<T>> {
        /**
         * Converte uma célula string para um tipo genérico
         * @param cell A célula a ser convertida
         * @return A célula convertida para o tipo
         */
        public T convert(String cell);
    }

    private final String[][] table;

    private Converter(String[][] table) {
        this.table = table;
    }

    /**
     * Extrai uma coluna de um arquivo separado por vírgulas
     * @param index O índice da coluna
     * @param converter A função conversora de célula
     * @return Um array
     * @param <T> Um tipo genérico comparável
     */
    private <T extends Comparable<T>> T[] extractColumn(int index, CellConverter<T> converter) {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Comparable[this.table.length - 1];
        for (int i = 1; i < this.table.length; i++) {
            result[i - 1] = converter.convert(this.table[i][index]);
        }

        return result;
    }

    /**
     * Encontra uma coluna
     * @param name O nome da coluna
     * @return O índice da coluna
     * @throws NoSuchFieldError se não encontrar a coluna pelo nome
     */
    private int findColumn(String name) {
        for (int i = 0; i < this.table[0].length; i++) {
            if (this.table[0][i].equals(name)) {
                return i;
            }
        }
        // TODO: Mudar para NoSuchFieldError
        throw new IllegalArgumentException("Cannot find column named " + name);
    }

    /**
     * Extrai uma coluna de uma tabela com o tipo correto
     * @param table A tabela a ser extraída
     * @param column O nome da coluna
     * @param cellConverter A função que converte a célula para o tipo correto
     * @return Um array convertido para o tipo correto
     * @param <T> Um tipo genérico
     */
    public static <T extends Comparable<T>> T[] getColumnAs(String[][] table, String column,
            CellConverter<T> cellConverter) {
        Converter tableConverter = new Converter(table);
        int index = tableConverter.findColumn(column);
        return tableConverter.extractColumn(index, cellConverter);
    }
}
