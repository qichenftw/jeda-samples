package ch.jeda.platformer;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.physics.Body;
import ch.jeda.physics.BodyType;
import ch.jeda.physics.ContactEvent;
import ch.jeda.physics.Physics;
import ch.jeda.physics.RectangleShape;
import ch.jeda.ui.*;

public class PhysicsSample extends Program implements TickListener {

    View fenster;
    Physics physics;
    // Daten der Spielfigur
    Player player;
    ContactEvent contact;

    @Override
    public void run() {
        Jeda.setTickFrequency(60);
        fenster = new View(1400, 700, ViewFeature.DOUBLE_BUFFERED);
        physics = new Physics();
        physics.setScale(100);
        physics.setGravity(0, 10);
        physics.setDebugging(true);

        player = new Player();
        player.setPosition(50, 50);

        physics.add(player);
        fenster.add(player);

        Body ground = new Body();
        ground.setType(BodyType.STATIC);
        ground.setPosition(0, fenster.getHeight() - 20);
        ground.addShape(new RectangleShape(fenster.getWidth(), 10, 10.0));
        physics.add(ground);
        fenster.add(ground);
//        physics.loadMap("res:level-1.tmx");
        fenster.addEventListener(this);
        physics.setPaused(false);
    }

    @Override
    public void onTick(TickEvent event) {
//        fenster.setColor(Color.WHITE);
//        fenster.fill();
        physics.step(event.getDuration());
//        if (this.contact != null && this.contact.involves(player)) {
//            fenster.setColor(Color.RED);
//            fenster.drawText(10, 10, "contact!");
//            this.contact = null;
//        }
    }

}
