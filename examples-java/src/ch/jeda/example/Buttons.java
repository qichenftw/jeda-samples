package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Buttons extends Program implements TickListener {

    Window window;
    Image background;

    @Override
    public void run() {
        window = new Window();
//        Button button1 = new Button(100, 50, StandardButton.PLAY, 1);
//        button1.setImage(new Image("res:jeda/ui/play_button_pressed.png"));
//        button1.setPressedImage(new Image("res:jeda/ui/play_button.png"));
//        window.add(button1);
        background = new Image("res:drawable/space.jpg");

        window.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        window.drawImage(0, 0, background);
    }
}
