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

import ch.jeda.Log;
import java.io.IOException;
import java.io.InputStream;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

class TmxReader {

    private final Context context;

    TmxReader(final Context context) {
        this.context = context;
    }

    <T> T read(final InputStream in) {
        try {
            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            final TmxContentHandler<T> contentHandler = new TmxContentHandler<T>(this.context);
            xmlReader.setContentHandler(contentHandler);
            xmlReader.setEntityResolver(new TmxEntityResolver());
            InputSource inputSource = new InputSource(in);
            xmlReader.parse(inputSource);
            return contentHandler.getResult();
        }
        catch (final SAXException ex) {
            Log.err(ex, "Fehler beim Lesen einer TMX-Datei: ", ex.getMessage());
        }
        catch (final IOException ex) {
            Log.err(ex, "Fehler beim Lesen einer TMX-Datei: ", ex.getMessage());
        }

        return null;
    }
}
