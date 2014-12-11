package ch.jeda.asteroids;

import ch.jeda.*;
import ch.jeda.ui.*;

public class HUD extends Element {

    private static final Color DISPLAY_BG = new Color(100, 155, 100, 150);
    private static final Color DISPLAY_FG = new Color(100, 255, 100);
    private int score;

    HUD() {
        setDrawOrder(Integer.MAX_VALUE);
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.setColor(DISPLAY_BG);
        canvas.fillRectangle(5, 5, 150, 40);
        canvas.setColor(DISPLAY_FG);
        canvas.drawRectangle(5, 5, 150, 40);
        canvas.setTextSize(30);
        canvas.drawText(10, 10, "Score: " + score);
    }

    void setScore(int score) {
        this.score = score;
    }

}
