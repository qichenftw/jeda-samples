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
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String VISIBLE = "visible";
    private static final String X = "x";
    private static final String Y = "y";

    int getGid() {
        return this.getIntAttribute(GID);
    }

    String getName() {
        return this.getStringAttribute(NAME);
    }

    String getType() {
        return this.getStringAttribute(TYPE);
    }

    boolean isVisible() {
        return this.getBooleanAttribute(VISIBLE);
    }

    int getX() {
        return this.getIntAttribute(X);
    }

    int getY() {
        return this.getIntAttribute(Y);
    }
}
