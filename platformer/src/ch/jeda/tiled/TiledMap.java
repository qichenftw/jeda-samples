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
import ch.jeda.JedaInternal;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Window;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Tiled map.
 *
 * @since 1.6
 */
public final class TiledMap {

    private final TiledMapData mapData;
    private final Map<String, Layer> layersByName;
    private TiledObject scrollLock;

    public TiledMap(final String path) {
        final TmxReader reader = new TmxReader(new Context());
        this.mapData = reader.read(JedaInternal.openResource(path));
        this.layersByName = new HashMap<String, Layer>();
        for (final Layer layer : this.mapData.layers) {
            this.layersByName.put(layer.getName(), layer);
        }

        int drawOrder = 10000;
        for (int i = 0; i < this.mapData.layers.size(); ++i) {
            this.mapData.layers.get(i).setMap(this);
            this.mapData.layers.get(i).setDrawOrder(drawOrder);
            ++drawOrder;
        }
    }

    public void drawBackground(final Canvas canvas) {
        canvas.setColor(this.mapData.backgroundColor);
        canvas.fill();
    }

    public void init(final Window window) {
        for (final Layer layer : this.mapData.layers) {
            layer.init(window);
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
        return this.mapData.backgroundColor;
    }

    /**
     * Returns the height of the map in tiles.
     *
     * @return the height of the map in tiles
     */
    public int getHeight() {
        return this.mapData.height;
    }

    public Layer getLayer(final int index) {
        return this.mapData.layers.get(index);
    }

    public Layer getLayer(final String name) {
        return this.layersByName.get(name);
    }

    public int getLayerCount() {
        return this.mapData.layers.size();
    }

    public TiledMapOrientation getOrientation() {
        return this.mapData.orientation;
    }

    public final Data getProperties() {
        return this.mapData.properties;
    }

    public int getTileHeight() {
        return this.mapData.tileHeight;
    }

    public int getTileWidth() {
        return this.mapData.tileWidth;
    }

    public int getWidth() {
        return this.mapData.width;
    }

    public void move(final double dx, final double dy) {
        for (int i = 0; i < this.mapData.layers.size(); ++i) {
            this.mapData.layers.get(i).move((int) dx, (int) dy);
        }
    }
}
