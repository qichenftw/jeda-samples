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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TileSet {

    private final String name;
    private final Data properties;
    private final int tileHeight;
    private final int tileOffsetX;
    private final int tileOffsety;
    private final List<Tile> tiles;
    private final int tileWidth;

    public TileSet(final Data properties, final String name, final int tileWidth, final int tileHeight,
                   final int tileOffsetX, final int tileOffsetY, final Collection<Tile> tiles) {
        this.name = name;
        this.properties = properties;
        this.tileHeight = tileHeight;
        this.tileOffsetX = tileOffsetX;
        this.tileOffsety = tileOffsetY;
        this.tiles = new ArrayList<Tile>(tiles);
        this.tileWidth = tileWidth;
    }

    public String getName() {
        return this.name;
    }

    public final Data getProperties() {
        return this.properties;
    }

    public Tile getTile(int index) {
        if (0 <= index && index < this.tiles.size()) {
            return this.tiles.get(index);
        }
        else {
            return null;
        }
    }
}
