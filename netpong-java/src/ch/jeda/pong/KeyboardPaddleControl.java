package ch.jeda.pong;

import ch.jeda.event.Key;
import ch.jeda.event.KeyDownListener;
import ch.jeda.event.KeyEvent;
import ch.jeda.event.KeyUpListener;
import ch.jeda.ui.Window;

public class KeyboardPaddleControl extends PaddleControl implements KeyDownListener, KeyUpListener {

    private Key up;
    // Taste für Abwärtsbewegung
    private Key down;
    // Ist die Aufwärtstaste gedrückt?
    private boolean moveUp;
    // Ist die Abwärtstaste gedrückt?
    private boolean moveDown;

    public KeyboardPaddleControl(Window window, Key up, Key down) {
        this.up = up;
        this.down = down;
        window.addEventListener(this);
    }

    @Override
    public boolean shouldMove() {
        return moveUp ^ moveDown;
    }

    @Override
    public int getTargetPos() {
        if (moveUp) {
            return 0;
        }
        else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        if (event.getKey() == up) {
            moveUp = false;
        }

        if (event.getKey() == down) {
            moveDown = false;
        }
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        if (event.getKey() == up) {
            moveUp = true;
        }

        if (event.getKey() == down) {
            moveDown = true;
        }
    }
}
