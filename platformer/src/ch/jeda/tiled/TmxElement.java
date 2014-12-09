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
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class TmxElement<T> {

    private static final String TRUE = "1";
    private final Map<String, String> attributes;
    private Context context;

    TmxElement() {
        this.attributes = new HashMap<String, String>();
    }

    final void initialize(final Attributes attributes, final Context context) {
        for (int i = 0; i < attributes.getLength(); ++i) {
            this.attributes.put(attributes.getLocalName(i), attributes.getValue(i));
        }

        this.context = context;
    }

    void addChild(final TmxElement element) throws SAXException {
    }

    void characters(char[] ch, int start, int length) throws SAXException {
    }

    T create() throws SAXException {
        return null;
    }

    final boolean getBooleanAttribute(final String name) {
        final String value = this.attributes.get(name);
        return TRUE.equals(value);
    }

    final boolean getBooleanAttribute(final String name, final boolean defaultValue) {
        final String value = this.attributes.get(name);
        if (value == null) {
            return defaultValue;
        }
        else {
            return TRUE.equals(value);
        }
    }

    final Color getColorAttribute(final String name) {
        final String value = this.attributes.get(name);
        if (value == null) {
            return null;
        }

        try {
            return new Color(value);
        }
        catch (final IllegalArgumentException ex) {
            return Color.BLACK;
        }
    }

    final Context getContext() {
        return this.context;
    }

    final double getDoubleAttribute(final String name) {
        final String value = this.attributes.get(name);
        try {
            return Double.parseDouble(value);
        }
        catch (final NumberFormatException ex) {
            return 0.0;
        }
    }

    final double getDoubleAttribute(final String name, final double defaultValue) {
        final String value = this.attributes.get(name);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value);
        }
        catch (final NumberFormatException ex) {
            return defaultValue;
        }
    }

    final int getIntAttribute(final String name) {
        final String value = this.attributes.get(name);
        try {
            return Integer.parseInt(value);
        }
        catch (final NumberFormatException ex) {
            return 0;
        }
    }

    final String getStringAttribute(final String name) {
        return this.attributes.get(name);
    }

    final boolean hasAttribute(final String name) {
        return this.attributes.containsKey(name);
    }

    final String resolvePath(final String path) {
        return this.context.resolvePath(path);
    }

    static int readIntAttribute(final Attributes attributes, final String name) {
        final String value = attributes.getValue(name);
        try {
            return Integer.parseInt(value);
        }
        catch (final NumberFormatException ex) {
            return 0;
        }
    }
}
