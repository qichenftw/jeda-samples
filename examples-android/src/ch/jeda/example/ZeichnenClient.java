package ch.jeda.example;

import ch.jeda.Data;
import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class ZeichnenClient extends Program implements PointerDownListener,
                                                       PointerMovedListener,
                                                       TickListener,
                                                       KeyUpListener {

    Window fenster;
    StringInputField serverNameInput;
    Button connectButton;
    NetworkSocket socket;
    String message;
    Color color;
    int x;
    int y;

    @Override
    public void run() {
        fenster = new Window();
        x = fenster.getWidth() / 2;
        y = fenster.getHeight() / 2 - 120;
        serverNameInput = new StringInputField(x, y, Alignment.CENTER);
        fenster.add(serverNameInput);
        y = y + 70;
        connectButton = new Button(x, y, Alignment.CENTER, "Verbinden");
        connectButton.setKey(Key.ENTER);
        connectButton.setStyle(DefaultButtonStyle.MODERN_GREEN);
        y = y + 50;
        fenster.add(connectButton);
        serverNameInput.select();
        fenster.addEventListener(this);
    }

    @Override
    public void onPointerDown(PointerEvent event) {
        draw(event);
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        draw(event);
    }

    @Override
    public void onTick(TickEvent event) {
        if (socket == null) {
            clear();
            fenster.setColor(Color.BLACK);
            fenster.drawText(fenster.getWidth() / 2, y, message, Alignment.CENTER);
        }
        else {
            if (socket.hasData()) {
                Data data = socket.receiveData();
                color = data.readObject("color");
            }
        }
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        if (event.getKey() == Key.ENTER && socket == null) {
            socket = new NetworkSocket();
            if (socket.connect(serverNameInput.getValue(), 1248)) {
                fenster.remove(serverNameInput);
                fenster.remove(connectButton);
                clear();
            }
            else {
                socket = null;
                message = "Verbindung mit Server nicht möglich.";
                serverNameInput.select();
            }
        }
    }

    private void clear() {
        fenster.setColor(Color.WHITE);
        fenster.fill();
        JedaLogo.draw(fenster);
    }

    private void draw(PointerEvent event) {
        if (socket != null) {
            Data data = new Data();
            data.writeInt("x", event.getX());
            data.writeInt("y", event.getY());
            socket.sendData(data);
            fenster.setColor(color);
            fenster.fillCircle(event.getX(), event.getY(), 5);
        }
    }
}
