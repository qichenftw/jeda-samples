package ch.jeda.pong;

public abstract class PaddleControl {

    /**
     * Liefert true, falls der Schläger bewegt werden soll.
     */
    public abstract boolean shouldMove();

    /**
     * Liefert die gewünschte Position des Zeigers zurück.
     */
    public abstract int getTargetPos();
}
