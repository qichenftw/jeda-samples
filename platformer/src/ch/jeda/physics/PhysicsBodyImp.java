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
import ch.jeda.ui.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;

final class PhysicsBodyImp implements BodyImp {

    private final org.jbox2d.dynamics.Body imp;
    private final Physics physics;
    private final List<Shape> shapes;

    PhysicsBodyImp(final Physics physics, final BodyImp oldImp) {
        this.shapes = new ArrayList<Shape>();
        final BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(physics.scaleLength(oldImp.getX()), physics.scaleLength(oldImp.getY()));
        switch (oldImp.getType()) {
            case DYNAMIC:
                bodyDef.type = org.jbox2d.dynamics.BodyType.DYNAMIC;
                break;
            case KINEMATIC:
                bodyDef.type = org.jbox2d.dynamics.BodyType.KINEMATIC;
                break;
            case STATIC:
                bodyDef.type = org.jbox2d.dynamics.BodyType.STATIC;
                break;
        }

        bodyDef.fixedRotation = oldImp.isRotationFixed();
        this.imp = physics.imp.createBody(bodyDef);
        this.imp.m_userData = this;
        this.physics = physics;
        for (final Shape shape : oldImp.getShapes()) {
            this.addShape(shape);
        }
    }

    @Override
    public void addShape(final Shape shape) {
        this.shapes.add(shape);
        this.imp.createFixture(shape.createFixtureDef(this.physics.getScale(), 1));
    }

    @Override
    public void applyForce(final double fx, final double fy) {
        this.imp.applyForce(new Vec2(this.physics.scaleLength(fx), this.physics.scaleLength(fy)), this.imp.getWorldCenter());
    }

    @Override
    public boolean belongsTo(final Physics physics) {
        return this.physics == physics;
    }

    @Override
    public void destroy() {
        this.physics.imp.destroyBody(this.imp);
        this.physics.bodyDestroyed(this);
    }

    @Override
    public void drawOverlay(final Canvas canvas) {
        canvas.setColor(Color.RED);
        if (this.physics.isDebugging()) {
            for (final Shape shape : this.shapes) {
                shape.draw(canvas);
            }
        }
    }

    @Override
    public double getRotation() {
        return this.imp.getAngle();
    }

    @Override
    public Shape[] getShapes() {
        return this.shapes.toArray(new Shape[this.shapes.size()]);
    }

    @Override
    public BodyType getType() {
        switch (this.imp.m_type) {
            case DYNAMIC:
                return BodyType.DYNAMIC;
            case KINEMATIC:
                return BodyType.KINEMATIC;
            case STATIC:
                return BodyType.STATIC;
            default:
                return null;
        }
    }

    @Override
    public double getX() {
        return this.imp.getPosition().x * this.physics.getScale();
    }

    @Override
    public double getY() {
        return this.imp.getPosition().y * this.physics.getScale();
    }

    @Override
    public boolean isRotationFixed() {
        throw new IllegalStateException("Body has already been added to a physics.");
    }

    @Override
    public void setPosition(final double x, final double y) {
        throw new IllegalStateException("Body has already been added to a physics.");
    }

    @Override
    public void setRotation(final double rotation) {
        throw new IllegalStateException("Body has already been added to a physics.");
    }

    @Override
    public void setRotationFixed(final boolean rotationFixed) {
        throw new IllegalStateException("Body has already been added to a physics.");
    }

    @Override
    public void setType(final BodyType type) {
        throw new IllegalStateException("Body has already been added to a physics.");
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("Body(name=");
        result.append(", x=");
        result.append(this.getX());
        result.append(", y=");
        result.append(this.getY());
        result.append(")");
        return result.toString();
    }

    private static EnumSet<BodyType> toSet(final BodyType... features) {
        final EnumSet<BodyType> result = EnumSet.noneOf(BodyType.class);
        for (BodyType feature : features) {
            result.add(feature);
        }

        return result;
    }
}
