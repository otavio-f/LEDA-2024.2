package br.otaviof.czech_accidents.tracker;

public final class ProgressTracker {
    public interface Tracker {
        public void update(double progress);
    }

    private Tracker tracker = null;
    private final int target;
    private int current;

    public ProgressTracker(int target) {
        this.target = target;
        this.current = 0;
    }

    private double getProgress() {
        return ((double) this.current) / ((double) this.target);
    }

    public void update() {
        this.current++;
        if (tracker != null)
            tracker.update(this.getProgress());
    }

    public void setTracker(Tracker t) {
        this.tracker = t;
    }
}
