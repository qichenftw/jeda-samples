package ch.jeda.pong;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Pong extends Program implements TickListener {

    public static final int SCORE_X = 100;
    public static final int SCORE_Y = 50;
    Window window;
    private int leftScore;
    private int rightScore;
    private Ball ball;

    @Override
    public void run() {
        window = new Window(WindowFeature.DOUBLE_BUFFERED, WindowFeature.ORIENTATION_LANDSCAPE);
        window.setTitle("Pong");

        ball = new Ball(window);
        window.add(ball);

        Paddle leftPaddle = new Paddle(15, window.getHeight() / 2, new KeyboardPaddleControl(window, Key.SHIFT_LEFT, Key.CTRL_LEFT));
        Paddle rightPaddle = new Paddle(window.getWidth() - 15, window.getHeight() / 2, new KeyboardPaddleControl(window, Key.SHIFT_RIGHT, Key.CTRL_RIGHT));
        window.add(leftPaddle);
        window.add(rightPaddle);

        window.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        // Hintergrund zeichnen
        window.setColor(Color.BLACK);
        window.fill();
        // Punkte zeichnen
        window.setFontSize(20);
        window.setColor(Color.JEDA);
        window.drawText(SCORE_X, SCORE_Y, Convert.toString(leftScore), Alignment.LEFT);
        window.drawText(window.getWidth() - SCORE_X, SCORE_Y, Convert.toString(rightScore), Alignment.RIGHT);

        checkPoint();
    }

    /**
     * Überprüft, ob der Ball die Grundlinie eines Spielers berührt. Falls ja, erhält der andere Spieler einen Punkt, es
     * findet ein neues Anspiel statt.
     */
    private void checkPoint() {
        if (ball.getX() < 0) {

            ++rightScore;
            ball.reset();
            ball.setDirectionLeft();
        }

        if (ball.getX() > window.getWidth()) {
            ++leftScore;
            ball.reset();
            ball.setDirectionRight();
        }
    }
}
