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
import java.util.ArrayList;
import java.util.List;

class DetachedBodyImp implements BodyImp {

    private final List<Shape> shapes;
    private double rotation;
    private boolean rotationFixed;
    private BodyType type;
    private double x;
    private double y;

    DetachedBodyImp() {
        this.shapes = new ArrayList<Shape>();
        this.rotation = 0.0;
        this.rotationFixed = false;
        this.type = BodyType.DYNAMIC;
        this.x = 0.0;
        this.y = 0.0;
    }

    @Override
    public void addShape(final Shape shape) {
        this.shapes.add(shape);
    }

    @Override
    public void applyForce(double fx, double fy) {
        throw new IllegalStateException("Cannot apply force to a body that has not been added to a physics.");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void drawOverlay(final Canvas canvas) {
    }

    @Override
    public double getRotation() {
        return 0.0;
    }

    @Override
    public Shape[] getShapes() {
        return this.shapes.toArray(new Shape[this.shapes.size()]);
    }

    @Override
    public boolean belongsTo(final Physics physics) {
        return false;
    }

    @Override
    public BodyType getType() {
        return this.type;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public boolean isRotationFixed() {
        return this.rotationFixed;
    }

    @Override
    public void setPosition(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setRotation(final double rotation) {
        this.rotation = rotation;
    }

    @Override
    public void setRotationFixed(final boolean rotationFixed) {
        this.rotationFixed = rotationFixed;
    }

    @Override
    public void setType(final BodyType type) {
        this.type = type;
    }

}
