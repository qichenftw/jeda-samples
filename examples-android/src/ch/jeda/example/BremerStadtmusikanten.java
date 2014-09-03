package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;
import java.util.HashMap;
import java.util.Map;

public class BremerStadtmusikanten extends Program implements ActionListener {

    Window window;
    Map<String, Sound> sounds;
    int x;
    int y;

    @Override
    public void run() {
        window = new Window();
        sounds = new HashMap<String, Sound>();
        x = 10;
        y = 10;

        addButton("Hahn", "rooster");
        addButton("Katze", "cat");
        addButton("Hund", "dog");
        window.addEventListener(this);
    }

    private void addButton(String text, String sound) {
        sounds.put(text, new Sound("res:raw/" + sound + ".wav"));
        new Button(window, x, y, text);
        y = y + 60;
    }

    @Override
    public void onAction(ActionEvent event) {
        sounds.get(event.getName()).play();
    }
}
