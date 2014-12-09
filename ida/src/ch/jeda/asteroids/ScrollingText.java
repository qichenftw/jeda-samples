package ch.jeda.asteroids;

import ch.jeda.event.KeyDownListener;
import ch.jeda.event.KeyEvent;
import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;
import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.GraphicsItem;
import ch.jeda.ui.Typeface;

public class ScrollingText extends GraphicsItem implements TickListener, KeyDownListener {

    private String[] lines;
    private double posY;
    private int lineHeight;

    ScrollingText(String[] lines) {
        this.lines = lines;
        posY = 0.0;
        lineHeight = 40;
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.setFontSize(40);
        canvas.setTypeface(Typeface.KENVECTOR_FUTURE);
        canvas.setColor(Color.WHITE);
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() + (int) posY;
        for (String line : lines) {
            canvas.drawText(x, y, line, Alignment.TOP_CENTER);
            y = y + lineHeight;
        }
    }

    @Override
    public void onTick(TickEvent event) {
        posY = posY - event.getDuration() * 30;
        if (posY < -getWindow().getHeight() - lines.length * lineHeight) {
            posY = 0.0;
        }
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        getWindow().setPage("Menu");
        posY = 0.0;
    }
}
