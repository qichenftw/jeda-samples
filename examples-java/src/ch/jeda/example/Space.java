package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Space extends Program implements TickListener {

    Window window;
    Ship ship;
    Image background;

    @Override
    public void run() {
        window = new Window(WindowFeature.DOUBLE_BUFFERED);
        ship = new Ship(window.getWidth() / 2, window.getHeight() / 2);
        window.add(ship);
        background = new Image("res:drawable/space.jpg");
        window.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        window.drawImage(0, 0, background);
    }
}

class Ship extends TopDownSprite implements KeyDownListener, KeyUpListener {

    private boolean turnLeft;
    private boolean turnRight;
    private boolean thrustOn;
    private Image thrust;

    public Ship(int x, int y) {
        setImage("res:drawable/ship.png");
        thrust = new Image("res:drawable/thrust.png");
        setPosition(x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (thrustOn) {
            canvas.drawImage(-50, 0, thrust, Alignment.CENTER);
        }
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        if (turnLeft) {
            rotate(-0.1);
        }

        if (turnRight) {
            rotate(0.1);
        }

        if (thrustOn) {
            setAcceleration(100 * Math.cos(getRotation()), 100 * Math.sin(getRotation()));
        }
        else {
            setAcceleration(0, 0);
        }

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

        super.update(dt, newX, newY);
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        switch (event.getKey()) {
            case UP:
                thrustOn = true;
                break;
            case LEFT:
                turnLeft = true;
                break;
            case RIGHT:
                turnRight = true;
                break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        switch (event.getKey()) {
            case UP:
                thrustOn = false;
                break;
            case LEFT:
                turnLeft = false;
                break;
            case RIGHT:
                turnRight = false;
                break;
        }
    }
}
