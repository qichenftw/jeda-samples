package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class SelfMadeButton extends Program implements PointerListener {

    Window fenster;
    int x;
    int y;
    int width;
    int height;
    PointerEvent gedrueckt;
    String text;

    @Override
    public void run() {
        fenster = new Window();
        x = 10;
        y = 10;
        width = 100;
        height = 50;
        text = "Press Me";
        drawButton();
        JedaLogo.draw(fenster);
        fenster.addEventListener(this);
    }

    void drawButton() {
        if (gedrueckt != null) {
            fenster.setColor(Color.RED);
        }
        else {
            fenster.setColor(Color.WHITE);
        }

        fenster.fillRectangle(x, y, width, height);
        fenster.setColor(Color.BLACK);
        fenster.drawRectangle(x, y, width, height);
        fenster.drawText(x + width / 2, y + height / 2, text, Alignment.CENTER);
    }

    void action() {
        fenster.drawText(10, 100, "Button has been pressed");
    }

    boolean isInside(PointerEvent event) {
        return x <= event.getX() && event.getX() <= x + width &&
               y <= event.getY() && event.getY() <= y + height;
    }

    @Override
    public void onPointerDown(PointerEvent event) {
        if (gedrueckt == null && isInside(event)) {
            gedrueckt = event;
            drawButton();
        }
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        if (gedrueckt != null && gedrueckt.getPointerId() == event.getPointerId() && !isInside(event)) {
            gedrueckt = null;
            drawButton();
        }
    }

    @Override
    public void onPointerUp(PointerEvent event) {
        if (gedrueckt != null && gedrueckt.getPointerId() == event.getPointerId()) {
            gedrueckt = null;
            drawButton();
            action();
        }
    }
}
