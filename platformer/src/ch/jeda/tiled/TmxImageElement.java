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

class TmxImageElement extends TmxElement<Image> {

    private static final String FORMAT = "format";
    private static final String HEIGHT = "height";
    private static final String SOURCE = "source";
    private static final String TRANS = "trans";
    private static final String WIDTH = "width";

    @Override
    Image create() {
        Image result = null;
        if (this.hasAttribute(FORMAT)) {
            // TODO: image data is internal
            return null;
        }
        else {
            result = new Image(this.resolvePath(this.getStringAttribute(SOURCE)));
        }

        // TODO: Handle transparency
        return result;
    }
}
