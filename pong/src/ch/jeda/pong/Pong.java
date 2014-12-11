package ch.jeda.pong;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Pong extends Program implements TickListener {

    Window window;
    double x;
    double y;
    double speed;
    double direction;
    double radius;

    @Override
    public void run() {
        window = new Window();
        x = window.getWidth() / 2;
        y = window.getHeight() / 2;
        speed = 500;
        direction = Math.PI / 2;
        radius = 10;
        window.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        window.setColor(Color.BLACK);
        window.fill();
        window.setColor(Color.WHITE);
        window.fillCircle(x, y, radius);

        x = x + event.getDuration() * speed * Math.cos(direction);
        y = y + event.getDuration() * speed * Math.sin(direction);

        if (y < radius) {
            y = radius;
            direction = -direction;
        }

        if (y > window.getHeight() - radius) {
            y = window.getHeight() - radius;
            direction = -direction;
        }
    }
}
