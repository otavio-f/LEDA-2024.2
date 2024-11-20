package br.otaviof.czech_accidents.tracker;

/**
 * Classe de manutenção de progresso
 */
public final class ProgressTracker {
    private UpdateAction action = null;
    private int target;
    private int current;

    public ProgressTracker() {
        this.target = 0;
        this.current = 0;
    }

    /**
     * Obtém o progresso atual
     * @return Um número entre 0 e 1 representando a porcentagem do progresso atual
     */
    private double getProgress() {
        return ((double) this.current) / ((double) this.target);
    }

    /**
     * Incrementa o progresso atual
     */
    public void update() {
        this.current++;
        if (action != null)
            action.act(this.getProgress());
    }

    /**
     * Atualiza o progresso máximo e redefine o progresso atual para 0%
     * @param target O alvo no qual o progresso é 100%
     */
    public void setTarget(int target) {
        this.target = target;
        this.reset();
    }

    /**
     * Redefine o progresso atual para zero
     */
    public void reset() {
        this.current = 0;
    }

    /**
     * Define a ação a ser executada quando o progresso aumentar
     * @param t A ação a ser executada
     */
    public void setAction(UpdateAction t) {
        this.action = t;
    }
}
