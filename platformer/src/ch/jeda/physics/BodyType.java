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

/**
 * Represents a physics body type.
 *
 * @since 2.0
 */
public enum BodyType {

    /**
     * A dynamic body. Dynamic bodies can move and are affected by forces and collide with other bodies.
     *
     * @since 2.0
     */
    DYNAMIC,
    /**
     * A kinematic body.
     *
     * @since 2.0
     */
    KINEMATIC,
    /**
     * A static body. Static bodies cannot move. They behave like they have an infinite mass.
     *
     * @since 2.0
     */
    STATIC,
}
