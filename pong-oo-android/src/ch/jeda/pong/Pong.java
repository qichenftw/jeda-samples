package ch.jeda.pong;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Pong extends Program implements TickListener {

    private static final int SCORE_X = 100;
    private static final int SCORE_Y = 50;
    private static final int PADDLE_OFFSET = 50;
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

        Paddle leftPaddle = new Paddle(PADDLE_OFFSET, window.getHeight() / 2,
                                       new PointerPaddleControl(window, 0, PADDLE_OFFSET + 15));
        Paddle rightPaddle = new Paddle(window.getWidth() - PADDLE_OFFSET, window.getHeight() / 2,
                                        new PointerPaddleControl(window, window.getWidth() - PADDLE_OFFSET - 15, window.getWidth()));
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
        window.setFontSize(40);
        window.setColor(Color.WHITE);
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
