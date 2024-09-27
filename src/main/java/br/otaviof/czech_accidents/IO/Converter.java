package br.otaviof.czech_accidents.IO;

public class Converter {
    public interface CellConverter<T extends Comparable<T>> {
        public T convert(String cell);
    }

    private final String[][] table;

    private Converter(String[][] table) {
        this.table = table;
    }

    private <T extends Comparable<T>> T[] extractColumn(int index, CellConverter<T> converter) {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Comparable[this.table.length - 1]; // TODO: Decide if keep headers??
        for (int i = 1; i < this.table.length; i++) {
            result[i - 1] = converter.convert(this.table[i][index]);
        }

        return result;
    }

    private int findColumn(String name) {
        for (int i = 0; i < this.table[0].length; i++) {
            if (this.table[0][i].equals(name)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Cannot find column named " + name);
    }

    public static <T extends Comparable<T>> T[] getColumnAs(String[][] table, String column,
            CellConverter<T> cellConverter) {
        Converter tableConverter = new Converter(table);
        int index = tableConverter.findColumn(column);
        return tableConverter.extractColumn(index, cellConverter);
    }
}
