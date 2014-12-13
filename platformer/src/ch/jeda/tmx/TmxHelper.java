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
import ch.jeda.ui.Color;
import ch.jeda.ui.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.xml.bind.DatatypeConverter;

final class TmxHelper {

    private static final String TRUE = "1";

    static boolean parseBoolean(final String value) {
        return TRUE.equals(value);
    }

    static Color parseColor(final String value, final Color defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return new Color(value);
        }
        catch (final IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    static int[] parseData(final XmlElement element, final int width, final int height) {
        final String encoding = element.getAttributes().readString("encoding");
        if ("base64".equalsIgnoreCase(encoding)) {
            return parseBase64(element, width, height);
        }
        else if ("csv".equalsIgnoreCase(encoding)) {
            return parseCsv(element.getContent(), width, height);
        }
        else {
            final int[] result = new int[width * height];
            int i = 0;
            for (final XmlElement tileElement : element.getChildren("tile")) {
                result[i] = tileElement.getAttributes().readInt("gid");
                ++i;
            }

            return result;
        }
    }

    // TODO: support internal images
    // TODO: support relative paths
    static Image parseImage(final XmlElement element, final XmlReader reader) {
        if (element == null) {
            return null;
        }

        final Data attributes = element.getAttributes();
        final String source = attributes.readString("source");
        if (source != null) {
            return reader.readImage(source);
        }
        else {
            return null;
        }
    }

    static TmxMapOrientation parseOrientation(final String value) {
        if (value == null) {
            return TmxMapOrientation.ORTHOGONAL;
        }

        try {
            return TmxMapOrientation.valueOf(value.toUpperCase());
        }
        catch (final IllegalArgumentException ex) {
            return TmxMapOrientation.ORTHOGONAL;
        }
    }

    static Data parseProperties(final XmlElement element) {
        final Data result = new Data();
        if (element != null) {
            for (final XmlElement child : element.getChildren("property")) {
                final Data attributes = child.getAttributes();
                result.writeString(attributes.readString("name"), attributes.readString("value"));
            }
        }

        return result;
    }

    static void parseTerrain(final String terrainInfo, final TmxTerrain[] terrainTypes, TmxTerrain[] result) {
        if (terrainInfo != null) {
            String[] parts = terrainInfo.split(",");
            int i = 0;
            while (i < 4 && i < parts.length) {
                try {
                    int terrainId = Integer.parseInt(parts[i]);
                    if (0 <= terrainId && terrainId < terrainTypes.length) {
                        result[i] = terrainTypes[terrainId];
                    }
                }
                catch (final NumberFormatException ex) {
                    // ignore
                }

                ++i;
            }
        }
    }

    private static int[] parseBase64(final XmlElement element, final int width, final int height) {
        final String compression = element.getAttributes().readString("compression");
        try {
            InputStream in = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(element.getContent()));
            if ("gzip".equalsIgnoreCase(compression)) {
                in = new GZIPInputStream(in);
            }
            else if ("zlib".equalsIgnoreCase(compression)) {
                in = new InflaterInputStream(in);
            }
            else if (compression != null && !compression.isEmpty()) {
                throw new RuntimeException("Unknown compression method: " + compression);
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
            throw new RuntimeException(ex);
        }
    }

    private static int[] parseCsv(final String data, final int width, final int height) {
        final String[] csvTileIds = data.trim().split("[\\s]*,[\\s]*");
        if (csvTileIds.length != width * height) {
            throw new RuntimeException("Number of tiles does not match the layer's width and height");
        }

        final int[] result = new int[width * height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                result[x + y * width] = Integer.parseInt(csvTileIds[x + y * width]);
            }
        }

        return result;
    }
}
