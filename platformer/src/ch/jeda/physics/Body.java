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
import ch.jeda.ui.Element;

/**
 * Represents a body.
 *
 * @since 2.0
 */
public class Body extends Element {

    private BodyImp imp;

    /**
     * Constructs a body.
     *
     * @since 2.0
     */
    public Body() {
        this.imp = new DetachedBodyImp();
    }

    /**
     * Adds a shape to this body.
     *
     * @param shape the shape to add
     *
     * @since 2.0
     */
    public final void addShape(final Shape shape) {
        this.imp.addShape(shape);
    }

    /**
     * Applys a force to this body.
     *
     * @param fx the horizontal component of the force
     * @param fy the vertical component of the force
     *
     * @since 2.0
     */
    public void applyForce(final double fx, final double fy) {
        this.imp.applyForce(fx, fy);
    }

    /**
     * Returns the current rotation of the body.
     *
     * @return the current rotation of the body
     *
     * @since 2.0
     */
    public final double getRotation() {
        return this.imp.getRotation();
    }

    /**
     * Returns the shapes of this body.
     *
     * @return the shapes of this body
     *
     * @since 2.0
     */
    public final Shape[] getShapes() {
        return this.imp.getShapes();
    }

    /**
     * Returns the x coordinate of the shape.
     *
     * @return the x coordinate of the shape
     *
     * @since 2.0
     */
    public final double getX() {
        return this.imp.getX();
    }

    /**
     * Returns the y coordinate of the shape.
     *
     * @return the y coordinate of the shape
     *
     * @since 2.0
     */
    public final double getY() {
        return this.imp.getY();
    }

    /**
     * Sets the position of the body.
     *
     * @param x the x coordinate of the body
     * @param y the y coordinate of the body
     *
     * @since 2.0
     */
    public final void setPosition(final double x, final double y) {
        this.imp.setPosition(x, y);
    }

    /**
     * Disallows or allows the body to rotate.
     *
     * @param fixed
     *
     * @since 2.0
     */
    public final void setRotationFixed(final boolean fixed) {
        this.imp.setRotationFixed(fixed);
    }

    /**
     * Sets the type of the body.
     *
     * @param type the type of the body
     *
     * @since 2.0
     */
    public final void setType(final BodyType type) {
        this.imp.setType(type);
    }

    @Override
    protected final void draw(final Canvas canvas) {
        canvas.setRotation(this.getRotation());
        canvas.setTranslation(this.getX(), this.getY());
        this.drawBody(canvas);
        this.imp.drawOverlay(canvas);
//        if (this.image != null) {
//            canvas.drawImage(0, 0, this.image, Alignment.CENTER);
//        }

        for (final Shape shape : this.getShapes()) {
            shape.draw(canvas);
        }
        canvas.resetTransformations();
    }

    protected void drawBody(final Canvas canvas) {

    }

    boolean setPhysics(final Physics physics) {
        if (this.imp.belongsTo(physics)) {
            return false;
        }

        final BodyImp oldImp = this.imp;
        this.imp = new PhysicsBodyImp(physics, oldImp);
        oldImp.destroy();
        return true;
    }
}
