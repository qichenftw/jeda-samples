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

import ch.jeda.tmx.TmxLayer;
import ch.jeda.tmx.TmxMap;
import ch.jeda.tmx.TmxObject;
import ch.jeda.tmx.TmxTile;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Window;

public class TmxPhysics extends Physics {

    private static final String DYNAMIC = "dynamic";
    private static final String PHYSICS = "physics";
    private TmxMap map;

    public TmxPhysics(final Window window) {
        super(window);
    }

    public void loadMap(final String path) {
        this.setMap(new TmxMap(path));
    }

    public void setMap(final TmxMap map) {
        this.map = map;
        for (final TmxLayer layer : map.getLayers()) {
            this.processLayer(layer);
        }
    }

    @Override
    protected void drawBackground(final Canvas canvas) {
        if (this.map != null) {
            this.map.draw(canvas, 0, 0);
        }
    }

    private Body createBody(final double x, final double y, final boolean dynamic) {
        if (dynamic) {
            return this.createDynamicBody(x, y);
        }
        else {
            return this.createStaticBody(x, y);
        }
    }

    private void processLayer(final TmxLayer layer) {
        if (!layer.getProperties().hasValue(PHYSICS)) {
            return;
        }

        final boolean dynamic = DYNAMIC.equals(layer.getProperties().readString(PHYSICS));
        switch (layer.getType()) {
            case IMAGE:
                break;
            case OBJECT:
                this.processObjectLayer(layer, dynamic);
                break;
            case TILE:
                this.processTileLayer(layer, dynamic);
                break;
        }
    }

    private void processObjectLayer(final TmxLayer layer, final boolean dynamic) {
        final TmxObject[] objects = layer.getObjects();
        for (int i = 0; i < objects.length; ++i) {
            this.processObject(objects[i], dynamic);
        }

        layer.setVisible(false);
    }

    private void processObject(final TmxObject object, final boolean dynamic) {
        final Body body = this.createBody(object.getX(), object.getY(), dynamic);
        body.setName(object.getName());
        body.addShape(this.objectToShape(object));
        final TmxTile tile = object.getTile();
        if (tile != null) {
            body.setImage(tile.getImage());
        }
    }

    private void processTileLayer(final TmxLayer layer, final boolean dynamic) {
        final int width = layer.getMap().getWidth();
        final int height = layer.getMap().getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                this.processTile(x, y, layer.getTile(x, y), dynamic);
            }
        }

        layer.setVisible(false);
    }

    private void processTile(final int x, final int y, final TmxTile tile, final boolean dynamic) {
        if (tile == null) {
            return;
        }

        final Body body = this.createBody(tile.screenX(x), tile.screenY(y), dynamic);
        TmxObject[] objects = tile.getObjects();
        if (objects.length > 0) {
            for (int i = 0; i < objects.length; ++i) {
                body.addShape(this.objectToShape(objects[i]));
            }
        }
        else {
            body.addShape(new RectangleShape(tile.getWidth(), tile.getHeight()));
        }

        body.setImage(tile.getImage());
    }

    private Shape objectToShape(TmxObject object) {
        final TmxTile tile = object.getTile();
        double width = object.getWidth();
        double height = object.getHeight();
        if (width < 0.001) {
            if (tile != null) {
                width = tile.getWidth();
            }
            else {
                width = 10;
            }
        }

        if (height < 0.001) {
            if (tile != null) {
                height = tile.getHeight();
            }
            else {
                height = 10;
            }
        }

        if (object.isEllipse()) {
            return new CircleShape((height + width) / 4);
        }
        else {
            return new RectangleShape(width, height);
        }
    }

}
