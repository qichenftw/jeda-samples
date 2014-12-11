package ch.jeda.platformer;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.tiled.*;
import ch.jeda.ui.*;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

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
    World world;
    Body body;

    @Override
    public void run() {
        fenster = new Window(1280, 800, WindowFeature.DOUBLE_BUFFERED);
        level = new TiledMap("platformer-1.tmx");
        r = 20;
        vx = 0;
        vy = 0;
        amBoden = true;
        player = level.getLayer("Objekte").getObject("Player");
        fenster.add(level);
        level.setScrollLock(player);
        initBody();
        fenster.addEventListener(this);
    }

    void initBody() {
        world = new World(new Vec2(0.0f, 10.0f));

        BodyDef bd = new BodyDef();
        bd.position.set((float) player.getX(), (float) player.getY());
        bd.type = BodyType.DYNAMIC;

        CircleShape cs = new CircleShape();
        cs.m_radius = 0.5f;

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;

        body = world.createBody(bd);
        body.createFixture(fd);

    }

    @Override
    public void onTick(TickEvent event) {
        steuereSpielfigur();
        world.step((float) event.getDuration(), 6, 2);
        Vec2 pos = body.getPosition();
        player.setPosition(pos.x, pos.y);
        //bewegeSpielfigur(event.getDuration());
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
        double x = player.getX();
        double y = player.getY();

        // Gravitation
        vy = vy + G * dt;
        // Bewege Spielfigur
        x = x + vx * dt;
        y = y + vy * dt;

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
