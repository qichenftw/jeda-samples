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
import ch.jeda.ui.Element;
import ch.jeda.ui.Image;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public class Body extends Element {

    private final org.jbox2d.dynamics.Body imp;
    private final Physics physics;
    private final List<Shape> shapes;
    double fatigue;
    double fatigueThreshold;
    private Image image;
    private String name;

    Body(final Physics physics, final BodyType type, final double x, final double y) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(physics.scaleLength(x), physics.scaleLength(y));
        bodyDef.type = type;
        this.imp = physics.imp.createBody(bodyDef);
        this.imp.m_userData = this;
        this.physics = physics;
        this.shapes = new ArrayList<Shape>();
        this.fatigue = 0.0;
        this.fatigueThreshold = 0.1;
    }

    public final void addShape(final Shape shape) {
        this.shapes.add(shape);
        if (this.imp != null) {
            this.imp.createFixture(shape.createFixtureDef(this.physics.getScale(), 1));
        }
    }

    public void applyForce(final double fx, final double fy) {
        this.imp.applyForce(new Vec2(this.physics.scaleLength(fx), this.physics.scaleLength(fy)), this.imp.getWorldCenter());
    }

    public final void destroy() {
        this.physics.imp.destroyBody(this.imp);
        this.physics.bodyDestroyed(this);
    }

    public final double getAngle() {
        return this.imp.getAngle();
    }

    /**
     * Returns the current fatigue of this body. The fatigue is the sum of all impulses above the fatigue threshold that
     * have been acting on this body so far.
     *
     * @return the current fatigue of this body
     *
     * @since 1.6
     */
    public final double getFatigue() {
        return this.fatigue;
    }

    /**
     * Returns the fatigue threshold.
     *
     * @return the fatigue threshold
     *
     * @since 1.6
     */
    public double getFatigueThreshold() {
        return this.fatigueThreshold;
    }

    public final Image getImage() {
        return this.image;
    }

    public final double getLinearImpulse() {
        return this.imp.getLinearVelocity().length() * this.imp.m_mass;
    }

    public final String getName() {
        return this.name;
    }

    public final double getX() {
        return this.imp.getPosition().x * this.physics.getScale();
    }

    public final double getY() {
        return this.imp.getPosition().y * this.physics.getScale();
    }

    public final boolean isStatic() {
        return this.imp.m_type == BodyType.STATIC;
    }

    public void setFatigue(final double fatigue) {
        this.fatigue = fatigue;
    }

    public void setFatigueThreshold(final double fatigueThreshold) {
        this.fatigueThreshold = fatigueThreshold;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public void setImage(final String path) {
        this.image = new Image(path);
    }

    public void setName(final String name) {
        this.physics.nameChanged(this, this.name, name);
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("Body(name=");
        result.append(this.name);
        result.append(", x=");
        result.append(this.getX());
        result.append(", y=");
        result.append(this.getY());
        result.append(")");
        return result.toString();
    }

    @Override
    protected void draw(final Canvas canvas) {
        canvas.setRotation(this.imp.getAngle());
        canvas.setTranslation(this.getX(), this.getY());
        if (this.image != null) {
            canvas.drawImage(0, 0, this.image, Alignment.CENTER);
        }

        for (final Shape shape : this.shapes) {
            shape.draw(canvas);
        }
        canvas.resetTransformations();
    }

    private static EnumSet<BodyFeature> toSet(final BodyFeature... features) {
        final EnumSet<BodyFeature> result = EnumSet.noneOf(BodyFeature.class);
        for (BodyFeature feature : features) {
            result.add(feature);
        }

        return result;
    }
}
