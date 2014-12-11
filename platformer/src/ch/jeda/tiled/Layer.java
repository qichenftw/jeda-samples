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
import ch.jeda.ui.Composite;

/**
 * Represents a layer of a Tiled map.
 *
 * @since 1.6
 */
public abstract class Layer extends Composite {

    private final Data properties;
    private TiledMap map;
    private double opacity;

    Layer(final Data properties, final String name, final double opacity, final boolean visible) {
        super(name);
        this.setVisible(visible);
        this.opacity = Math.max(0.0, Math.min(opacity, 1.0));
        this.properties = properties;
    }

    /**
     * Returns the Tiled object with the specified name in this layer. Returns <tt>null</tt> if there is no object with
     * the specified name in this layer.
     *
     * @param name the name of the Tiled object
     * @return object with the specified name or <tt>null</tt>
     *
     * @since 1.6
     */
    public abstract TiledObject getObject(final String name);

    /**
     * Returns the current opacity of this layer.
     *
     * @return the current opacity of this layer
     *
     * @since 1.6
     */
    public final double getOpacity() {
        return this.opacity;
    }

    /**
     * Returns the properties of this layer.
     *
     * @return the properties of this layer
     *
     * @since 1.6
     */
    public final Data getProperties() {
        return this.properties;
    }

    public void setOpacity(final double opacity) {
        this.opacity = Math.max(0.0, Math.min(opacity, 1.0));
    }

    TiledMap getMap() {
        return this.map;
    }

    void setMap(final TiledMap map) {
        this.map = map;
    }
}
