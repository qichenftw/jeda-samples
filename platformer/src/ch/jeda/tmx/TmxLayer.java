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

/**
 * Base class for TMX map layers.
 *
 * @since 1.6
 */
public abstract class TmxLayer {

    private final TmxMap map;
    private final String name;
    private final Data properties;
    private double opacity;
    private boolean visible;

    TmxLayer(final TmxMap map, final XmlElement element) {
        final Data attributes = element.getAttributes();
        this.map = map;
        this.name = attributes.readString("name");
        this.opacity = Math.max(0.0, Math.min(attributes.readDouble("opacity", 1.0), 1.0));
        this.properties = TmxHelper.parseProperties(element.getChild("properties"));
        this.visible = TmxHelper.parseBoolean(attributes.readString("visible", "1"));
    }

    /**
     * Draws this layer at the specified coordinates. The current opacity and visibility of the layer are taken into
     * account when drawing the layer.
     *
     * @param canvas the canvas to draw this layer on
     * @param offsetX the horizontal offset for drawing the layer
     * @param offsetY the vertical offset for drawing the layer
     *
     * @since 1.6
     */
    public abstract void draw(final Canvas canvas, final double offsetX, final double offsetY);

    /**
     * Returns the map this layer belongs to.
     *
     * @return the map this layer belongs to
     *
     * @since 1.6
     */
    public TmxMap getMap() {
        return this.map;
    }

    /**
     * Returns the name of this layer.
     *
     * @return the name of this layer
     *
     * @since 1.6
     */
    public final String getName() {
        return this.name;
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
//    public abstract TiledObject getObject(final String name);
    /**
     * Returns the opacity of this layer. The opacity is a number between 0 and 1 where 0 means totally transparent and
     * 1 means totally opaque.
     *
     * @return the opacity of this layer
     *
     * @see #setOpacity(double)
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

    /**
     * Checks if this layer is visible.
     *
     * @return <tt>true</tt> is this layer is visible, otherwise <tt>false</tt>
     *
     * @see #setVisible(boolean)
     * @since 1.6
     */
    public final boolean isVisible() {
        return this.visible;
    }

    /**
     * Sets the opacity of this layer. The opacity is a number between 0 and 1 where 0 means totally transparent and 1
     * means totally opaque.
     *
     * @param opacity the opacity of this layer
     *
     * @see #getOpacity()
     * @since 1.6
     */
    public void setOpacity(double opacity) {
        this.opacity = Math.max(0.0, Math.min(opacity, 1.0));
    }

    /**
     * Sets the visibility of this layer.
     *
     * @param visible the visibility of this layer
     *
     * @see #isVisible()
     * @since 1.6
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
