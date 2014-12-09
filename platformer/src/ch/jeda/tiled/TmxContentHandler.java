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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * TODO:
 * <ul>
 * <li>image: format, trans, width, height
 * <li>terraintypes
 * <li>object layer
 * </ul>
 *
 * @author stefan
 */
class TmxContentHandler<T> implements ContentHandler {

    private final Context context;
    private final Map<String, Class<? extends TmxElement>> elementClassMap;
    private final Stack<TmxElement> elements;
    private T result;

    TmxContentHandler(final Context context) {
        this.context = context;
        this.elements = new Stack<TmxElement>();
        this.elementClassMap = new HashMap<String, Class<? extends TmxElement>>();
        this.elementClassMap.put("data", TmxDataElement.class);
        this.elementClassMap.put("image", TmxImageElement.class);
        this.elementClassMap.put("imagelayer", TmxImageLayerElement.class);
        this.elementClassMap.put("layer", TmxLayerElement.class);
        this.elementClassMap.put("map", TmxMapElement.class);
        this.elementClassMap.put("object", TmxObjectElement.class);
        this.elementClassMap.put("objectgroup", TmxObjectGroupElement.class);
        this.elementClassMap.put("properties", TmxPropertiesElement.class);
        this.elementClassMap.put("property", TmxPropertyElement.class);
        this.elementClassMap.put("tile", TmxTileElement.class);
        this.elementClassMap.put("tileoffset", TmxTileOffsetElement.class);
        this.elementClassMap.put("tileset", TmxTileSetElement.class);
    }

    @Override
    public void setDocumentLocator(final Locator locator) {
        // ignore
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(final String prefix, final String uri) throws SAXException {
    }

    @Override
    public void endPrefixMapping(final String prefix) throws SAXException {
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName,
                             final Attributes attributes) throws SAXException {
        TmxElement element = this.createElement(localName);
        if (element == null) {
            element = new TmxElement();
        }

        element.initialize(attributes, this.context);
        this.elements.push(element);
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        final TmxElement element = this.elements.pop();
        if (this.elements.empty()) {
            this.result = (T) element.create();
        }
        else {
            this.elements.peek().addChild(element);
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        this.elements.peek().characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(final char[] ch, final int start, final int length) throws SAXException {
    }

    @Override
    public void processingInstruction(final String target, final String data) throws SAXException {
    }

    @Override
    public void skippedEntity(final String name) throws SAXException {
    }

    T getResult() {
        return this.result;
    }

    private TmxElement createElement(final String name) throws SAXException {
        final Class<? extends TmxElement> clazz = this.elementClassMap.get(name.toLowerCase());
        if (clazz == null) {
            return null;
        }

        try {
            return clazz.newInstance();
        }
        catch (final SecurityException ex) {
            throw new SAXException(ex);
        }
        catch (final InstantiationException ex) {
            throw new SAXException(ex);
        }
        catch (final IllegalAccessException ex) {
            throw new SAXException(ex);
        }
        catch (final IllegalArgumentException ex) {
            throw new SAXException(ex);
        }
    }
}
