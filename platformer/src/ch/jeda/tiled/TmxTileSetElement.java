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
import java.util.List;
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
    private int offsetX;
    private int offsetY;

    TmxTileSetElement() {
    }

    @Override
    void addChild(final TmxElement element) throws SAXException {
        if (element instanceof TmxImageElement) {
            this.image = ((TmxImageElement) element).create();
        }
        else if (element instanceof TmxTileOffsetElement) {
            this.offsetX = ((TmxTileOffsetElement) element).getX();
            this.offsetY = ((TmxTileOffsetElement) element).getY();
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

        int nextX = margin;
        int nextY = margin;

        while (nextY + tileHeight + margin <= this.image.getHeight()) {
            final Image tileImage = this.image.subImage(nextX, nextY, tileWidth, tileHeight);
            tiles.add(new Tile(new Data(), tiles.size(), tileImage));

            nextX += tileWidth + spacing;
            if (nextX + tileWidth + margin > this.image.getWidth()) {
                nextX = margin;
                nextY += tileHeight + spacing;
            }
        }

        return new TileSet(this.createData(), name, tileWidth, tileHeight, this.offsetX, this.offsetY, tiles);
    }

    int getFirstGlobalId() {
        return this.getIntAttribute(FIRSTGID);
    }
}
