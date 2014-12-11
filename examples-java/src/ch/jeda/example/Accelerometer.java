package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Accelerometer extends Program implements SensorListener {

    Window fenster;
    int cx;
    int cy;

    @Override
    public void run() {
        fenster = new Window();
        fenster.setTextSize(12);
        cx = fenster.getWidth() / 2;
        cy = fenster.getHeight() / 2;
        fenster.setTextSize(20);
        fenster.setLineWidth(3);
        if (Jeda.isSensorAvailable(SensorType.ACCELERATION)) {
            Jeda.enableSensor(SensorType.ACCELERATION);
        }
        else {
            fenster.drawText(cx, cy, "Kein Accelerometer vorhanden.", Alignment.CENTER);
        }

        fenster.addEventListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        JedaLogo.draw(fenster);
        fenster.setColor(Color.BLACK);
        fenster.drawLine(cx, cy, cx + event.getX() * 20, cy + event.getY() * 20);
    }
}
