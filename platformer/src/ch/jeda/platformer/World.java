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

import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;
import ch.jeda.ui.Window;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.common.Vec2;

public final class World implements TickListener {

    private final List<Body> bodies;
    final org.jbox2d.dynamics.World imp;
    private final Window window;
    private double scale;
    private boolean paused;

    public World(final Window window) {
        this.bodies = new ArrayList<Body>();
        this.imp = new org.jbox2d.dynamics.World(new Vec2(0f, 0f));
        this.paused = true;
        this.scale = 10.0;
        this.window = window;
        this.window.addEventListener(this);
    }

    public void add(final Body body) {
        body.addToWorld(this);
        this.bodies.add(body);
        this.window.add(body);
    }

    public double getScale() {
        return this.scale;
    }

    public void setGravity(final double ax, final double ay) {
        this.imp.setGravity(new Vec2((float) ax, (float) ay));
    }

    public void setScale(final double scale) {
        this.scale = scale;
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    @Override
    public void onTick(final TickEvent event) {
        if (!this.paused) {
            this.imp.step((float) event.getDuration(), 6, 2);
        }
    }
}
