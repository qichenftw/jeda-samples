package ch.jeda.platformer;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Physics extends Program implements TickListener, KeyDownListener, KeyUpListener {

    Window fenster;
    boolean links;
    boolean rechts;
    boolean springen;
    // Daten der Spielfigur
    World world;
    DynamicBody body;

    @Override
    public void run() {
        fenster = new Window(1280, 800, WindowFeature.DOUBLE_BUFFERED);
        world = new World(fenster);
        world.setGravity(0, 100);
        body = new DynamicBody(100, 100);
        Shape bodyShape = new CircleShape(20);
        bodyShape.setFricition(0.7);
        body.addShape(bodyShape);
        body.addShape(new RectangleShape(80, 10));

        Body ground = new StaticBody(640, 794);
        ground.addShape(new RectangleShape(1280, 10));

        world.add(body);
        world.add(ground);

        world.setPaused(false);
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        fenster.setColor(Color.BLACK);
        fenster.drawText(10, 10, "x= " + body.getX() + ", y=" + body.getY());
        steuereSpielfigur();
    }

    void steuereSpielfigur() {
        if (links) {
            body.applyForce(-50, 0);
        }

        if (rechts) {
            body.applyForce(50, 0);
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
