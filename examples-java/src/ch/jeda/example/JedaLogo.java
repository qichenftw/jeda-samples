package ch.jeda.example;

import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Image;

public class JedaLogo {

    static void draw(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        canvas.drawImage(w - 10, h - 10, Image.JEDA_LOGO_48x48, Alignment.BOTTOM_RIGHT);
        canvas.setColor(Color.JEDA);
        canvas.drawText(w - 60, h - 38, "www.jeda.ch", Alignment.RIGHT);
    }
}
