package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class SierpinskiTeppich extends Program {

    Window fenster;

    @Override
    public void run() {
        fenster = new Window();
        int w = fenster.getWidth();
        int h = fenster.getHeight();

        int s = Math.min(h, w);

        int x = (w - s) / 2;
        int y = (h - s) / 2;
        zeichneQuadrat(x, y, s);
        fenster.setColor(Color.WHITE);
        zeichneTeppich(x + s / 3, y + s / 3, s / 3);
        JedaLogo.draw(fenster);
    }

    void zeichneTeppich(int x, int y, int s) {
        this.zeichneQuadrat(x, y, s);

        x = x - s * 2 / 3;
        y = y - s * 2 / 3;

        if (s > 3) {
            zeichneTeppich(x, y, s / 3);
            zeichneTeppich(x + s, y, s / 3);
            zeichneTeppich(x + 2 * s, y, s / 3);
            zeichneTeppich(x, y + s, s / 3);
            zeichneTeppich(x + 2 * s, y + s, s / 3);
            zeichneTeppich(x, y + 2 * s, s / 3);
            zeichneTeppich(x + s, y + 2 * s, s / 3);
            zeichneTeppich(x + 2 * s, y + 2 * s, s / 3);
        }
    }

    void zeichneQuadrat(int x, int y, int s) {
        fenster.fillRectangle(x, y, s, s);
    }
}
