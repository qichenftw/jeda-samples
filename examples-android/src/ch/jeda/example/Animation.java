package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Animation extends Program implements TickListener {

    double x;
    double y;
    double vy;
    int boden;
    Window fenster;

    @Override
    public void run() {
        fenster = new Window();
        boden = fenster.getHeight();
        x = fenster.getWidth() / 2;
        y = boden / 3;
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        // Berechnungen für die Bewegung des Balls.
        vy = vy + 30;
        y = y + vy * event.getDuration();
        if (y > boden - 20) {
            y = boden - 20;
            vy = -vy;
        }

        // Hintergrund zeichnen
        fenster.setColor(Color.WHITE);
        fenster.fill();
        JedaLogo.draw(fenster);
        // Ball zeichnen
        fenster.setColor(Color.BLACK);
        fenster.fillCircle(x, y, 20);
        // Informationen ausgeben
        fenster.drawText(10, 10, "FPS: " + event.getFrameRate());
    }
}
