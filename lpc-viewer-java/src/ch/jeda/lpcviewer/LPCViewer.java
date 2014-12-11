package ch.jeda.lpcviewer;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.lpc.Action;
import ch.jeda.lpc.Figurine;
import ch.jeda.ui.*;

public class LPCViewer extends Program implements TickListener, KeyDownListener {

    private Window window;
    private Figurine figurine;

    @Override

    public void run() {
        window = new Window();
        figurine = new Figurine();
        figurine.addFeature("base/female_human_tanned_2");
        figurine.addFeature("head/hair_long_2_purple");
        figurine.addFeature("base/female_dark_elf_1_ear");
        figurine.addFeature("clothing/boots_large_brown");
        figurine.addFeature("clothing/skirt");
        figurine.addFeature("clothing/corset_black");
        figurine.setPosition(window.getWidth() / 2, window.getHeight() / 2);
        figurine.setAction(Action.STAND);
        window.add(figurine);
        window.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        window.setColor(Color.GREEN);
        window.fill();
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        switch (event.getKey()) {
            case DIGIT_1:
                figurine.setAction(Action.STAND);
                break;
            case DIGIT_2:
                figurine.setAction(Action.WALK);
                break;
            case DIGIT_3:
                figurine.setAction(Action.SHOOT);
                break;
            case DIGIT_4:
                figurine.setAction(Action.SLASH);
                break;
            case DIGIT_5:
                figurine.setAction(Action.THRUST);
                break;
            case DIGIT_6:
                figurine.setAction(Action.SPELLCAST);
                break;
            case DIGIT_7:
                figurine.setAction(Action.HURT);
                break;
        }
    }
}
