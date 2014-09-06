package ch.jeda.asteroids;

import ch.jeda.ui.Sprite;
import ch.jeda.*;
import ch.jeda.ui.*;

public abstract class Body extends Sprite {

    private static int COUNT = 0;
    int left;
    int top;
    int width;
    int height;

    protected Body(double x, double y, double speed, double direction) {
        super(x, y, speed, direction);
    }

    public void draw(Canvas canvas, boolean debug) {
        if (debug) {
            canvas.setColor(Color.RED);
            canvas.drawCircle(getX(), getY(), getRadius());
            canvas.drawRectangle(left, top, width, height);
        }
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        // Raum geht am Bildschirmrand auf der gegenüberliegenden Seite weiter
        if (newX < 0) {
            newX = getWindow().getWidth();
        }

        if (newY < 0) {
            newY = getWindow().getHeight();
        }

        if (newX > getWindow().getWidth()) {
            newX = 0;
        }

        if (newY > getWindow().getHeight()) {
            newY = 0;
        }

        setPosition(newX, newY);
    }
}
