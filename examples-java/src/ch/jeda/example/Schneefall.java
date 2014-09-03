package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Schneefall extends Program implements TickListener {

    static final int ANZAHL_FLOCKEN = 400;
    Window fenster;
    Image bild;
    int[] x;
    int[] y;
    int[] r;

    @Override
    public void run() {
        fenster = new Window(WindowFeature.ORIENTATION_LANDSCAPE);
        // Bild laden
        bild = new Image("res:drawable/background.jpg");
        bild = bild.scale(fenster.getWidth(), fenster.getHeight());
        // Arrays müssen initialisiert werden
        x = new int[ANZAHL_FLOCKEN];
        y = new int[ANZAHL_FLOCKEN];
        r = new int[ANZAHL_FLOCKEN];
        // Koordinaten der Schneeflocken setzen
        int i = 0;
        while (i < ANZAHL_FLOCKEN) {
            x[i] = Util.randomInt(fenster.getWidth());
            y[i] = Util.randomInt(fenster.getHeight());
            r[i] = 2 + Util.randomInt(6);
            ++i;
        }

        // Dies muss die letzte Anweisung in run() sein.
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        // Hintergrund zeichnen
        fenster.drawImage(0, 0, bild);
        // Schneeflocke zeichnen
        fenster.setColor(Color.WHITE);
        int i = 0;
        while (i < ANZAHL_FLOCKEN) {
            fenster.fillCircle(x[i], y[i], r[i]);

            // Schneeflocke animieren
            y[i] = y[i] + r[i];
            // Wenn die Schneeflocken den unteren Bildschirmrand erreicht, wieder oben hinsetzen.
            if (y[i] >= fenster.getHeight()) {
                x[i] = Util.randomInt(fenster.getWidth());
                y[i] = 0;
            }

            ++i;
        }
    }
}
