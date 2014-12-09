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

import org.xml.sax.SAXException;

class TmxLayerElement extends TmxElementWithProperties<TileLayer> {

    private static final String NAME = "name";
    private static final String OPACITY = "opacity";
    private static final String VISIBLE = "visible";

    private TmxDataElement data;

    @Override
    void addChild(TmxElement element) throws SAXException {
        if (element instanceof TmxDataElement) {
            this.data = (TmxDataElement) element;
        }
        else {
            super.addChild(element);
        }
    }

    TileLayer createLayer(final TmxMapElement mapElement) throws SAXException {
        final String name = this.getStringAttribute(NAME);
        final double opacity = this.getDoubleAttribute(OPACITY, 1.0);
        final boolean visible = this.getBooleanAttribute(VISIBLE, true);
        final int width = mapElement.getWidth();
        final int height = mapElement.getHeight();

        if (this.data == null) {
            throw new SAXException("Missing data element");
        }

        final int[] tileIds = this.data.parseTiles(width, height);
        final Tile[] tiles = new Tile[width * height];
        for (int i = 0; i < tileIds.length; ++i) {
            tiles[i] = mapElement.lookupTile(tileIds[i]);
        }

        return new TileLayer(this.createData(), name, opacity, visible, width, height, tiles);
    }
}
