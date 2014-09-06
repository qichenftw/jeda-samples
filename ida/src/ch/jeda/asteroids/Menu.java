package ch.jeda.asteroids;

import ch.jeda.Sound;
import ch.jeda.event.KeyDownListener;
import ch.jeda.event.KeyEvent;
import ch.jeda.ui.Alignment;
import ch.jeda.ui.Canvas;
import ch.jeda.ui.Color;
import ch.jeda.ui.Typeface;
import ch.jeda.ui.Widget;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Widget implements KeyDownListener {

    private List<String> options;
    private int selected;
    private int stepY;
    private Sound sound;

    public Menu(int x, int y, Alignment alignment) {
        super(x, y, alignment);
        options = new ArrayList<String>();
        selected = -1;
        stepY = 50;
        sound = new Sound("res:raw/menu-select.wav");
    }

    void addOption(String option) {
        options.add(option);
        if (selected == -1) {
            selected = options.size() - 1;
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    @Override
    public int getHeight() {
        return stepY * options.size();
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        switch (event.getKey()) {
            case UP:
                --selected;
                sound.play();
                if (selected < 0) {
                    selected = options.size() - 1;
                }

                break;
            case DOWN:
                ++selected;
                sound.play();
                if (options.size() <= selected) {
                    selected = 0;
                }

                break;

            case ENTER:
                action(options.get(selected));
                break;
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.setFontSize(40);
        canvas.setTypeface(Typeface.KENVECTOR_FUTURE);
        int y = getTop();
        for (int i = 0; i < options.size(); ++i) {
            String text = options.get(i);
            if (i == selected) {
                canvas.setColor(Color.YELLOW);
                text = "> " + text + " <";
            }
            else {
                canvas.setColor(Color.WHITE);
            }

            canvas.drawText(getX(), y, text, getAlignment());
            y = y + stepY;
        }
    }

}
