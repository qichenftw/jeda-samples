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

import ch.jeda.ui.Image;
import ch.jeda.ui.Shape;

public class TiledObject extends Shape {

    private final String name;
    private final Tile tile;
    private final String type;
    private final boolean visible;

    TiledObject(final String name, final String type, final boolean visible, final int x, final int y,
                final Tile tile) {
        super(x, y);
        this.name = name;
        this.tile = tile;
        this.type = type;
        this.visible = visible;
        if (this.tile != null) {
            final Image image = this.tile.getImage();
            // Position is lower left corner of object.
            this.move(image.getWidth() / 2, -image.getHeight() / 2);
            this.setImage(image);
        }
    }

    public final String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public boolean isVisible() {
        return this.visible;
    }
}
