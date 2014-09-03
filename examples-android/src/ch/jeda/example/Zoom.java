package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Zoom extends Program implements PointerDownListener, PointerMovedListener, PointerUpListener {

    Window fenster;
    PointerEvent finger1;
    PointerEvent finger2;
    double fingerDistanz;
    int cx;
    int cy;
    double seitenlaenge;

    @Override
    public void run() {
        fenster = new Window();
        cx = fenster.getWidth() / 2;
        cy = fenster.getHeight() / 2;
        seitenlaenge = 50;
        zeichnen();
        fenster.addEventListener(this);
    }

    public void onPointerDown(PointerEvent event) {
        if (finger1 == null) {
            finger1 = event;
        }
        else if (finger2 == null) {
            finger2 = event;
            fingerDistanz = aktuelleDistanz();
        }
    }

    public void onPointerMoved(PointerEvent event) {
        if (finger1 != null && finger1.getPointerId() == event.getPointerId()) {
            finger1 = event;
            zoomen();
        }

        if (finger2 != null && finger2.getPointerId() == event.getPointerId()) {
            finger2 = event;
            zoomen();
        }
    }

    public void onPointerUp(PointerEvent event) {
        if (finger1 != null && finger1.getPointerId() == event.getPointerId()) {
            finger1 = null;
        }

        if (finger2 != null && finger2.getPointerId() == event.getPointerId()) {
            finger2 = null;
        }
    }

    double aktuelleDistanz() {
        int dx = finger1.getX() - finger2.getX();
        int dy = finger1.getY() - finger2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    void zoomen() {
        if (finger1 != null && finger2 != null) {
            double d = aktuelleDistanz();
            seitenlaenge = seitenlaenge + d - fingerDistanz;
            fingerDistanz = d;
            zeichnen();
        }
    }

    void zeichnen() {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        fenster.setColor(Color.JEDA);
        fenster.fillRectangle(cx - seitenlaenge / 2, cy - seitenlaenge / 2, seitenlaenge, seitenlaenge);
        JedaLogo.draw(fenster);
    }
}
