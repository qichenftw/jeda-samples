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
    private Image image;

    Body(final Physics physics, final BodyType type, final double x, final double y) {
        this.shapes = new ArrayList<Shape>();
        final BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(physics.scaleLength(x), physics.scaleLength(y));
        bodyDef.type = type;
        this.imp = physics.imp.createBody(bodyDef);
        this.physics = physics;
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
    }

    public final double getAngle() {
        return this.imp.getAngle();
    }

    public final Image getImage() {
        return this.image;
    }

    public final double getX() {
        return this.imp.getPosition().x * this.physics.getScale();
    }

    public final double getY() {
        return this.imp.getPosition().y * this.physics.getScale();
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public void setImage(final String path) {
        this.image = new Image(path);
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
