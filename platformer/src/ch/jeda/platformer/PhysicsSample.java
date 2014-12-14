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
    TmxPhysics physics;
    Body player;
    ContactEvent contact;

    @Override
    public void run() {
        Jeda.setTickFrequency(60);
        fenster = new Window(1400, 700, WindowFeature.DOUBLE_BUFFERED);
        physics = new TmxPhysics(fenster);
        physics.setScale(100);
        physics.setGravity(0, 10);

        physics.loadMap("res:level-1.tmx");

        player = physics.getBody("player");
        fenster.addEventListener(this);
        physics.setPaused(false);
    }

    @Override
    public void onTick(TickEvent event) {
        if (this.contact != null && this.contact.involves(player)) {
            fenster.setColor(Color.RED);
            fenster.drawText(10, 10, "contact!");
            this.contact = null;
        }

        for (final Body body : physics.getBodies()) {
            if (body != player && !body.isStatic()) {
                if (body.getFatigue() > 50) {
                }

                if (body.getFatigue() > 50) {
                    body.destroy();
                }
            }

        }
        steuereSpielfigur();
    }

    void steuereSpielfigur() {
        if (links) {
            player.applyForce(-500, 0);
        }

        if (rechts) {
            player.applyForce(500, 0);
        }

        if (springen) {
            player.applyForce(0, -2000);
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
