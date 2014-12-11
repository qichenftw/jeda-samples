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
package ch.jeda.tiled;

final class TmxObjectElement extends TmxElement<TiledObject> {

    private static final String GID = "gid";
    private static final String HEIGHT = "height";
    private static final String NAME = "name";
    private static final String ROTATION = "rotation";
    private static final String TYPE = "type";
    private static final String VISIBLE = "visible";
    private static final String WIDTH = "width";
    private static final String X = "x";
    private static final String Y = "y";

    int getGid() {
        return this.getIntAttribute(GID);
    }

    double getHeight() {
        return this.getDoubleAttribute(HEIGHT, 0.0);
    }

    String getName() {
        return this.getStringAttribute(NAME);
    }

    public double getRotation() {
        return Math.toRadians(this.getDoubleAttribute(ROTATION, 0.0));
    }

    String getType() {
        return this.getStringAttribute(TYPE);
    }

    double getWidth() {
        return this.getDoubleAttribute(WIDTH, 0.0);
    }

    double getX() {
        return this.getDoubleAttribute(X);
    }

    double getY() {
        return this.getDoubleAttribute(Y);
    }

    boolean isVisible() {
        return this.getBooleanAttribute(VISIBLE, true);
    }
}
