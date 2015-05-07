package ch.jeda.asteroids;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Explosion extends Element implements TickListener {

//    private static Sound SOUND = new Sound("res:raw/explosion.wav");
    private double age;
    private double x;
    private double y;
    private double radius;

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
        age = 0;
//        SOUND.play();
        setDrawOrder(1);
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.setColor(Color.RED);
        canvas.fillCircle(x, y, age * 400);
    }

    @Override
    public void onTick(TickEvent event) {
        age = age + event.getDuration();
        if (age > 0.5) {
            getView().remove(this);
        }
    }
}
