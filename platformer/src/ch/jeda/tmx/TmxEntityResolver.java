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

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

class TmxEntityResolver implements EntityResolver {

    private static final String TMX_DTD_URL = "http://mapeditor.org/dtd/1.0/map.dtd";

    @Override
    public InputSource resolveEntity(final String publicId, final String systemId) {
        if (TMX_DTD_URL.equals(systemId)) {
            return new InputSource(this.getClass().getResourceAsStream("ch/jeda/tiled/map.dtd"));
        }
        else {
            return null;
        }
    }
}
