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

import ch.jeda.ui.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.xml.sax.SAXException;

// TODO: Add renderorder
class TmxMapElement extends TmxElementWithProperties<TiledMapData> {

    private static final String BACKGROUNDCOLOR = "backgroundcolor";
    private static final String HEIGHT = "height";
    private static final String ORIENTATION = "orientation";
    private static final String TILEHEIGHT = "tileheight";
    private static final String TILEWIDTH = "tilewidth";
    private static final String WIDTH = "width";
    private final List<Layer> layers;
    private final List<TileSet> tileSets;
    private final TreeMap<Integer, TileSet> tileSetsByGid;

    TmxMapElement() {
        this.layers = new ArrayList<Layer>();
        this.tileSets = new ArrayList<TileSet>();
        this.tileSetsByGid = new TreeMap<Integer, TileSet>();
    }

    @Override
    void addChild(final TmxElement element) throws SAXException {
        if (element instanceof TmxImageLayerElement) {
            this.layers.add(((TmxImageLayerElement) element).createLayer());
        }
        else if (element instanceof TmxLayerElement) {
            this.layers.add(((TmxLayerElement) element).createLayer(this));
        }
        else if (element instanceof TmxObjectGroupElement) {
            this.layers.add(((TmxObjectGroupElement) element).createLayer(this));
        }
        else if (element instanceof TmxTileSetElement) {
            this.addTileSet((TmxTileSetElement) element);
        }
        else {
            super.addChild(element);
        }
    }

    @Override
    TiledMapData create() throws SAXException {
        final Color backgroundColor = this.getColorAttribute(BACKGROUNDCOLOR);
        final int height = this.getHeight();
        final TiledMapOrientation orientation = this.getOrientation();
        final int tileHeight = this.getIntAttribute(TILEHEIGHT);
        final int tileWidth = this.getIntAttribute(TILEWIDTH);
        final int width = this.getWidth();
        return new TiledMapData(this.createData(), orientation, width, height, tileWidth, tileHeight, backgroundColor,
                                this.layers);
    }

    Tile lookupTile(final int globalId) {
        Map.Entry<Integer, TileSet> entry = this.tileSetsByGid.floorEntry(globalId);
        if (entry == null) {
            return null;
        }
        else {
            return entry.getValue().getTile(globalId - entry.getKey());
        }
    }

    int getHeight() {
        return this.getIntAttribute(HEIGHT);
    }

    int getWidth() {
        return this.getIntAttribute(WIDTH);
    }

    private void addTileSet(final TmxTileSetElement tileSetElement) throws SAXException {
        final TileSet tileSet = tileSetElement.create();
        this.tileSets.add(tileSet);
        this.tileSetsByGid.put(tileSetElement.getFirstGlobalId(), tileSet);
    }

    private TiledMapOrientation getOrientation() throws SAXException {
        String value = this.getStringAttribute(ORIENTATION);
        if (value == null) {
            throw new SAXException("Attribute 'orientation' of element 'map' is missing.");
        }

        try {
            return TiledMapOrientation.valueOf(value.toUpperCase());
        }
        catch (final IllegalArgumentException ex) {
            throw new SAXException(ex);
        }

    }
}
