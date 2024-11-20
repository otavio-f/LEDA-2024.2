package br.otaviof.czech_accidents.utils;

import java.util.Objects;

public final class GenericsUtils {
    // TODO: Substituir todas ocorrencias de x.equals(y) por Object.equals(x, y)
    public static <T extends Comparable<? super T>> boolean equals(T i, T j) {
        return Objects.equals(i, j);
    }

    /**
     * Cria um array genérico com tamanho arbitrário
     * @param size O tamanho do array
     * @return Um array genérico
     * @param <T> O genérico
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> T[] createArrayOfSize(int size) { //BUG: sempre retorna Comparable<?>
        return (T[]) new Comparable[size];
    }
}
