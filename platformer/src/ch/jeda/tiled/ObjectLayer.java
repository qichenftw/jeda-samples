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

import ch.jeda.Data;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import java.util.HashMap;
import java.util.Map;

public class ObjectLayer extends Layer {

    private final Color color;
    private final TiledObject[] objects;
    private final Map<String, TiledObject> objectsByName;

    ObjectLayer(final Data properties, final String name, final double opacity, final boolean visible,
                final Color color, final TiledObject[] objects) {
        super(properties, name, opacity, visible);
        this.color = color;
        this.objects = objects;
        this.objectsByName = new HashMap<String, TiledObject>();
        for (int i = 0; i < this.objects.length; ++i) {
            this.objectsByName.put(objects[i].getName(), objects[i]);
            objects[i].layer = this;
            this.add(objects[i]);
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public TiledObject getObject(final String name) {
        return this.objectsByName.get(name);
    }
}
