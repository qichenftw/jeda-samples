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
package ch.jeda.physics;

import ch.jeda.event.Event;
import ch.jeda.event.EventType;

public class ContactEvent extends Event {

    private final PhysicsBodyImp firstBody;
    private final Shape firstShape;
    private final PhysicsBodyImp secondBody;
    private final Shape secondShape;

    public ContactEvent(final Object source, final PhysicsBodyImp firstBody, final Shape firstShape, final PhysicsBodyImp secondBody,
                        final Shape secondShape) {
        super(source, EventType.ACTION);
        this.firstBody = firstBody;
        this.firstShape = firstShape;
        this.secondBody = secondBody;
        this.secondShape = secondShape;
    }

    public PhysicsBodyImp getFirstBody() {
        return this.firstBody;
    }

    public Shape getFirstShape() {
        return this.firstShape;
    }

    public PhysicsBodyImp getSecondBody() {
        return this.secondBody;
    }

    public Shape getSecondShape() {
        return this.secondShape;
    }

    public boolean involves(final PhysicsBodyImp body) {
        return this.firstBody == body || this.secondBody == body;
    }
}
