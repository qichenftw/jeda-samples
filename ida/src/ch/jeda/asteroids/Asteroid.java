package ch.jeda.asteroids;

import ch.jeda.*;

public class Asteroid extends Body {

//    private static Sound BREAK_SOUND = new Sound("res:raw/explosion.wav");
    private static int[] RADII = {22, 45, 100};
    private Game game;
    private int size;
    private int stability;

    public Asteroid(Game game, double x, double y) {
        this(game, x, y, 30, Math.random() * Math.PI * 2, 2);
    }

    public Asteroid(Game game, double x, double y, double speed, double direction, int size) {
        super(x, y, 0, direction);
        this.game = game;
        stability = 30 * (size + 1);
        this.size = size;
        setImage("res:drawable/asteroid-" + size + ".png");
        setRadius(RADII[size]);
    }

    public void hit() {
        stability = stability - 5;
        if (stability <= 0) {
            breakUp();
        }
    }

    private void breakUp() {
//        BREAK_SOUND.play();
        game.addScore(size + 1);
        if (size > 0) {
            double dir = getDirection() + Math.random() * Math.PI / 2.0 - Math.PI / 4.0;
            double speed = getSpeed() * 1.2f;
            getView().add(new Asteroid(game, getX(), getY(), speed, dir + Math.PI / 4, size - 1));
            getView().add(new Asteroid(game, getX(), getY(), speed, dir - Math.PI / 4, size - 1));
        }

        getView().remove(this);
    }
}
