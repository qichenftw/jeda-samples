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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TmxTileSet {

    private final int firstGlobalId;
    private final Image image;
    private final String name;
    private final Data properties;
    private final TmxTerrain[] terrainTypes;
    private final int tileHeight;
    private final int tileOffsetX;
    private final int tileOffsetY;
    private final List<TmxTile> tiles;
    private final int tileWidth;

    TmxTileSet(final XmlElement element, final XmlReader reader) {
        final Data attributes = element.getAttributes();
        this.firstGlobalId = attributes.readInt("firstgid");
        this.name = attributes.readString("name");
        this.tileHeight = attributes.readInt("tileheight");
        this.tileWidth = attributes.readInt("tilewidth");

        // Read tile offset
        final XmlElement tileOffsetElement = element.getChild("tileoffset");
        if (tileOffsetElement == null) {
            this.tileOffsetX = 0;
            this.tileOffsetY = 0;
        }
        else {
            this.tileOffsetX = tileOffsetElement.getAttributes().readInt("x");
            this.tileOffsetY = tileOffsetElement.getAttributes().readInt("y");
        }

        // Read properties
        this.properties = TmxHelper.parseProperties(element.getChild("properties"));
        // Read image
        this.image = TmxHelper.parseImage(element.getChild("image"), reader);

        // Read terrain types
        List<TmxTerrain> terrainTypesList = new ArrayList<TmxTerrain>();
        final XmlElement terrainTypesElement = element.getChild("terraintypes");
        if (terrainTypesElement != null) {
            for (final XmlElement terrainElement : terrainTypesElement.getChildren("terrain")) {
                terrainTypesList.add(new TmxTerrain(terrainElement));
            }
        }

        this.terrainTypes = terrainTypesList.toArray(new TmxTerrain[terrainTypesList.size()]);
        // Read additional tile information
        final Map<Integer, XmlElement> tileElements = new HashMap<Integer, XmlElement>();
        for (final XmlElement tileElement : element.getChildren("tile")) {
            final int id = tileElement.getAttributes().readInt("id");
            tileElements.put(id, tileElement);
        }

        // Create all tiles
        this.tiles = new ArrayList<TmxTile>();

        final int spacing = attributes.readInt("spacing");
        final int margin = attributes.readInt("margin");

        int nextX = margin;
        int nextY = margin;

        while (nextY + this.tileHeight + margin <= this.image.getHeight()) {
            final Image tileImage = this.image.subImage(nextX, nextY, this.tileWidth, this.tileHeight);
            final int tileId = tiles.size();
            tiles.add(new TmxTile(this, tileId, tileImage, element));
            nextX += this.tileWidth + spacing;
            if (nextX + this.tileWidth + margin > this.image.getWidth()) {
                nextX = margin;
                nextY += this.tileHeight + spacing;
            }
        }
    }

    public int getFirstGlobalId() {
        return this.firstGlobalId;
    }

    public String getName() {
        return this.name;
    }

    public final Data getProperties() {
        return this.properties;
    }

    public TmxTerrain[] getTerrains() {
        return Arrays.copyOf(this.terrainTypes, this.terrainTypes.length);
    }

    public TmxTile getTile(final int index) {
        if (0 <= index && index < this.tiles.size()) {
            return this.tiles.get(index);
        }
        else {
            return null;
        }
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileOffsetX() {
        return this.tileOffsetX;
    }

    public int getTileOffsetY() {
        return this.tileOffsetY;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }
}
