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
package ch.jeda.platformer;

import ch.jeda.Program;
import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;
import ch.jeda.tmx.TmxMap;
import ch.jeda.ui.Window;
import ch.jeda.ui.WindowFeature;

public class TmxViewer extends Program implements TickListener {

    Window fenster;
    TmxMap map;
    DragHelper drag;

    @Override
    public void run() {
        fenster = new Window(1400, 700, WindowFeature.DOUBLE_BUFFERED);
        map = new TmxMap("res:/level-1.tmx");
        drag = new DragHelper(fenster, map.getWidth() * map.getTileWidth(), map.getHeight() * map.getTileHeight());
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        map.draw(fenster, drag.getOffsetX(), drag.getOffsetY());
    }

}
