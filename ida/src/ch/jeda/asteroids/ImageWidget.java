package ch.jeda.asteroids;

import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Image;
import ch.jeda.ui.Widget;

public class ImageWidget extends Widget {

    private Image image;

    public ImageWidget(final int x, final int y, final String imagePath) {
        this(x, y, new Image(imagePath), Alignment.TOP_LEFT);
    }

    public ImageWidget(final int x, final int y, final Image image) {
        this(x, y, image, Alignment.TOP_LEFT);
    }

    public ImageWidget(final int x, final int y, final String imagePath, final Alignment alignment) {
        this(x, y, new Image(imagePath), alignment);
    }

    public ImageWidget(final int x, final int y, final Image image, final Alignment alignment) {
        super(x, y, alignment);
        if (image == null) {
            throw new NullPointerException("image");
        }

        this.image = image;
        this.setDrawOrder(-1);
    }

    @Override
    public boolean contains(final int x, final int y) {
        final int dx = this.getCenterX() - x;
        final int dy = this.getCenterY() - y;
        return Math.abs(dx) <= this.getWidth() / 2 && Math.abs(dy) <= this.getHeight() / 2;
    }

    @Override
    public int getHeight() {
        return this.image.getHeight();
    }

    @Override
    public int getWidth() {
        return this.image.getWidth();
    }

    public final void setImage(final Image image) {
        if (image == null) {
            throw new NullPointerException("image");
        }

        this.image = image;
    }

    @Override
    protected void draw(final Canvas canvas) {
        canvas.drawImage(this.getX(), this.getY(), this.image, this.getAlignment());
    }
}
