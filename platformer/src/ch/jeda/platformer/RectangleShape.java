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

import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import org.jbox2d.common.Vec2;

public class RectangleShape extends Shape {

    private final double width;
    private final double height;

    public RectangleShape(final double width, final double height) {
        this.height = height;
        this.width = width;
    }

    @Override
    void draw(final Canvas canvas) {
        canvas.setColor(Color.RED);
        canvas.drawRectangle(0, 0, this.width, this.height, Alignment.CENTER);
    }

    @Override
    org.jbox2d.collision.shapes.Shape createImp(final double scale) {
        final org.jbox2d.collision.shapes.PolygonShape result = new org.jbox2d.collision.shapes.PolygonShape();
        result.setAsBox((float) (this.width / scale / 2.0), (float) (this.height / scale / 2.0), new Vec2(0, 0), 0);
        return result;
    }
}
