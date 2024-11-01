package br.otaviof.czech_accidents.dataStructures;

/**
 * @author otavio-f
 * Exceção a ser lançada quando operações invávlidas ocorrerem sobre uma coleção cheia
 */
public class FullException extends RuntimeException {
    public FullException(String message) {
        super(message);
    }

    public FullException() {
        super();
    }
}
