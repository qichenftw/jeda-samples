package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class TileBasedAnimation extends Program implements TickListener, KeyDownListener {

    AnimatedImage open;
    AnimatedImage close;
    Window window;
    boolean doOpen;
    int i;

    @Override
    public void run() {
        window = new Window();
        close = new AnimatedImage("res:drawable/shrine_friendly_close.png", 151);
        open = new AnimatedImage("res:drawable/shrine_friendly_open.png", 151);
        window.addEventListener(open);
        window.addEventListener(close);
        window.addEventListener(this);
        doOpen = true;
    }

    @Override
    public void onTick(TickEvent event) {
        window.fill();
        if (doOpen) {
            window.drawImage(10, 10, open.getImage());
        }
        else {
            window.drawImage(10, 10, close.getImage());
        }
    }

    @Override
    public void onKeyDown(KeyEvent ke) {
        doOpen = !doOpen;
        if (doOpen) {
            open.start();
        }
        else {
            close.start();
        }
    }

}
