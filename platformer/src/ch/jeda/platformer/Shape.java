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
package ch.jeda.platformer;

import ch.jeda.ui.Canvas;
import org.jbox2d.dynamics.FixtureDef;

public abstract class Shape {

    private double friction;

    protected Shape() {
        this.friction = 0.1;
    }

    public final void setFricition(final double friction) {
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
