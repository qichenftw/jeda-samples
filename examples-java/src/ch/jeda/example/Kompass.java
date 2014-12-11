package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Kompass extends Program implements SensorListener {

    Window fenster;
    float cx;
    float cy;

    @Override
    public void run() {
        fenster = new Window();
        fenster.setTextSize(12);
        cx = fenster.getWidth() / 2;
        cy = fenster.getHeight() / 2;
        fenster.setTextSize(20);
        fenster.setLineWidth(3);
        if (Jeda.isSensorAvailable(SensorType.MAGNETIC_FIELD)) {
            Jeda.enableSensor(SensorType.MAGNETIC_FIELD);
        }
        else {
            fenster.drawText(cx, cy, "Kein Kompass vorhanden.", Alignment.CENTER);
        }

        fenster.addEventListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        JedaLogo.draw(fenster);
        fenster.setColor(Color.BLACK);
        fenster.drawText(10, 10, "type=" + event.getSensorType());
        fenster.drawText(10, 30, "source=" + event.getSource());
        fenster.drawText(10, 50, "x = " + event.getX());
        fenster.drawText(10, 70, "y = " + event.getY());
        fenster.drawText(10, 90, "z = " + event.getZ());
        double dx = event.getX();
        double dy = event.getY();
        // Normalisieren
        double d = Util.distance(dx, dy);
        dx = dx / d;
        dy = dy / d;

        fenster.setColor(Color.JEDA);
        fenster.fillPolygon(cx - dy * 10, cy + dx * 10,
                            cx + dy * 10, cy - dx * 10,
                            cx + dx * 100, cy + dy * 100);
    }
}
