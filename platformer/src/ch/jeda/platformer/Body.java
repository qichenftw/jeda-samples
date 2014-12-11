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
import ch.jeda.ui.Element;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public class Body extends Element {

    private final double initialX;
    private final double initialY;
    private final List<Shape> shapes;
    private final BodyType type;
    org.jbox2d.dynamics.Body imp;
    private World world;

    Body(final double x, final double y, final BodyType type) {
        this.initialX = x;
        this.initialY = y;
        this.shapes = new ArrayList<Shape>();
        this.type = type;
    }

    public final void addShape(final Shape shape) {
        this.shapes.add(shape);
        if (this.imp != null) {
            this.imp.createFixture(shape.createFixtureDef(this.world.getScale()));
        }
    }

    public final double getX() {
        if (this.imp == null) {
            return this.initialX;
        }
        else {
            return this.imp.getPosition().x * world.getScale();
        }
    }

    public final double getY() {
        if (this.imp == null) {
            return this.initialY;
        }
        else {
            return this.imp.getPosition().y * world.getScale();
        }
    }

    @Override
    protected void draw(final Canvas canvas) {
        canvas.setTranslation(this.getX(), this.getY());
        for (final Shape shape : this.shapes) {
            shape.draw(canvas);
        }

        canvas.resetTransformations();
    }

    void addToWorld(final World world) {
        this.world = world;
        final BodyDef bodyDef = new BodyDef();
        final double x = this.initialX / world.getScale();
        final double y = this.initialY / world.getScale();
        bodyDef.position.set((float) x, (float) y);
        bodyDef.type = type;

        this.imp = world.imp.createBody(bodyDef);
        for (final Shape shape : this.shapes) {
            this.imp.createFixture(shape.createFixtureDef(world.getScale()));
        }
    }
}
