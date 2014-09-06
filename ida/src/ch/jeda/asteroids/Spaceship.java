package ch.jeda.asteroids;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Spaceship extends Body {

    private static final double GUN_FIRE_DELAY = 0.2;
    private Sound shootSound = new Sound("res:raw/bullet.wav");
    private RotatedImage thrust;
    private double gunCooldown;
    private boolean thrustOn;
    private boolean shoot;
    private boolean rotateLeft;
    private boolean rotateRight;

    public Spaceship(double x, double y) {
        super(x, y, 0, 0);
        gunCooldown = 0.0;
        thrustOn = false;
        setImage("res:drawable/ship.png", 36);
        thrust = new RotatedImage("res:drawable/thrust.png", 36);
        setRadius(50);
        setDrawOrder(100);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (thrustOn) {
            double x = getX() - 50 * (float) Math.cos(getRotation());
            double y = getY() - 50 * (float) Math.sin(getRotation());
            canvas.drawImage(x, y, thrust.getImage(getRotation()), Alignment.CENTER);
        }
    }

    public void steer(PressedKeys pressedKeys) {
        rotateLeft = pressedKeys.contains(Key.LEFT);
        rotateRight = pressedKeys.contains(Key.RIGHT);
        shoot = pressedKeys.contains(Key.SPACE);
        thrustOn = pressedKeys.contains(Key.UP);
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        super.update(dt, newX, newY);
        for (Asteroid asteroid : getCollidingSprites(Asteroid.class)) {
            getWindow().add(new Explosion(getX(), getY()));
            getWindow().remove(this);
        }

        if (thrustOn) {
            setAcceleration(100 * Math.cos(getRotation()), 100 * Math.sin(getRotation()));
        }
        else {
            setAcceleration(0, 0);
        }

        if (rotateLeft) {
            rotate(-0.1);
        }

        if (rotateRight) {
            rotate(0.1);
        }

        if (gunCooldown > 0) {
            gunCooldown = gunCooldown - dt;
        }
        if (gunCooldown <= 0.0 && shoot) {
            double x = getX() + Math.cos(getRotation()) * 50;
            double y = getY() + Math.sin(getRotation()) * 50;
            Shot bullet = new Shot((int) x, (int) y, this.getRotation());
            shootSound.play();
            getWindow().add(bullet);
            gunCooldown = GUN_FIRE_DELAY;
        }
    }
}
