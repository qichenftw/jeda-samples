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
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import java.util.ArrayList;
import java.util.List;

public final class TmxMap {

    private final Color backgroundColor;
    private final int height;
    private final TmxLayer[] layers;
    private final TmxMapOrientation orientation;
    private final Data properties;
    private final int tileHeight;
    private final TmxTileSets tileSets;
    private final int tileWidth;
    private final int width;

    public TmxMap(String path) {
        if (path == null) {
            throw new NullPointerException("path");
        }

        final StringBuilder prefix = new StringBuilder();
        if (path.startsWith("res:")) {
            prefix.append("res:");
            path = path.substring(4);
        }

        final int index = path.lastIndexOf('/');
        if (index != -1) {
            prefix.append(path.substring(0, index));
            path = path.substring(index + 1);
        }

        System.out.println("prefix: " + prefix);
        System.out.println("path: " + path);
        final XmlReader reader = new XmlReader(prefix.toString());
        final XmlElement element = reader.read(path);
        final Data attributes = element.getAttributes();
        this.backgroundColor = TmxHelper.parseColor(attributes.readString("backgroundcolor"), Color.WHITE);
        this.height = attributes.readInt("height");
        this.tileHeight = attributes.readInt("tileheight");
        this.tileWidth = attributes.readInt("tilewidth");
        this.width = attributes.readInt("width");
        this.orientation = TmxHelper.parseOrientation(attributes.readString("orientation"));
        this.properties = TmxHelper.parseProperties(element.getChild("properties"));
        // Read tile sets
        this.tileSets = new TmxTileSets();
        for (final XmlElement tileSetElement : element.getChildren("tileset")) {
            if (tileSetElement.getAttributes().hasValue("source")) {
                this.tileSets.add(new TmxTileSet(reader.read(tileSetElement.getAttributes().readString("source")), reader));
            }
            else {
                this.tileSets.add(new TmxTileSet(tileSetElement, reader));
            }
        }

        // Read layers
        final List<TmxLayer> layerList = new ArrayList<TmxLayer>();
        for (final XmlElement layerElement : element.getChildren()) {
            if (layerElement.is("layer")) {
                layerList.add(new TmxTileLayer(this, layerElement));
            }
            else if (layerElement.is("objectgroup")) {
                layerList.add(new TmxObjectGroup(this, layerElement));
            }
            else if (layerElement.is("imagelayer")) {
                layerList.add(new TmxImageLayer(this, layerElement, reader));
            }
        }

        this.layers = layerList.toArray(new TmxLayer[layerList.size()]);
    }

    public void draw(final Canvas canvas, final double offsetX, final double offsetY) {
        canvas.setColor(this.backgroundColor);
        canvas.fill();
        for (int i = 0; i < this.layers.length; ++i) {
            this.layers[i].draw(canvas, offsetX, offsetY);
        }
    }

    /**
     * Returns the background color for the map. Reading the background color from a TMX file is currently not
     * supported. This method always returns {@link ch.jeda.ui.Color#WHITE}.
     *
     * @return the background color for the map
     *
     * @since 1.6
     */
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Returns the height of the map in tiles.
     *
     * @return the height of the map in tiles
     */
    public int getHeight() {
        return this.height;
    }

    public TmxMapOrientation getOrientation() {
        return this.orientation;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getWidth() {
        return this.width;
    }

    public TmxTile lookupTile(final int globalId) {
        return this.tileSets.lookupTile(globalId);
    }
}
