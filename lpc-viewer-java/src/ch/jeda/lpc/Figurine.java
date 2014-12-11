package ch.jeda.lpc;

import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Sprite;
import ch.jeda.ui.TileSet;
import java.util.ArrayList;
import java.util.List;

public class Figurine extends Sprite {

    private static final int TILE_WIDTH = 64;
    private static final int TILE_HEIGHT = 64;
    private final List<TileSet> layers;
    private Action action;
    private List<String> features;
    private int maxStep;
    private int step;
    private double speed;
    private double nextStep;
    private int direction;

    public Figurine() {
        this.features = new ArrayList<>();
        this.layers = new ArrayList<>();
        this.step = 0;
        this.speed = 12.0;
        this.nextStep = System.currentTimeMillis() + 1000.0 / this.speed;
        this.action = Action.WALK;
        direction = 1;
    }

    public void setAction(final Action action) {
        if (action == null) {
            throw new NullPointerException("action");
        }

        this.action = action;
        this.step = this.action.getFirstStep();
        this.loadTileSet();
    }

    public void addFeature(String feature) {
        this.features.add(feature);
        this.loadTileSet();
    }

    public void clearFeatures() {
        this.features.clear();
        this.loadTileSet();
    }

    @Override
    protected void draw(Canvas canvas) {
        for (TileSet layer : this.layers) {
            canvas.drawImage(getX(), getY(), layer.getTileAt(this.step, this.direction), Alignment.CENTER);
        }
    }

    @Override
    protected void update(double dt, double newX, double newY) {
        final double now = System.currentTimeMillis();
        if (this.nextStep <= now) {
            this.nextStep = now + 1000.0 / this.speed;
            if (this.step < this.action.getLastStep() - 1) {
                ++this.step;
            }
            else if (this.action.getNext() != null) {
                this.setAction(this.action.getNext());
            }
            else {
                this.step = this.action.getFirstStep();
            }
        }

        super.update(dt, newX, newY);
    }

    private void loadTileSet() {
        this.layers.clear();
        for (String feature : this.features) {
            String imagePath = ":drawable/character/" + feature + "/" + this.action.getFileName() + ".png";
            this.layers.add(new TileSet(imagePath, TILE_WIDTH, TILE_HEIGHT));
        }
    }
}
