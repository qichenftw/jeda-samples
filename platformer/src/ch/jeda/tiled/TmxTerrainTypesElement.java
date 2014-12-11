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

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;

class TmxTerrainTypesElement extends TmxElement<TmxTerrainTypesElement> {

    private final List<TmxTerrainElement> terrains;

    TmxTerrainTypesElement() {
        this.terrains = new ArrayList<TmxTerrainElement>();
    }

    @Override
    void addChild(TmxElement element) throws SAXException {
        if (element instanceof TmxTerrainElement) {
            this.terrains.add((TmxTerrainElement) element);
        }
        else {
            super.addChild(element);
        }
    }

    List<TmxTerrainElement> getTerrains() {
        return this.terrains;
    }

}
