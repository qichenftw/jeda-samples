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

import ch.jeda.JedaInternal;
import ch.jeda.Log;
import ch.jeda.ui.Image;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

class XmlReader {

    private final String prefix;

    XmlReader(final String prefix) {
        this.prefix = prefix;
    }

    XmlElement read(final String path) {
        try {
            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            final XmlContentHandler contentHandler = new XmlContentHandler();
            xmlReader.setContentHandler(contentHandler);
            xmlReader.setEntityResolver(new TmxEntityResolver());
            InputSource inputSource = new InputSource(JedaInternal.openResource(this.prefix + path));
            xmlReader.parse(inputSource);
            return contentHandler.getRootElement();
        }
        catch (final SAXException ex) {
            Log.err(ex, "Fehler beim Lesen einer TMX-Datei: ", ex.getMessage());
        }
        catch (final IOException ex) {
            Log.err(ex, "Fehler beim Lesen einer TMX-Datei: ", ex.getMessage());
        }

        return null;
    }

    // TODO: support internal images
    // TODO: support relative paths
    Image readImage(final String path) {
        return new Image(this.prefix + path);
    }
}
