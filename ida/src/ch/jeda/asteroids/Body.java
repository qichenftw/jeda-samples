package ch.jeda.asteroids;

import ch.jeda.*;
import ch.jeda.ui.*;

public abstract class Body extends Element {

    private static int COUNT = 0;
    private double x;
    private double y;
    int left;
    int top;
    int width;
    int height;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    protected Body(double x, double y, double speed, double direction) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.setColor(Color.RED);
        canvas.drawCircle(getX(), getY(), getRadius());
        canvas.drawRectangle(left, top, width, height);
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        // Raum geht am Bildschirmrand auf der gegenüberliegenden Seite weiter
        if (newX < 0) {
            newX = getView().getWidth();
        }

        if (newY < 0) {
            newY = getView().getHeight();
        }

        if (newX > getView().getWidth()) {
            newX = 0;
        }

        if (newY > getView().getHeight()) {
            newY = 0;
        }

        setPosition(newX, newY);
    }
}
