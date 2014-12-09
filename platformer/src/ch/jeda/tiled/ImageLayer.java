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

import ch.jeda.Data;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Image;

public final class ImageLayer extends Layer {

    private final Image image;

    public ImageLayer(final Data properties, final String name, final double opacity, final boolean visible,
                      final Image image) {
        super(properties, name, opacity, visible);
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    @Override
    public TiledObject getObject(final String name) {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(this.getX(), this.getY(), this.image, (int) (this.getOpacity() * 255));
    }
}
