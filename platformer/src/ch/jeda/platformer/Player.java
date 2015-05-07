package ch.jeda.platformer;

import ch.jeda.event.Key;
import ch.jeda.event.KeyDownListener;
import ch.jeda.event.KeyEvent;
import ch.jeda.event.KeyUpListener;
import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;
import ch.jeda.physics.Body;
import ch.jeda.physics.CircleShape;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;

public class Player extends Body implements KeyDownListener, KeyUpListener, TickListener {

    private boolean links;
    private boolean rechts;
    private boolean springen;

    public Player() {
        addShape(new CircleShape(30));
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        updateKey(event.getKey(), true);
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        updateKey(event.getKey(), false);
    }

    @Override
    protected void drawBody(Canvas canvas) {
        canvas.setColor(Color.AQUA);
        canvas.fillCircle(0, 0, 30);
    }

    void updateKey(Key key, boolean pressed) {
        if (key == Key.SPACE) {
            springen = pressed;
        }
        if (key == Key.LEFT) {
            links = pressed;
        }
        if (key == Key.RIGHT) {
            rechts = pressed;
        }
    }

    @Override
    public void onTick(TickEvent te) {
        if (links) {
            applyForce(-500, 0);
        }

        if (rechts) {
            applyForce(500, 0);
        }

        if (springen) {
            applyForce(0, -2000);
        }
    }

}
