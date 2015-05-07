/*
 * Copyright (C) 2015 by Stefan Rothe
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
package ch.jeda.physics;

import ch.jeda.ui.Canvas;
import org.jbox2d.dynamics.FixtureDef;

/**
 * Represents a shape.
 *
 * @since 2.0
 */
public abstract class Shape {

    private static final double DEFAULT_FRICTION = 0.1;
    private final double friction;

    /**
     * Constructs a shape.
     *
     * @since 2.0
     */
    protected Shape() {
        this(DEFAULT_FRICTION);
    }

    /**
     * Constructs a shape.
     *
     * @param friction the friction of this shape
     *
     * @since 2.0
     */
    protected Shape(final double friction) {
        this.friction = friction;
    }

    abstract void draw(final Canvas canvas);

    final FixtureDef createFixtureDef(final double scale, final double density) {
        FixtureDef result = new FixtureDef();
        result.shape = createImp(scale);
        result.density = (float) density;
        result.friction = (float) this.friction;
        result.userData = this;
        return result;
    }

    abstract org.jbox2d.collision.shapes.Shape createImp(final double scale);
}
