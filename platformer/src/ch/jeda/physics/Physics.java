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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbox2d.common.Vec2;

public final class Physics {

    private final List<Body> bodies;
    private final Map<String, Body> bodiesByName;
    private boolean debugging;
    final org.jbox2d.dynamics.World imp;
    private double scale;
    private boolean paused;

    public Physics() {
        this.bodies = new ArrayList<Body>();
        this.bodiesByName = new HashMap<String, Body>();
        this.debugging = false;
        this.imp = new org.jbox2d.dynamics.World(new Vec2(0f, 0f));
        this.imp.setContactListener(new PhysicsContactListener());
        this.paused = true;
        this.scale = 10.0;
    }

    public void add(final Body body) {
        if (body != null) {
            if (body.setPhysics(this)) {
                this.bodies.add(body);
            }
        }
    }

    public Body[] getBodies() {
        return this.bodies.toArray(new Body[this.bodies.size()]);
    }

    public Body getBody(final String name) {
        return this.bodiesByName.get(name);
    }

    public double getScale() {
        return this.scale;
    }

    public boolean isDebugging() {
        return this.debugging;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void remove(final Body body) {
        this.bodies.remove(body);
    }

    public void setDebugging(final boolean debugging) {
        this.debugging = debugging;
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

    public void step(final double seconds) {
        if (!this.paused) {
            this.imp.step((float) seconds, 6, 2);
        }
    }

    protected void drawBackground(final Canvas canvas) {
    }

    void bodyDestroyed(final PhysicsBodyImp body) {
        this.bodies.remove(body);
    }

    float scaleLength(final double length) {
        return (float) (length / this.scale);
    }

    float scaleForce(final double force) {
        return (float) force;
    }
}
