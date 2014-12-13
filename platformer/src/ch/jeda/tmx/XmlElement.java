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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;

final class XmlElement {

    private final Data attributes;
    private final List<XmlElement> children;
    private final Map<String, List<XmlElement>> childrenByName;
    private final String localName;
    private String content;

    XmlElement(final String localName, final Attributes attributes) {
        this.attributes = new Data();
        for (int i = 0; i < attributes.getLength(); ++i) {
            this.attributes.writeString(attributes.getLocalName(i), attributes.getValue(i));
        }

        this.children = new ArrayList<XmlElement>();
        this.childrenByName = new HashMap<String, List<XmlElement>>();
        this.localName = localName;
    }

    void addChild(final XmlElement element) {
        final String name = element.getLocalName();
        if (!this.childrenByName.containsKey(name)) {
            this.childrenByName.put(name, new ArrayList<XmlElement>());
        }

        this.children.add(element);
        this.childrenByName.get(name).add(element);
    }

    Data getAttributes() {
        return this.attributes;
    }

    XmlElement getChild(final String name) {
        if (this.childrenByName.containsKey(name)) {
            return Collections.unmodifiableList(this.childrenByName.get(name)).get(0);
        }
        else {
            return null;
        }
    }

    List<XmlElement> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    List<XmlElement> getChildren(final String name) {
        if (this.childrenByName.containsKey(name)) {
            return Collections.unmodifiableList(this.childrenByName.get(name));
        }
        else {
            return Collections.EMPTY_LIST;
        }
    }

    public String getContent() {
        return this.content;
    }

    String getLocalName() {
        return this.localName;
    }

    boolean hasChild(final String name) {
        return this.childrenByName.containsKey(name);
    }

    boolean is(final String name) {
        return this.localName.equals(name);
    }

    void setContent(final String content) {
        this.content = content;
    }
}
