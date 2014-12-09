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
import ch.jeda.ui.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class TiledMapData {

    final Color backgroundColor;
    final int height;
    final List<Layer> layers;
    final TiledMapOrientation orientation;
    final Data properties;
    final int tileHeight;
    final int tileWidth;
    final int width;

    TiledMapData(final Data properties, final TiledMapOrientation orientation, final int width,
                 final int height, final int tileWidth, final int tileHeight, final Color backgroundColor,
                 final Collection<Layer> layers) {
        this.backgroundColor = backgroundColor;
        this.height = height;
        this.layers = new ArrayList<Layer>(layers);
        this.orientation = orientation;
        this.properties = properties;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.width = width;
    }
}
