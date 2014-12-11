package ch.jeda.lpc;

public enum Action {

    STAND("walk", 0, 0, null),
    HURT("hurt", 0, 5, STAND),
    SHOOT("shoot", 0, 12, STAND),
    SLASH("slash", 0, 5, STAND),
    SPELLCAST("spellcast", 0, 6, STAND),
    THRUST("thrust", 0, 7, STAND),
    WALK("walk", 1, 7, null);

    private final String fileName;
    private final int firstStep;
    private final int lastStep;
    private final Action next;

    private Action(final String fileName, final int firstStep, final int lastStep, final Action next) {
        this.fileName = fileName;
        this.firstStep = firstStep;
        this.lastStep = lastStep;
        this.next = next;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getFirstStep() {
        return this.firstStep;
    }

    public int getLastStep() {
        return this.lastStep;
    }

    public Action getNext() {
        return this.next;
    }

}
