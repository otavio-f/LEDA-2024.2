package br.otaviof.czech_accidents.dataStructures;

/**
 * @author otavio-f
 * Exceção a ser lançada quando operações invávlidas ocorrerem sobre uma coleção vazia
 */
public class EmptyException extends RuntimeException {
    public EmptyException(String message) {
        super(message);
    }

    public EmptyException() {
        super();
    }
}
