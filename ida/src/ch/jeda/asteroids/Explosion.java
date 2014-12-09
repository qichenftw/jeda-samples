package ch.jeda.asteroids;

import ch.jeda.ui.Sprite;
import ch.jeda.*;
import ch.jeda.ui.*;

public class Explosion extends Sprite {

//    private static Sound SOUND = new Sound("res:raw/explosion.wav");
    private double age;

    public Explosion(double x, double y) {
        super(x, y);
        age = 0;
//        SOUND.play();
        setDrawOrder(1);
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.setColor(Color.RED);
        canvas.fillCircle(getX(), getY(), getRadius());
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        age = age + dt;
        setRadius((int) (age * 400));
        if (age > 0.5) {
            getView().remove(this);
        }
    }
}
