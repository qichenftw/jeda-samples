package ch.jeda.platformer;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.tiled.*;
import ch.jeda.ui.*;

public class Platformer extends Program implements TickListener, KeyDownListener, KeyUpListener {

    static final double VX = 300;
    static final double VY = 500;
    static final double G = 1000;
    Window fenster;
    boolean links;
    boolean rechts;
    boolean springen;
    // Daten der Spielfigur
    double r; // Radius
    double vx; // horizontale Geschwindigkeit
    double vy; // vertikale Geschwindigkeit
    boolean amBoden; // Ist die Spielfigur am Boden?
    TiledMap level;
    TiledObject player;

    @Override
    public void run() {
        fenster = new Window(1280, 800, WindowFeature.DOUBLE_BUFFERED);
        level = new TiledMap("platformer-1.tmx");
        r = 20;
        vx = 0;
        vy = 0;
        amBoden = true;
        fenster.addEventListener(this);
        player = level.getLayer("Objekte").getObject("Player");
        level.init(fenster);
    }

    @Override
    public void onTick(TickEvent event) {
        steuereSpielfigur();
        bewegeSpielfigur(event.getDuration());
        zeichneHintergrund();
    }

    void steuereSpielfigur() {
        // Die Spielfigur kann nur gesteuert werden, wenn sie sich am Boden befindet
        if (amBoden) {
            if (links) {
                // Falls die 'Links'-Taste gedrückt ist, ist horizontale Geschwindigkeit negativ
                vx = -VX;
            }
            else if (rechts) {
                // Falls die 'Rechts'-Taste gedrückt ist, ist horizontale Geschwindigkeit positiv
                vx = VX;
            }
            else {
                // Ansonsten ist horizontale Geschwindigkeit 0
                vx = 0;
            }

            if (springen) {
                vy = -VY;
                // Nun ist die Spielfigur nicht mehr am Boden
                amBoden = false;
            }
        }
    }

    void bewegeSpielfigur(double dt) {
        double x = player.getCenterX();
        double y = player.getCenterY();
        // Merke alte y-Position
        double oldY = y;

        // Gravitation
        vy = vy + G * dt;
        // Bewege Spielfigur
        x = x + vx * dt;
        y = y + vy * dt;

        // Verhindere, dass Spielfigur über linken Rand hinausläuft.
        if (x < r) {
            x = r;
        }

        // Verhindere, dass Spielfigur über rechten Rand hinausläuft.
        if (x > fenster.getWidth() - r - 1) {
            x = fenster.getWidth() - r - 1;
        }

        // Verhindere, dass Spielfigur durch die Decke springt.
        if (y < r) {
            y = r;
            vy = 0;
        }

        // Verhindere, dass Spielfigur durch den Boden fällt.
        if (y > fenster.getHeight() - r - 1) {
            y = fenster.getHeight() - r - 1;
            vy = 0;
            // Jetzt ist die Spielfigur wieder gelandet
            amBoden = true;
        }

        // Wenn Spielfigur am Fallen ist...
        if (vy > 0) {
            amBoden = false;
        }

        player.setPosition(x, y);
    }

    void zeichneHintergrund() {
        level.drawBackground(fenster);
        fenster.setColor(Color.RED);
        fenster.drawText(10, 10, "x=" + player.getCenterX() + ", y=" + player.getCenterY());
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
