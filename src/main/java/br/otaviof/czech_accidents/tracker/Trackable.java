package br.otaviof.czech_accidents.tracker;

/**
 * Interface de objetos com progresso
 */
public interface Trackable {
    /**
     * Obtém o tracker desse objeto
     * @return O objeto de manutenção de progresso
     */
    ProgressTracker getTracker();
}
