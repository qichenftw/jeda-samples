package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Zeichnen extends Program implements PointerMovedListener {

    Window fenster;

    @Override
    public void run() {
        fenster = new Window();
        fenster.addEventListener(this);
        JedaLogo.draw(fenster);
    }

    public void onPointerMoved(PointerEvent event) {
        fenster.fillCircle(event.getX(), event.getY(), 20);
    }
}
