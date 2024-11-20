package br.otaviof.czech_accidents.tracker;

/**
 * Interface funcional de progresso
 */
public interface UpdateAction {
    /**
     * Executa uma ação sobre o progresso atual
     * @param progress O progresso, valor entre 0 e 1
     */
    void act(double progress);
}
