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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class DynamicBody extends Body {

    public DynamicBody(final double x, final double y) {
        super(x, y, BodyType.DYNAMIC);
    }

    public void applyForce(final double fx, final double fy) {
        this.imp.applyForce(new Vec2((float) fx, (float) fy), new Vec2(0, 0));
    }

}
