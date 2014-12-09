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

import ch.jeda.ui.Image;
import org.xml.sax.SAXException;

class TmxImageLayerElement extends TmxElementWithProperties<ImageLayer> {

    private static final String NAME = "name";
    private static final String OPACITY = "opacity";
    private static final String VISIBLE = "visible";
    private Image image;

    @Override
    void addChild(final TmxElement element) throws SAXException {
        if (element instanceof TmxImageElement) {
            this.image = ((TmxImageElement) element).create();
        }
        else {
            super.addChild(element);
        }
    }

    ImageLayer createLayer() {
        final String name = this.getStringAttribute(NAME);
        final double opacity = this.getDoubleAttribute(OPACITY, 1.0);
        final boolean visible = this.getBooleanAttribute(VISIBLE, true);
        return new ImageLayer(this.createData(), name, opacity, visible, this.image);
    }
}
