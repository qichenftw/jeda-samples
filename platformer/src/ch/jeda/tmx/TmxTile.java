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
package ch.jeda.tmx;

import ch.jeda.Data;
import ch.jeda.ui.Image;
import java.util.List;

public class TmxTile {

    private final int id;
    private final Image image;
    private final Data properties;
    private final TmxTerrain[] terrain;
    private final TmxTileSet tileSet;

    TmxTile(final TmxTileSet tileSet, final int id, final Image image, final XmlElement element) {
        this.id = id;
        this.image = image;
        this.terrain = new TmxTerrain[4];
        this.tileSet = tileSet;
        if (element != null) {
            this.properties = TmxHelper.parseProperties(element.getChild("properties"));
            TmxHelper.parseTerrain(element.getAttributes().readString("terrain"), tileSet.getTerrains(), this.terrain);
        }
        else {
            this.properties = new Data();
        }
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

    public final TmxTerrain getTerrainBottomLeft() {
        return this.terrain[2];
    }

    public final TmxTerrain getTerrainBottomRight() {
        return this.terrain[3];
    }

    public final TmxTerrain getTerrainTopLeft() {
        return this.terrain[0];
    }

    public final TmxTerrain getTerrainTopRight() {
        return this.terrain[1];
    }

    public final TmxTileSet getTileSet() {
        return this.tileSet;
    }
}
