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
import ch.jeda.ui.Element;
import ch.jeda.ui.Window;

/**
 * Represents a layer of a Tiled map.
 *
 * @since 1.6
 */
public abstract class Layer extends Element {

    private final String name;
    private final double opacity;
    private final Data properties;
    private final boolean visible;
    private TiledMap map;
    private int x;
    private int y;

    Layer(final Data properties, final String name, final double opacity, final boolean visible) {
        this.name = name;
        this.opacity = opacity;
        this.properties = properties;
        this.visible = visible;
    }

    /**
     * Returns the name of the layer.
     *
     * @return the name of the layer
     *
     * @since 1.6
     */
    public final String getName() {
        return this.name;
    }

    public abstract TiledObject getObject(final String name);

    public final double getOpacity() {
        return this.opacity;
    }

    public final Data getProperties() {
        return this.properties;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final boolean isVisible() {
        return this.visible;
    }

    public final void move(final int dx, final int dy) {
        this.x = this.x + dx;
        this.y = this.y + dy;
    }

    void init(final Window window) {
        window.add(this);
    }

    TiledMap getMap() {
        return this.map;
    }

    void setMap(final TiledMap map) {
        this.map = map;
    }
}
