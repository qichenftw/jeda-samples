package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Pong extends Program implements TickListener,
                                             PointerDownListener,
                                             PointerMovedListener,
                                             PointerUpListener {

    Window fenster;
    // Höhe des Zeichenbereichs
    int h;
    // Breite des Zeichenbereichs
    int w;
    // Ball: Koordinaten des Mittelpunkts
    double x;
    double y;
    // Ball: Radius
    int radius;
    // Ball: Geschwindigkeit
    double speed;
    // Ball: Richtung
    double direction;
    // Linker Schläger: y-Koordinate der Mitte des Schlägers
    double leftPaddleY;
    // Rechter Schläger: -Koordinate der Mitte des Schlägers
    double rightPaddleY;
    // Halbe Höhe der Schläger
    double paddleRadius;
    // Dicke der Schläger
    double paddleWidth;
    // Geschwindigkeit der Schläger
    double paddleSpeed;
    // Linker Zeiger
    PointerEvent leftPointer;
    // Rechter Zeiger
    PointerEvent rightPointer;
    // Punkte des linken Spielers
    int leftPoints;
    // Punkte des rechten Spielers
    int rightPoints;

    @Override
    public void run() {
        fenster = new Window(WindowFeature.DOUBLE_BUFFERED, WindowFeature.ORIENTATION_LANDSCAPE);
        fenster.setTitle("Pong");
        w = fenster.getWidth();
        h = fenster.getHeight();

        // Der Ball ist am Anfang in der Mitte des Spielfelds
        x = w / 2;
        y = h / 2;
        speed = 500;
        direction = (0.5 * Math.random() - 0.25) * Math.PI;
        radius = 15;

        // Die Schläger sind am Anfang in der Mitte der Seite
        leftPaddleY = h / 2;
        rightPaddleY = leftPaddleY;
        paddleRadius = 75;
        paddleWidth = 30;
        paddleSpeed = 500;
        fenster.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        // Ball bewegen
        movePaddles(event.getDuration());
        moveBall(event.getDuration());
        checkHit();
        checkPoint();

        // Hintergrund zeichnen
        fenster.setColor(Color.BLACK);
        fenster.fill();
        // Ball zeichnen
        fenster.setColor(Color.JEDA);
        fenster.fillCircle((int) x, (int) y, radius);
        // Linker Schläger zeichnen
        fenster.fillRectangle(0, leftPaddleY - paddleRadius, paddleWidth, 2 * paddleRadius);
        // Rechter Schläger zeichnen
        fenster.fillRectangle(w - paddleWidth, rightPaddleY - paddleRadius, paddleWidth, 2 * paddleRadius);
        // Punkte zeichnen
        fenster.setFontSize(20);
        fenster.drawText(paddleWidth + 10, 10, String.valueOf(leftPoints));
        fenster.drawText(w - paddleWidth - 10, 10, String.valueOf(rightPoints), Alignment.RIGHT);
    }

    @Override
    public void onPointerDown(PointerEvent event) {
        if (leftPointer == null && event.getX() < paddleWidth) {
            leftPointer = event;
        }

        if (rightPointer == null && event.getX() > w - paddleWidth) {
            rightPointer = event;
        }
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        if (leftPointer != null && event.getPointerId() == leftPointer.getPointerId()) {
            leftPointer = event;
        }

        if (rightPointer != null && event.getPointerId() == rightPointer.getPointerId()) {
            rightPointer = event;
        }
    }

    public void onPointerUp(PointerEvent event) {
        if (leftPointer != null && event.getPointerId() == leftPointer.getPointerId()) {
            leftPointer = null;
        }

        if (rightPointer != null && event.getPointerId() == rightPointer.getPointerId()) {
            rightPointer = null;
        }
    }

    private void checkHit() {
        if (x < paddleWidth + radius && leftPaddleY - paddleRadius < y && y < leftPaddleY + paddleRadius) {
            direction = Math.PI - direction;
            x = paddleWidth + radius;
        }

        if (x > w - paddleWidth - radius && rightPaddleY - paddleRadius < y && y < rightPaddleY + paddleRadius) {
            direction = Math.PI - direction;
            x = w - paddleWidth - radius;
        }
    }

    private void checkPoint() {
        if (x < 0) {
            ++rightPoints;
            x = w / 2;
            y = h / 2;
            direction = (0.5 * Math.random() - 0.25) * Math.PI;
        }

        if (x > w) {
            ++leftPoints;
            x = w / 2;
            y = h / 2;
            direction = (0.5 * Math.random() + 0.75) * Math.PI;
        }
    }

    private void movePaddles(double dt) {
        double dy = dt * paddleSpeed;
        if (leftPointer != null) {
            double delta = leftPointer.getY() - leftPaddleY;
            if (Math.abs(delta) < dy) {
                leftPaddleY = leftPointer.getY();
            }
            else {
                leftPaddleY = leftPaddleY + dy * Math.signum(delta);
            }

            leftPaddleY = Math.min(h - paddleRadius, Math.max(paddleRadius, leftPaddleY));
        }

        if (rightPointer != null) {
            double delta = rightPointer.getY() - rightPaddleY;
            if (Math.abs(delta) < dy) {
                rightPaddleY = rightPointer.getY();
            }
            else {
                rightPaddleY = rightPaddleY + dy * Math.signum(delta);
            }

            rightPaddleY = Math.min(h - paddleRadius, Math.max(paddleRadius, rightPaddleY));
        }
    }

    private void moveBall(double dt) {
        x = x + speed * dt * Math.cos(direction);
        y = y - speed * dt * Math.sin(direction);

        // Abprallen am oberen Rand
        // Wenn die y-Koordinate kleiner als der Radius ist,
        // hat der Ball den Rand erreicht.
        if (y < radius) {
            direction = -direction;
            // Den Ball aufs Spielfeld zurück setzen, damit er sich nicht am
            // Rand verfängt.
            y = radius;
        }

        // Abprallen am unteren Rand
        // Wenn die y-Koordinate grösser als die Höhe des Spielfelds minus
        // Radius ist, hat der Ball den Rand erreicht.
        if (y > h - radius) {
            direction = -direction;
            // Den Ball aufs Spielfeld zurück setzen, damit er sich nicht am
            // Rand verfängt.
            y = h - radius;
        }
    }
}
