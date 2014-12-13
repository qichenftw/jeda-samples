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
package ch.jeda.tmx;

import ch.jeda.Data;
import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Image;

public final class TmxObject {

    private final String name;
    private final String type;
    private double x;
    private double y;
    private double width;
    private double height;
    private double rotation;
    private TmxTile tile;
    private boolean visible;
    private final Data properties;

    TmxObject(final TmxMap map, final XmlElement element) {
        final Data attributes = element.getAttributes();
        this.name = attributes.readString("name");
        this.type = attributes.readString("type");
        this.x = attributes.readDouble("x");
        this.y = attributes.readDouble("y");
        this.width = attributes.readDouble("width", 0.0);
        this.height = attributes.readDouble("height", 0.0);
        this.properties = TmxHelper.parseProperties(element.getChild("properties"));
        this.rotation = attributes.readDouble("rotation", 0.0);
        this.visible = TmxHelper.parseBoolean(attributes.readString("visible", "1"));
        if (attributes.hasValue("gid")) {
            this.tile = map.lookupTile(attributes.readInt("gid"));
        }
        else {
            this.tile = null;
        }
    }

    public void draw(final Canvas canvas, final double offsetX, final double offsetY) {
        if (!this.visible) {
            return;
        }

        canvas.resetTransformations();
        canvas.setTranslation(this.x + offsetX, this.y + offsetY);
        canvas.setRotation(Math.toRadians(this.rotation));
        if (this.tile != null) {
            canvas.drawImage(0, 0, this.tile.getImage(), Alignment.BOTTOM_LEFT);
        }

        canvas.popTransformations();
    }

    public double getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public Data getProperties() {
        return this.properties;
    }

    public double getRotation() {
        return this.rotation;
    }

    public String getType() {
        return this.type;
    }

    public double getWidth() {
        return this.width;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
