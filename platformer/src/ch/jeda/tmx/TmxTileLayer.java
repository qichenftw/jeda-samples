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

import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Image;

/**
 * Represents a TMX tile layer.
 *
 * @since 1.6
 */
public final class TmxTileLayer extends TmxLayer {

    private final TmxTile[] tiles;

    TmxTileLayer(final TmxMap map, final XmlElement element) {
        super(map, element);
        // Read tile ids
        final int width = map.getWidth();
        final int height = map.getHeight();
        final int[] tileIds = TmxHelper.parseData(element.getChild("data"), width, height);
        this.tiles = new TmxTile[width * height];
        for (int i = 0; i < tileIds.length; ++i) {
            this.tiles[i] = map.lookupTile(tileIds[i]);
        }
    }

    @Override
    public void draw(final Canvas canvas, final double offsetX, final double offsetY) {
        if (!this.isVisible()) {
            return;
        }

        final int alpha = (int) (this.getOpacity() * 255);
        final int tileHeight = this.getMap().getTileHeight();
        final int tileWidth = this.getMap().getTileWidth();
        int screenX = (int) offsetX;
        int screenY = (int) offsetY + tileHeight;
        int startX = 0;
        int startY = 0;
        int endX = this.getMap().getWidth();
        int endY = this.getMap().getHeight();
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                final TmxTile tile = this.getTile(x, y);
                if (tile != null) {
                    canvas.drawImage(screenX + tile.getTileSet().getTileOffsetX(),
                                     screenY + tile.getTileSet().getTileOffsetY(),
                                     tile.getImage(), alpha, Alignment.BOTTOM_LEFT);
                }
                screenY += tileHeight;
            }

            screenY = (int) offsetY + tileHeight;
            screenX += tileWidth;
        }

        drawDebugOverlay(canvas, (int) offsetX, (int) offsetY);
    }

    public TmxTile getTile(final int x, final int y) {
        final int index = x + y * this.getMap().getWidth();
        if (0 <= index && index < this.tiles.length && this.tiles[index] != null) {
            return this.tiles[index];
        }

        return null;
    }

    Image getTileImage(final int x, final int y) {
        final TmxTile tile = this.getTile(x, y);
        if (tile == null) {
            return null;
        }
        else {
            return tile.getImage();
        }
    }

    private void drawDebugOverlay(final Canvas canvas, final int offsetX, final int offsetY) {
        canvas.setColor(Color.RED);
        final int tileHeight = this.getMap().getTileHeight();
        final int tileWidth = this.getMap().getTileWidth();
        int screenX = offsetX;
        int screenY = offsetY;
        int startX = 0;
        int startY = 0;
        int endX = this.getMap().getWidth();
        int endY = this.getMap().getHeight();
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                final TmxTile tile = this.getTile(x, y);
                if (tile != null && tile.getTerrainTopLeft() != null) {
                    canvas.fillRectangle(screenX, screenY, 5, 5);
                }

                if (tile != null && tile.getTerrainTopRight() != null) {
                    canvas.fillRectangle(screenX + tileWidth - 5, screenY, 5, 5);
                }

                if (tile != null && tile.getTerrainBottomLeft() != null) {
                    canvas.fillRectangle(screenX, screenY + tileHeight - 5, 5, 5);
                }

                if (tile != null && tile.getTerrainBottomRight() != null) {
                    canvas.fillRectangle(screenX + tileWidth - 5, screenY + tileHeight - 5, 5, 5);
                }

                screenY += tileHeight;
            }

            screenY = offsetY;
            screenX += tileWidth;
        }
    }
}
