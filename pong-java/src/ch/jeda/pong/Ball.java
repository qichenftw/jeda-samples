package ch.jeda.pong;

import ch.jeda.Convert;
import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Image;
import ch.jeda.ui.Sprite;
import ch.jeda.ui.Window;

public class Ball extends Sprite {

    private double serveTimeout;

    public Ball(Window window) {
        super(window.getWidth() / 2, window.getHeight() / 2);
        serveTimeout = System.currentTimeMillis() + 3000;
        setSpeed(0);
        Image image = new Image("res:drawable/ball_grey.png");
        setImage(image);
        setRadius(image.getWidth() / 2.0);
        setDirectionRight();
    }

    /**
     * Setze den Ball in die Mitte des Spielfelds für ein Anspiel.
     */
    public void reset() {
        setPosition(getWindow().getWidth() / 2, getWindow().getHeight() / 2);
        serveTimeout = System.currentTimeMillis() + 3000;
        setSpeed(0);
    }

    /**
     * Setze die Richtung mit einem zufällig variierenden Winkel nach links für ein Anspiel vom rechten Spieler.
     */
    public void setDirectionLeft() {
        setDirection((0.5 * Math.random() + 0.75) * Math.PI);
    }

    /**
     * Setze die Richtung mit einem zufällig variierenden Winkel nach links für ein Anspiel vom linken Spieler.
     */
    public void setDirectionRight() {
        setDirection((0.5 * Math.random() - 0.25) * Math.PI);
    }

    @Override
    protected void draw(Canvas canvas) {
        super.draw(canvas);
        double timeToServe = serveTimeout - System.currentTimeMillis();
        if (timeToServe > 0) {
            canvas.setFontSize(20);
            canvas.setColor(Color.WHITE);
            canvas.drawText(getX(), getY() - getRadius() - 5, Convert.toString(1 + (int) timeToServe / 1000), Alignment.BOTTOM_CENTER);
        }
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        if (System.currentTimeMillis() > serveTimeout) {
            setSpeed(500);
        }

        // Abprallen am Schläger
        for (Paddle paddle : getCollidingSprites(Paddle.class)) {
            if (0.5 * Math.PI < getDirection() && getDirection() < 1.5 * Math.PI) {
                newX = paddle.getX() + 15 + getRadius();
            }
            else {
                newX = paddle.getX() - 15 - getRadius();
            }

            setDirection(Math.PI - getDirection());
        }

        // Abprallen am oberen Rand
        // Wenn die y-Koordinate kleiner als der Radius ist,
        // hat der Ball den Rand erreicht.
        {
            if (newY < getRadius()) {
                setDirection(-getDirection());
                // Den Ball aufs Spielfeld zurück setzen, damit er sich nicht am
                // Rand verfängt.
                newY = getRadius();
            }
        }

        // Abprallen am unteren Rand
        // Wenn die y-Koordinate grösser als die Höhe des Spielfelds minus
        // Radius ist, hat der Ball den Rand erreicht.
        if (newY > getWindow().getHeight() - getRadius()) {
            setDirection(-getDirection());
            // Den Ball aufs Spielfeld zurück setzen, damit er sich nicht am
            // Rand verfängt.
            newY = getWindow().getHeight() - getRadius();
        }

        super.update(dt, newX, newY);
    }

}
