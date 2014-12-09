package ch.jeda.asteroids;

import ch.jeda.ui.Image;

public class Shot extends Body {

    private double age;

    public Shot(int x, int y, double direction) {
        super(x, y, 200, direction);
        Image image = new Image("res:drawable/laser.png");
        setImage(image);
        setRadius(image.getWidth() / 2 - 10);
        setDrawOrder(100);
        age = 0;
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        super.update(dt, newX, newY);
        for (Asteroid asteroid : getCollidingSprites(Asteroid.class)) {
            asteroid.hit();
            getView().remove(this);
        }

        age = age + dt;
        if (age > 2.0) {
            getView().remove(this);
        }

    }
}
