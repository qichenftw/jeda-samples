package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class ObjektZiehen extends Program implements PointerListener {

    PointerEvent dragStart;
    Window fenster;
    int radius;
    int x;
    int y;

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
        if (dragStart == null) {
            float dx = event.getX() - this.x;
            float dy = event.getY() - this.y;
            if (dx * dx + dy * dy < radius * radius) {
                dragStart = event;
            }

            zeichne();
        }
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        if (dragStart != null && dragStart.getPointerId() == event.getPointerId()) {
            x = x + event.getX() - dragStart.getX();
            y = y + event.getY() - dragStart.getY();
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

            dragStart = event;
            zeichne();
        }
    }

    @Override
    public void onPointerUp(PointerEvent event) {
        if (dragStart != null && dragStart.getPointerId() == event.getPointerId()) {
            dragStart = null;
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
