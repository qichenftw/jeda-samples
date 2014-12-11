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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.xml.bind.DatatypeConverter;
import org.xml.sax.SAXException;

class TmxDataElement extends TmxElement {

    private static final String BASE64 = "base64";
    private static final String COMPRESSION = "compression";
    private static final String CSV = "csv";
    private static final String ENCODING = "encoding";
    private static final String GZIP = "gzip";
    private static final String ZLIB = "zlib";
    private String data;
    private List<TmxTileElement> tiles;

    TmxDataElement() {
        this.tiles = new ArrayList<TmxTileElement>();
    }

    @Override
    void addChild(TmxElement element) throws SAXException {
        if (element instanceof TmxTileElement) {
            this.tiles.add((TmxTileElement) element);
        }
        else {
            super.addChild(element);
        }
    }

    @Override
    void characters(char[] ch, int start, int length) throws SAXException {
        this.data = new String(ch, start, length);
    }

    int[] parseTiles(int width, int height) throws SAXException {
        final String encoding = this.getStringAttribute(ENCODING);
        if (BASE64.equalsIgnoreCase(encoding)) {
            return this.parseBase64(width, height);
        }
        else if (CSV.equalsIgnoreCase(encoding)) {
            return this.parseCsv(width, height);
        }
        else {
            final int[] result = new int[this.tiles.size()];
            for (int i = 0; i < this.tiles.size(); ++i) {
                result[i] = this.tiles.get(i).getGid();
            }

            return result;
        }

    }

    private int[] parseCsv(int width, int height) throws SAXException {
        final String[] csvTileIds = this.data.trim().split("[\\s]*,[\\s]*");
        if (csvTileIds.length != width * height) {
            throw new SAXException("Number of tiles does not match the layer's width and height");
        }

        final int[] result = new int[width * height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                result[x + y * width] = Integer.parseInt(csvTileIds[x + y * width]);
            }
        }

        return result;
    }

    private int[] parseBase64(int width, int height) throws SAXException {
        final String compression = this.getStringAttribute(COMPRESSION);
        try {
            InputStream in = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(this.data));
            if (GZIP.equalsIgnoreCase(compression)) {
                in = new GZIPInputStream(in);
            }
            else if (ZLIB.equalsIgnoreCase(compression)) {
                in = new InflaterInputStream(in);
            }
            else if (compression != null && !compression.isEmpty()) {
                throw new SAXException("Unknown compression method: " + compression);
            }

            final int[] result = new int[width * height];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    int tileId = 0;
                    tileId |= in.read();
                    tileId |= in.read() << 8;
                    tileId |= in.read() << 16;
                    tileId |= in.read() << 24;
                    result[x + y * width] = tileId;
                }
            }

            return result;
        }
        catch (final IOException ex) {
            throw new SAXException(ex);
        }
    }
}
