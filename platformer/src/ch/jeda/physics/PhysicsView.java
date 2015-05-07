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

import ch.jeda.ui.View;
import ch.jeda.ui.ViewFeature;

public final class PhysicsView extends View {

    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 800;

    private final Physics physics;

    public PhysicsView() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PhysicsView(final ViewFeature... features) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PhysicsView(int width, int height, ViewFeature... features) {
        super(width, height, features);
        this.physics = new Physics();
    }

    public void add(final Body body) {
        super.add(body);
        this.physics.add(body);
    }

    public double getScale() {
        return this.physics.getScale();
    }

    public void remove(final Body body) {
        super.remove(body);
        this.physics.remove(body);
    }

    public void setGravity(final double ax, final double ay) {
        this.physics.setGravity(ax, ay);
    }

    public void setScale(final double scale) {
        this.physics.setScale(scale);
    }
}
