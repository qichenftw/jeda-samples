/*
 * Copyright (C) 2014 by Stefan Rothe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY); without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.jeda.tiled;

import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Element;
import ch.jeda.ui.Image;

public class TiledObject extends Element {

    private String type;
    private double x;
    private double y;
    private double width;
    private double height;
    private double rotation;
    private Tile tile;
    private Image image;
    ObjectLayer layer;

    TiledObject(final String name, final String type, final double x, final double y, final double width,
                final double height, final double rotation, final Tile tile, final boolean visible) {
        super(name);
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.tile = tile;
        if (this.tile != null) {
            this.image = this.tile.getImage();
        }
    }

    public int getHeight() {
        if (this.image != null) {
            return this.image.getHeight();
        }
        else {
            return 0;
        }
    }

    public String getType() {
        return this.type;
    }

    public int getWidth() {
        if (this.image != null) {
            return this.image.getWidth();
        }
        else {
            return 0;
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setPosition(final double x, final double y) {
        final int hw = this.getWidth() / 2;
        final int hh = this.getHeight() / 2;
        this.x = x;
        this.y = y;
        if (this.x < hw) {
            this.x = hw;
        }

        if (this.y < hh) {
            this.y = hh;
        }
    }

    public void setRotation(final double rotation) {
        this.rotation = rotation;
    }

    @Override
    protected void draw(final Canvas canvas) {
        canvas.setTranslation(this.x + this.layer.getMap().getOffsetX(), this.y + this.layer.getMap().getOffsetY());
        canvas.setRotation(this.rotation);
        canvas.drawImage(0, 0, this.image, Alignment.CENTER);
        canvas.resetTransformations();
    }
}
