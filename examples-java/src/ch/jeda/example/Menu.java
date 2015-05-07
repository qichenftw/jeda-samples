package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Menu extends Program implements TickListener, ActionListener {

    private View view;
    private int x;
    private int y;
    private int choice;

    @Override
    public void run() {
        view = new View();
        // Write initialization code here.
        view.addEventListener(this);
        view.add(new Button(100, 50, "Circle"));
        view.add(new Button(300, 50, "Rectangle"));
        choice = 0;
        view.addEventListener(this);
    }

    public void moveCircle() {
        x = x + 1;
        view.getBackground().setColor(Color.RED);
        view.getBackground().fillCircle(x, y, 20);
    }

    public void moveRectangle() {
        y = y + 1;
        view.getBackground().setColor(Color.RED);
        view.getBackground().fillRectangle(x, y, 40, 30);
    }

    @Override
    public void onTick(TickEvent event) {
        view.getBackground().setColor(Color.WHITE);
        view.getBackground().fill();
        if (choice == 1) {
            moveCircle();
        }
        else if (choice == 2) {
            moveRectangle();
        }
    }

    @Override
    public void onAction(ActionEvent event) {
        if (event.getName().equals("Circle")) {
            choice = 1;
            x = 0;
            y = view.getHeight() / 2;
            removeButtons();
        }
        if (event.getName().equals("Rectangle")) {
            choice = 2;
            x = view.getWidth() / 2;
            y = 0;
            removeButtons();
        }
    }

    void removeButtons() {
        for (Button button : view.getElements(Button.class)) {
            view.remove(button);
        }
    }
}
