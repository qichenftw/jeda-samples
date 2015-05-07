package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Race extends Program {

    View view;
    Sprite ship;
    Image background;

    @Override
    public void run() {
        view = new View();
        ship = new Car(view.getWidth() / 2, view.getHeight() / 2);
        view.add(ship);
        view.getBackground().setColor(Color.BLACK);
        view.getBackground().fill();
        view.addEventListener(this);
    }
}

class Car extends Sprite implements KeyDownListener, KeyUpListener {

    private boolean turnLeft;
    private boolean turnRight;
    private boolean gas;
    private boolean brake;

    public Car(int x, int y) {
        setImage("res:drawable/racer_green.png");
        setPosition(x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.setColor(Color.WHITE);
        canvas.drawText(10, 10, "v = " + getSpeed());
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        if (turnLeft) {
            rotate(-0.02);
            turn(-0.02);
        }

        if (turnRight) {
            rotate(0.02);
            turn(0.02);
        }

        if (gas) {
            setSpeed(Math.min(500, getSpeed() + 2));
        }
        else if (brake) {
            setSpeed(Math.max(0, getSpeed() - 2));
        }

        // Raum geht am Bildschirmrand auf der gegenüberliegenden Seite weiter
        if (newX < 0) {
            newX = getView().getWidth();
        }

        if (newY < 0) {
            newY = 0;
        }

        if (newX > getView().getWidth()) {
            newX = 0;
        }

        if (newY > getView().getHeight()) {
            newY = 0;
        }

        super.update(dt, newX, newY);
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        switch (event.getKey()) {
            case UP:
                gas = true;
                break;
            case DOWN:
                brake = true;
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
                gas = false;
                break;
            case DOWN:
                brake = false;
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
