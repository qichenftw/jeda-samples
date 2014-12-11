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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.SAXException;

class TmxTileSetElement extends TmxElementWithProperties<TileSet> {

    private static final String FIRSTGID = "firstgid";
    private static final String MARGIN = "margin";
    private static final String NAME = "name";
    private static final String SOURCE = "source";
    private static final String TILEHEIGHT = "tileheight";
    private static final String TILEWIDTH = "tilewidth";
    private static final String SPACING = "spacing";
    private Image image;
    private TmxTerrainTypesElement terrainTypes;
    private TmxTileOffsetElement tileOffset;
    private final Map<Integer, TmxTileElement> tileElements;

    TmxTileSetElement() {
        this.tileElements = new HashMap<Integer, TmxTileElement>();
    }

    @Override
    void addChild(final TmxElement element) throws SAXException {
        if (element instanceof TmxTileElement) {
            final TmxTileElement tileElement = (TmxTileElement) element;
            this.tileElements.put(tileElement.getId(), tileElement);
        }
        else if (element instanceof TmxImageElement) {
            this.image = ((TmxImageElement) element).create();
        }
        else if (element instanceof TmxTileOffsetElement) {
            this.tileOffset = (TmxTileOffsetElement) element;
        }
        else if (element instanceof TmxTerrainTypesElement) {
            this.terrainTypes = (TmxTerrainTypesElement) element;
        }
        else {
            super.addChild(element);
        }
    }

    @Override
    TileSet create() throws SAXException {
        if (this.hasAttribute(SOURCE)) {
            return this.createExternalTileSet();
        }
        else {
            return this.createInternalTileSet();
        }
    }

    private TileSet createExternalTileSet() throws SAXException {
        final TmxReader reader = new TmxReader(this.getContext());
        try {
            return reader.read(new FileInputStream(this.resolvePath(this.getStringAttribute(SOURCE))));

        }
        catch (final IOException ex) {
            throw new SAXException(ex);
        }
    }

    private TileSet createInternalTileSet() throws SAXException {
        final String name = this.getStringAttribute(NAME);
        final int tileHeight = this.getIntAttribute(TILEHEIGHT);
        final int tileWidth = this.getIntAttribute(TILEWIDTH);
        final int margin = this.getIntAttribute(MARGIN);
        final int spacing = this.getIntAttribute(SPACING);

        final List<Tile> tiles = new ArrayList<Tile>();
        final List<Terrain> terrains = this.createTerrainList();
        final Map<String, Terrain> terrainMap = new HashMap<String, Terrain>();

        int nextX = margin;
        int nextY = margin;

        while (nextY + tileHeight + margin <= this.image.getHeight()) {
            final Image tileImage = this.image.subImage(nextX, nextY, tileWidth, tileHeight);
            final int tileId = tiles.size();
            tiles.add(this.createTile(tileImage, tileId, this.tileElements.get(tileId), terrains));

            nextX += tileWidth + spacing;
            if (nextX + tileWidth + margin > this.image.getWidth()) {
                nextX = margin;
                nextY += tileHeight + spacing;
            }
        }

        int offsetX = 0;
        int offsetY = 0;
        if (tileOffset != null) {
            offsetX = this.tileOffset.getX();
            offsetY = this.tileOffset.getY();
        }

        return new TileSet(this.createData(), name, tileWidth, tileHeight, offsetX, offsetY, tiles);
    }

    int getFirstGlobalId() {
        return this.getIntAttribute(FIRSTGID);
    }

    List<Terrain> createTerrainList() {
        final List<Terrain> result = new ArrayList<Terrain>();
        if (this.terrainTypes != null) {
            for (final TmxTerrainElement element : this.terrainTypes.getTerrains()) {
                result.add(new Terrain(element.getName()));
            }
        }

        return result;
    }

    Tile createTile(final Image tileImage, final int id, final TmxTileElement element, final List<Terrain> terrainList) {
        Data properties = new Data();
        Terrain[] terrains = new Terrain[4];
        if (element != null) {
            properties = element.createData();
            // Parse terrain info of tile
            final String terrainInfo = element.getTerrain();
            if (terrainInfo != null) {
                String[] parts = terrainInfo.split(",");
                int i = 0;
                while (i < 4 && i < parts.length) {
                    try {
                        int terrainId = Integer.parseInt(parts[i]);
                        if (0 <= terrainId && terrainId < terrainList.size()) {
                            terrains[i] = terrainList.get(terrainId);
                        }
                    }
                    catch (final NumberFormatException ex) {
                        // ignore
                    }

                    ++i;
                }
            }
        }

        return new Tile(properties, id, tileImage, terrains);
    }
}
