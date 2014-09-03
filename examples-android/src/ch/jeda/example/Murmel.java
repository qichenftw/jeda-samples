package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Murmel extends Program implements SensorListener, TickListener {

    double x;
    double y;
    double vx;
    double vy;
    double ax;
    double ay;
    Window fenster;
    int r;
    Image murmel;

    @Override
    public void run() {
        fenster = new Window();
        r = 20;
        x = fenster.getWidth() / 2;
        y = fenster.getHeight() / 2;
        Jeda.enableSensor(SensorType.ACCELERATION);
        murmel = erstelleMurmel();
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        vx = vx + ax * event.getDuration();
        vy = vy + ay * event.getDuration();
        x = x + vx * event.getDuration();
        y = y + vy * event.getDuration();
        if (x < r) {
            x = r;
            vx = 0;
        }

        if (y < r) {
            y = r;
            vy = 0;
        }

        if (x > fenster.getWidth() - r) {
            x = fenster.getWidth() - r;
            vx = 0;
        }

        if (y > fenster.getHeight() - r) {
            y = fenster.getHeight() - r;
            vy = 0;
        }
        // Hintergrund zeichnen
        fenster.setColor(Color.GRAY);
        fenster.fill();
        JedaLogo.draw(fenster);
        // Ball zeichnen
        fenster.drawImage(x, y, murmel, Alignment.CENTER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ax = event.getX() * 500;
        ay = event.getY() * 500;
    }

    Image erstelleMurmel() {
        Canvas leinwand = new Canvas(2 * r, 2 * r);
        int f = 4;
        int b = 60;
        for (int i = 0; i <= r; ++i) {
            leinwand.setColor(new Color(b + f * i, b + 2 * f * i, b + f * i));
            leinwand.fillCircle(r + i / 4, r - i / 4, r - i);
        }

        return leinwand.takeSnapshot();
    }
}
