package ch.jeda.pong;

import ch.jeda.ui.Image;
import ch.jeda.ui.Sprite;

/**
 * Diese Klasse stellt einen Schläger dar.
 */
public class Paddle extends Sprite {

    private static final double MAX_SPEED = 500;
    private PaddleControl control;

    public Paddle(int x, int y, PaddleControl control) {
        super(x, y);
        Image image = new Image("res:drawable/paddle_blue.png");
        setImage(image);
        setRadius(image.getHeight() / 2);
        this.control = control;
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        if (control.shouldMove()) {
            // Berechne, um wieviel Pixel sich der Schläger maximal bewegt
            double dy = dt * MAX_SPEED;
            // Distanz zwischen gewünschter und aktueller Position berechnen
            double delta = control.getTargetPos() - newY;
            // Wenn die Distanz kleiner als die maximale Bewegung ist, ...
            if (Math.abs(delta) < dy) {
                // ... Schläger auf die Zeigerposition setzen
                newY = control.getTargetPos();
            }
            else {
                // ansonsten um maximale Geschwindigkeit in Richtung Zeiger bewegen.
                newY = newY + dy * Math.signum(delta);
            }
        }

        // Nicht über den Rand hinaus bewegen
        newY = Math.min(getWindow().getHeight() - getRadius(), Math.max(getRadius(), newY));

        super.update(dt, newX, newY);
    }

}
