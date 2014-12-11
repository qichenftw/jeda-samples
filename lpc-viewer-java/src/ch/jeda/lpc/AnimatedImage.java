package ch.jeda.lpc;

import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;
import ch.jeda.ui.Image;
import ch.jeda.ui.TileSet;

public class AnimatedImage implements TickListener {

    private final TileSet tileSet;
    private int step;
    private double speed;
    private double nextStep;

    public AnimatedImage(final String imagePath, final int tileWidth) {
        final Image image = new Image(imagePath);
        this.tileSet = new TileSet(image, tileWidth, image.getHeight());
        this.step = 0;
        this.speed = 50.0;
        this.nextStep = System.currentTimeMillis() + 1000.0 / this.speed;
    }

    public void start() {
        this.step = 0;
        this.nextStep = System.currentTimeMillis() + 1000.0 / this.speed;
    }

    public Image getImage() {
        return this.tileSet.getTileAt(this.step, 0);
    }

    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void onTick(final TickEvent event) {
        final double now = System.currentTimeMillis();
        if (this.nextStep <= now) {
            this.nextStep = now + 1000.0 / this.speed;
            if (this.step < this.tileSet.getWidth() - 1) {
                ++this.step;
            }
        }
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

}
