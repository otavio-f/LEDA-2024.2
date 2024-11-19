package br.otaviof.czech_accidents.io;

/**
 * Interface funcional de conversão de células
 * @param <T> Um tipo conversível
 * @author otavio-f
 */
public interface CellConverter<T> {
    /**
     * Converte a célula para o tipo correto
     * @param cell A célula a ser convertida
     * @return A célula com o tipo correto
     */
    T convert(String cell);
}
