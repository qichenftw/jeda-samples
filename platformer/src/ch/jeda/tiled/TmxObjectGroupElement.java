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

import ch.jeda.ui.Color;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;

class TmxObjectGroupElement extends TmxElementWithProperties<ObjectLayer> {

    private static final String COLOR = "color";
    private static final String NAME = "name";
    private static final String OPACITY = "opacity";
    private static final String VISIBLE = "visible";
    private final List<TmxObjectElement> objectElements;

    public TmxObjectGroupElement() {
        this.objectElements = new ArrayList<TmxObjectElement>();
    }

    @Override
    void addChild(final TmxElement element) throws SAXException {
        if (element instanceof TmxObjectElement) {
            this.objectElements.add((TmxObjectElement) element);
        }
        else {
            super.addChild(element);
        }
    }

    ObjectLayer createLayer(final TmxMapElement mapElement) throws SAXException {
        final Color color = this.getColorAttribute(COLOR);
        final String name = this.getStringAttribute(NAME);
        final double opacity = this.getDoubleAttribute(OPACITY, 1.0);
        final boolean visible = this.getBooleanAttribute(VISIBLE, true);

        final TiledObject[] objects = new TiledObject[this.objectElements.size()];
        for (int i = 0; i < this.objectElements.size(); ++i) {
            final TmxObjectElement element = this.objectElements.get(i);
            final Tile tile = mapElement.lookupTile(element.getGid());
            objects[i] = new TiledObject(element.getName(), element.getType(), element.isVisible(),
                                         element.getX(), element.getY(), tile);
        }

        return new ObjectLayer(this.createData(), name, opacity, visible, color, objects);
    }
}
