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
import ch.jeda.ui.Image;

public class Tile {

    private final int id;
    private final Image image;
    private final Data properties;
    private final Terrain[] terrain;

    Tile(final Data properties, final int id, final Image image, final Terrain[] terrain) {
        this.id = id;
        this.image = image;
        this.properties = properties;
        this.terrain = terrain;
    }

    public final int getId() {
        return this.id;
    }

    public final Image getImage() {
        return this.image;
    }

    public final Data getProperties() {
        return this.properties;
    }

    public final Terrain getTerrainBottomLeft() {
        return this.terrain[2];
    }

    public final Terrain getTerrainBottomRight() {
        return this.terrain[3];
    }

    public final Terrain getTerrainTopLeft() {
        return this.terrain[0];
    }

    public final Terrain getTerrainTopRight() {
        return this.terrain[1];
    }
}
