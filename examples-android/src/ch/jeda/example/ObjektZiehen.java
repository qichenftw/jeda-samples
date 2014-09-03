package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class ObjektZiehen extends Program implements PointerListener {

    PointerEvent anfang;
    Window fenster;
    int radius;
    float x;
    float y;

    @Override
    public void run() {
        fenster = new Window();
        fenster.addEventListener(this);
        radius = 50;
        x = fenster.getWidth() / 2;
        y = fenster.getHeight() / 2;
        zeichne();
    }

    @Override
    public void onPointerDown(PointerEvent event) {
        if (anfang == null) {
            float dx = event.getX() - this.x;
            float dy = event.getY() - this.y;
            if (dx * dx + dy * dy < radius * radius) {
                anfang = event;
            }

            zeichne();
        }
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        if (anfang != null && anfang.getPointerId() == event.getPointerId()) {
            x = x + event.getX() - anfang.getX();
            y = y + event.getY() - anfang.getY();
            if (x < 0) {
                x = 0;
            }

            if (x > fenster.getWidth()) {
                x = fenster.getWidth();
            }

            if (y < 0) {
                y = 0;
            }

            if (y > fenster.getHeight()) {
                y = fenster.getHeight();
            }

            anfang = event;
            zeichne();
        }
    }

    @Override
    public void onPointerUp(PointerEvent event) {
        if (anfang != null && anfang.getPointerId() == event.getPointerId()) {
            anfang = null;
        }
    }

    void zeichne() {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        JedaLogo.draw(fenster);
        fenster.setColor(Color.BLACK);
        fenster.fillCircle(x, y, radius);
        fenster.setColor(Color.WHITE);
        fenster.drawText(x, y, "Drag me", Alignment.CENTER);
    }
}
