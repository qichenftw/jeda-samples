package ch.jeda.platformer;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class PhysicsSample extends Program implements TickListener, KeyDownListener, KeyUpListener {

    Window fenster;
    boolean links;
    boolean rechts;
    boolean springen;
    // Daten der Spielfigur
    Physics physics;
    Body body;

    @Override
    public void run() {
        fenster = new Window(1280, 800, WindowFeature.DOUBLE_BUFFERED);
        physics = new Physics(fenster);
        physics.setScale(100);
        physics.setGravity(0, 10);

        for (int i = 0; i < 50; ++i) {
            physics.createDynamicBody(100 + Math.random() * 500, 100 + Math.random() * 500).addShape(new RectangleShape(20, 20));
        }

        body = physics.createDynamicBody(100, 100);
        body.addShape(new CircleShape(35));
        body.setImage("res:drawable/alien_beige.png");
//        body.addShape(new RectangleShape(80, 10));

        physics.createStaticBody(640, 794).addShape(new RectangleShape(1280, 10));

        physics.setPaused(false);
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        fenster.setColor(Color.BLACK);
        steuereSpielfigur();
    }

    void steuereSpielfigur() {
        if (links) {
            body.applyForce(-500, 0);
        }

        if (rechts) {
            body.applyForce(500, 0);
        }

        if (springen) {
            body.applyForce(0, -2000);
        }
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        updateKey(event.getKey(), true);
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        updateKey(event.getKey(), false);
    }

    void updateKey(Key key, boolean pressed) {
        if (key == Key.SPACE) {
            springen = pressed;
        }
        if (key == Key.LEFT) {
            links = pressed;
        }
        if (key == Key.RIGHT) {
            rechts = pressed;
        }
    }
}
